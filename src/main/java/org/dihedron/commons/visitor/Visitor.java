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
public class Visitor implements Iterable<Node> {
	
	/**
	 * @author Andrea Funto'
	 */
	private static class NodeIterator implements Iterator<Node> {
	
		/**
		 * The position of the head of the list.
		 */
		private static final int HEAD = 0;
						
		/**
		 * The list of nodes in the object graph.
		 */
		private List<Node> nodes;
		
		/**
		 * Constructor.
		 *
		 * @param object
		 *   the object to visit.
		 * @throws VisitorException 
		 */
		private NodeIterator(Object object) throws VisitorException {
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
							
							String nodeName = getName(field.getName(), path);
							Object nodeValue = getValue(node, field);
							
							// checking if we need to recurse into this field's properties
							Visitable visitable = field.getAnnotation(Visitable.class);
							if(visitable != null) {
								
								if(isList(nodeValue)) {					
									logger.trace("node '{}' is a list of visitable objects", nodeName);
									List<?> list = (List<?>)nodeValue;
									int i = 0;
									for(Object elementValue : list) {
										String elementName = nodeName + "[" + i++ + "]";
										if(visitable.value()) {
											nodes.addAll(visitNode(elementValue, elementName));
										} else {
											nodes.add(new Node(elementName, elementValue));
										}
									}
								} else if(isArray(nodeValue)) {
									logger.trace("node '{}' is an array of visitable objects", nodeName);
									for(int i = 0; i < Array.getLength(nodeValue); ++ i) {
										Object elementValue = Array.get(nodeValue, i);
										String elementName = nodeName + "[" + i + "]";
										if(visitable.value()) {
											nodes.addAll(visitNode(elementValue, elementName));
										} else {
											nodes.add(new Node(elementName, elementValue));
										}
									}									
								} else if(isMap(nodeValue)) {
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
											nodes.add(new Node(elementName, entry.getValue()));
										}
									}																		
								} else if(isSet(nodeValue)) {
									logger.trace("node '{}' is a set of visitable objects", nodeName);
									Set<?> set = (Set<?>)nodeValue;
									int i = 0;
									for(Object elementValue : set) {
										String elementName = nodeName + "$" + i++;
										if(visitable.value()) {
											nodes.addAll(visitNode(elementValue, elementName));
										} else {
											nodes.add(new Node(elementName, elementValue));
										}
									}									
								} else {
									logger.trace("recursing into nested object '{}'", nodeName);
									nodes.addAll(visitNode(nodeValue, nodeName));
									logger.trace("... done recursing on '{}'", nodeName);
								}
							} else {
								nodes.add(new Node(nodeName, nodeValue));								
							}
						}
					}			
					clazz = clazz.getSuperclass();
				} while(clazz != null && clazz != Object.class);
			}
			return nodes;
		}
		
		/**
		 * Returns the OGNL name of the node.
		 * 
		 * @param name
		 *   the name of the property.
		 * @param path
		 *   the object graph navigation path so far.
		 * @return
		 *   the OGNL name of the node.
		 */
		protected static String getName(String name, String path) {
			logger.trace("getting OGNL name for node '{}' at path '{}'", name, path);
			StringBuilder buffer = new StringBuilder();
			if(path != null) {
				buffer.append(path);
			}
			if(buffer.length() != 0 && name != null && !name.startsWith("[")) {
				buffer.append(".");
			}
			buffer.append(name);
			logger.trace("OGNL path of node is '{}'", buffer.toString());
			return buffer.toString();
		}
		
		/**
		 * Returns the value of the given field on the given object.
		 * 
		 * @param object
		 *   the object whose field is to be retrieved.
		 * @param field
		 *   the field being retrieved.
		 * @return
		 *   the value of the field.
		 * @throws VisitorException 
		 *   if an error occurs while evaluating the node's value.
		 */
		protected static Object getValue(Object object, Field field) throws VisitorException {
			boolean reprotect = false;
			if(object == null) {
				return null;
			}
			try {
				if(!field.isAccessible()) {
					field.setAccessible(true);
					reprotect = true;
				}
				Object value = field.get(object);
				logger.trace("field '{}' has value '{}'", field.getName(), value);
				return value;
			} catch (IllegalArgumentException e) {
				logger.error("Trying to access field '{}' on invalid object of class '{}'", field.getName(), object.getClass().getSimpleName());
				throw new VisitorException("Trying to access field on invalid object", e);
			} catch (IllegalAccessException e) {
				logger.error("Illegal access to class '{}'", object.getClass().getSimpleName());
				throw new VisitorException("Illegal access to class", e);
			} finally {
				if(reprotect) {
					field.setAccessible(false);
				}
			}
		}	
		
		/**
		 * Returns whether the object under inspection is an array of objects, e.g if 
		 * applied to <code>int[]</code>, it will return <code>true</code>.
		 * 
		 * @return
		 *   whether the object under inspection is an array of objects.
		 */
		public static boolean isArray(Object object) {
	    	return object != null && object.getClass().isArray();
		}
	    
		/**
		 * Returns whether the object under inspection is an instance of a <code>
		 * List<?></code>.
		 * 
		 * @return
		 *   whether the object under inspection is a <code>List</code>. 
		 */
		public static boolean isList(Object object) {
			return object != null && object instanceof List<?>;
		}
		
		/**
		 * Returns whether the object under inspection is an instance of a <code>
		 * Set<?></code>.
		 * 
		 * @return
		 *   whether the object under inspection is a <code>Set</code>. 
		 */
		public static boolean isSet(Object object) {
			return object != null && object instanceof Set<?>;
		}
		    
	    /**
	     * Returns whether the object under inspection is an instance of a <code>
	     * Map<?, ?></code>.
	     * 
	     * @return
	     *   whether the object under inspection is a <code>Map</code>.
	     */
		public static boolean isMap(Object object) {
			return object != null && object instanceof Map<?, ?>;
		}		
	}
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(Visitor.class);

	/**
	 * The object whose properties are being inspected.
	 */
	private Object object;
	
	/**
	 * Constructor.
	 * 
	 * @param object
	 *   the object whose properties are being enumerated and visited.
	 */
	public Visitor(Object object) {
		this.object = object;
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Node> iterator() {
		try {
			return new NodeIterator(object);
		} catch (VisitorException e) {
			logger.error("error visiting object properties", e);
		}
		return null;
	}
}
