/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
 */

package org.dihedron.commons.visitor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class performs a depth-first visit of an object graph by visiting one at 
 * a time the properties at a certain level (say level N), and then recursing 
 * into its children and descendants (at levels N+1, N+2...) until the bottom is
 * reached, then going on with its siblings and repeating the process over and
 * over until all simple properties and all visitable containers (arrays, lists, 
 * maps, sets?) have been visited.
 * 
 * @author Andrea Funto'
 */
public final class Visitor implements Iterable<Node> {
	
	/**
	 * The default kind of access to the object's properties.
	 */
	public static final VisitMode DEFAULT_VISIT_MODE = VisitMode.READ_ONLY;
	
	/**
	 * Whether Java Sets should be introspected; by default they should not,
	 * since there is no sensible way of representing their elements. 
	 */
	public static final boolean DEFAULT_VISIT_SETS = false;
				
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(Visitor.class);

	/**
	 * The object whose properties are being inspected.
	 */
	private Object object;
	
	/**
	 * How the visit is performed, that is if the modes will be modifiable or not.
	 */
	private VisitMode mode = DEFAULT_VISIT_MODE;
	
	/**
	 * Whether the Java sets should be visited or regarded as opaque objects.
	 */
	private boolean visitSets = DEFAULT_VISIT_SETS;

	/**
	 * Constructor.
	 * 
	 * @param object
	 *   the object whose properties are being enumerated and visited.
	 */
	public Visitor(Object object) {
		this(object, DEFAULT_VISIT_MODE, DEFAULT_VISIT_SETS);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param object
	 *   the object whose properties are being enumerated and visited.
	 * @param mode
	 *   whether the nodes should be visited in read-only or read/write mode.
	 */
	public Visitor(Object object, VisitMode mode) {
		this(object, mode, DEFAULT_VISIT_SETS);
	}

	/**
	 * Constructor.
	 * 
	 * @param object
	 *   the object whose properties are being enumerated and visited.
	 * @param visitSets
	 *   whether the Java sets should be visited or regarded as opaque objects.
	 */
	public Visitor(Object object, boolean visitSets) {
		this(object, DEFAULT_VISIT_MODE, visitSets);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param object
	 *   the object whose properties are being enumerated and visited.
	 * @param mode
	 *   whether the nodes should be visited in read-only or read/write mode.
	 * @param visitSets
	 *   whether the Java sets should be visited or regarded as opaque objects.
	 */
	public Visitor(Object object, VisitMode mode, boolean visitSets) {
		this.object = object;
		this.mode = mode;		
		this.visitSets = visitSets;
	}
		
	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Node> iterator() {
		try {
			return new NodeIterator(object, mode, visitSets);
		} catch (VisitorException e) {
			logger.error("error visiting object properties", e);
		}
		return null;
	}
	
	/**
	 * @author Andrea Funto'
	 */
	private static final class NodeIterator implements Iterator<Node> {
			
		/**
		 * The position of the head of the list.
		 */
		private static final int HEAD = 0;
						
		/**
		 * The list of nodes in the object graph.
		 */
		private List<Node> nodes;
		
		/**
		 * The factory that will create nodes.
		 */
		private NodeFactory factory;		
		
		/**
		 * Whether Java Sets should be introspected. 
		 */
		private boolean visitSets;
		
		/**
		 * Constructor.
		 *
		 * @param object
		 *   the object to visit.
		 * @param mode
		 *   whether the nodes being visited will be accessed in read-only or
		 *   read-write mode.
		 * @param visitSets
		 *   whether Java Sets should be introspected; by default they should 
		 *   not, since there is no sensible way of representing set elements.
		 * @throws VisitorException 
		 */
		private NodeIterator(Object object, VisitMode mode, boolean visitSets) throws VisitorException {
			this.factory = NodeFactory.getNodeFactory(mode);
			this.visitSets = visitSets;			
			this.nodes = visitNode(object, null);
		}
		
		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return !nodes.isEmpty();
		}
	
		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Node next() {
			if(nodes.isEmpty()) {
				return null;
			}
			return nodes.remove(HEAD);
		}
	
		/**
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Operation not supported");		
		}
		
		/**
		 * Runs a recursive visit of the given object's property fields.
		 * 
		 * @param node
		 *   the object whose fields are to be visited.
		 * @param path
		 *   the path (name) of the current node.
		 * @return
		 *   a list of nodes, one for each property of this objects (and their
		 *   respective properties).
		 * @throws VisitorException
		 */
		private List<Node> visitNode(Object node, String path) throws VisitorException {
			logger.trace("visiting node at path '{}' (class '{}')", path, node.getClass().getCanonicalName());
			List<Node> nodes = null;
			if(node != null) {
				nodes = new ArrayList<Node>();
				Set<String> names = new HashSet<String>();
				Class<?> clazz = node.getClass();
				do {
					logger.trace("analysing fields of class '{}'", clazz.getSimpleName());
					for(Field field : clazz.getDeclaredFields()) {
						if(Modifier.isStatic(field.getModifiers())) {
							logger.trace("skipping static field '{}'", field.getName());
						} else if(names.contains(field.getName())) {
							logger.trace("skipping overridden field '{}'", field.getName());
						} else if(field.getName().equals("this$0")) {
							logger.trace("skipping reference to outer class '{}'", field.getName());
						} else {
							logger.trace("adding field '{}'", field.getName());
							names.add(field.getName());
							
							String nodeName = VisitorHelper.getName(field.getName(), path);
							Object nodeValue = VisitorHelper.getValue(node, field);
							
							// checking if we need to recurse into this field's properties
							Visitable visitable = field.getAnnotation(Visitable.class);
							if(visitable != null) {
								
								if(VisitorHelper.isList(nodeValue)) {					
									logger.trace("node '{}' is a list of visitable objects", nodeName);
									List<?> list = (List<?>)nodeValue;
									int i = 0;
									for(Object elementValue : list) {
										String elementName = nodeName + "[" + i + "]";
										if(visitable.value()) {
											nodes.addAll(visitNode(elementValue, elementName));
										} else {
											nodes.add(factory.makeNode(elementName, node, field, i));
										}
										++i; // move on to next element
									}
								} else if(VisitorHelper.isArray(nodeValue)) {
									logger.trace("node '{}' is an array of visitable objects", nodeName);
									for(int i = 0; i < Array.getLength(nodeValue); ++ i) {										
										String elementName = nodeName + "[" + i + "]";
										if(visitable.value()) {
											nodes.addAll(visitNode(Array.get(nodeValue, i), elementName));
										} else {
											nodes.add(factory.makeNode(elementName, node, field, i));
										}
									}									
								} else if(VisitorHelper.isMap(nodeValue)) {
									logger.trace("node '{}' is a map of visitable objects", nodeName);
									Map<?, ?> map = (Map<?, ?>)nodeValue;
									for(Entry<?, ?> entry : map.entrySet()) {
										String label = null;
										Object key = entry.getKey();
										Class<?> cls = key.getClass();										
										if( cls == Byte.class 	|| 
											cls == Short.class 	||  
											cls == Long.class 	|| 
											cls == Float.class 	|| 
											cls == Double.class || 
											cls == Boolean.class||
											cls == Integer.class ) {
											label = key.toString();
										} else {
											label = "'" + key.toString() + "'";											
										}
										String elementName = nodeName + "[" + label + "]";
										if(visitable.value()) {
											nodes.addAll(visitNode(entry.getValue(), elementName));
										} else {
											nodes.add(factory.makeNode(elementName, node, field, key));
										}
									}																		
								} else if(visitSets && VisitorHelper.isSet(nodeValue)) {
									logger.trace("node '{}' is a set of visitable objects", nodeName);
									Set<?> set = (Set<?>)nodeValue;
									int i = 0;
									for(Object elementValue : set) {
										String elementName = nodeName + "$" + i++;
										if(visitable.value()) {
											nodes.addAll(visitNode(elementValue, elementName));
										} else {
											nodes.add(factory.makeNode(elementName, elementValue));
										}
									}									
								} else {
									logger.trace("recursing into nested object '{}'", nodeName);
									nodes.addAll(visitNode(nodeValue, nodeName));
									logger.trace("... done recursing on '{}'", nodeName);
								}
							} else {
								nodes.add(factory.makeNode(nodeName, nodeValue));								
							}
						}
					}			
					clazz = clazz.getSuperclass();
				} while(clazz != null && clazz != Object.class);
			}
			return nodes;
		}
	}	
}
