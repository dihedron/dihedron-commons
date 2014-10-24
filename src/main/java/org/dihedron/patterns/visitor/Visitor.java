/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.visitor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dihedron.core.License;
import org.dihedron.patterns.visitor.factories.ModifiableNodeFactory;
import org.dihedron.patterns.visitor.factories.UnmodifiableNodeFactory;
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
@License
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
	 * Constructor.
	 * 
	 * @param object
	 *   the object whose properties are being enumerated and visited.
	 */
	public Visitor(Object object) {
		this(object, DEFAULT_VISIT_MODE);
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
	public Visitor(Object object, VisitMode mode) {
		this.object = object;
		this.mode = mode;		
	}
		
	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Node> iterator() {
		try {
			NodeFactory factory = null;
			if(mode == VisitMode.READ_ONLY) {
				factory = new UnmodifiableNodeFactory();
			} else {
				factory = new ModifiableNodeFactory();
			}
			return this.new NodeIterator(object, factory);
		} catch (VisitorException e) {
			logger.error("error visiting object properties", e);
		}
		return null;
	}
	
	/**
	 * @author Andrea Funto'
	 */
	private final class NodeIterator implements Iterator<Node> {
			
		/**
		 * The position of the head of the list.
		 */
		private static final int HEAD = 0;
						
		/**
		 * The list of nodes in the object graph.
		 */
		private List<Node> nodes;
		
		/**
		 * The factory that will generate the nodes as the visit proceeds.
		 */
		private NodeFactory factory;
				
		/**
		 * Constructor.
		 *
		 * @param object
		 *   the object to visit.
		 * @param factory
		 *   the factory that will instantiate nodes corresponding to the object
		 *   fields as the visit proceeds.
		 * @throws VisitorException 
		 */
		private NodeIterator(Object object, NodeFactory factory) throws VisitorException {
			this.factory = factory;
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
		
		private List<Node> visitNode(Object currentNode, String path) throws VisitorException {
			logger.trace("visiting node at path '{}' (class '{}')", path, currentNode.getClass().getCanonicalName());
			List<Node> nodes = new ArrayList<Node>();
			if(currentNode != null) {
				
				Set<String> names = new HashSet<String>();
				Class<?> clazz = currentNode.getClass();
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
							
							// checking if we need to recurse into this field's properties
							Visitable visitable = field.getAnnotation(Visitable.class);
							if(visitable == null || visitable.value() == false) {
								logger.trace("node '{}' is an opaque object", nodeName);
								nodes.add(factory.makeObjectNode(path, currentNode, field));								
							} else {
								// treat each kind of node differently
								Class<?> type = field.getType();
								if(List.class.isAssignableFrom(type)) {
									logger.trace("node '{}' is a list", nodeName);
									List<?> list = (List<?>)getValue(currentNode, field);
									int i = 0;
									for(Object elementValue : list) {
										String elementName = nodeName + "[" + i++ + "]";
										if(visitable.recurse()) {
											nodes.addAll(visitNode(elementValue, elementName));
										} else {
											nodes.add(factory.makeListElementNode(elementName, list, i));
										}
									}
								} else if(Map.class.isAssignableFrom(type)) {									
									logger.trace("node '{}' is a map", nodeName);
									Map<?, ?> map = (Map<?, ?>)getValue(currentNode, field);
									for(Entry<?, ?> entry : map.entrySet()) {
										String elementName = nodeName + "['" + entry.getKey().toString() + "']";
										if(visitable.recurse()) {
											nodes.addAll(visitNode(entry.getValue(), elementName));
										} else {
											nodes.add(factory.makeMapEntryNode(elementName, map, entry.getKey()));
										}
									}
									
								} else if(Set.class.isAssignableFrom(type)) {
									logger.trace("node '{}' is a set", nodeName);
									Set<?> set = (Set<?>)getValue(currentNode, field);
									int i = 0;
									for(Object elementValue : set) {
										String elementName = nodeName + "$" + i++;
										if(visitable.recurse()) {
											nodes.addAll(visitNode(elementValue, elementName));
										} else {
											nodes.add(factory.makeSetElementNode(elementName, set, i));
										}
									}
								} else if(type.isArray()) {
									logger.trace("node '{}' is an array", nodeName);
									
									Object [] array = null;
									
									Object elementValue = getValue(currentNode, field);
									
									Class<?> componentType = elementValue.getClass().getComponentType();

								    if(componentType.isPrimitive()) {
								    	logger.trace("component type: {}", componentType);
								    	if(componentType == Boolean.TYPE) {
								    		array = new Object[((boolean[])elementValue).length];
								    		for(int i = 0; i < array.length; ++i) {
								                array[i] = new Boolean(((boolean[])elementValue)[i]);
								            }
								    	} else if(componentType == Character.TYPE) {
								    		array = new Object[((char[])elementValue).length];
								    		for(int i = 0; i < array.length; ++i) {
								                array[i] = new Character(((char[])elementValue)[i]);
								            }
								    	} else if(componentType == Byte.TYPE) {
								    		array = new Object[((byte[])elementValue).length];
								    		for(int i = 0; i < array.length; ++i) {
								                array[i] = new Byte(((byte[])elementValue)[i]);
								            }
								    	} else if(componentType == Short.TYPE) {
								    		array = new Object[((short[])elementValue).length];
								    		for(int i = 0; i < array.length; ++i) {
								                array[i] = new Short(((short[])elementValue)[i]);
								            }
								    	} else if(componentType == Integer.TYPE) {
								    		array = new Object[((int[])elementValue).length];
								    		for(int i = 0; i < array.length; ++i) {
								                array[i] = new Integer(((int[])elementValue)[i]);
								            }
								    	} else if(componentType == Long.TYPE) {
								    		array = new Object[((long[])elementValue).length];
								    		for(int i = 0; i < array.length; ++i) {
								                array[i] = new Long(((long[])elementValue)[i]);
								            }
								    	} else if(componentType == Float.TYPE) {
								    		array = new Object[((float[])elementValue).length];
								    		for(int i = 0; i < array.length; ++i) {
								                array[i] = new Float(((float[])elementValue)[i]);
								            }
								    	} else if(componentType == Double.TYPE) {
								    		array = new Object[((double[])elementValue).length];
								    		for(int i = 0; i < array.length; ++i) {
								                array[i] = new Double(((double[])elementValue)[i]);
								            }
								    	}
								    } else {									
										array = (Object[])getValue(currentNode, field);
										for(int i = 0; i < array.length; ++i) {
											Object arrayElementValue = array[i];
											String elementName = nodeName + "[" + i + "]";
											if(visitable.recurse()) {
												nodes.addAll(visitNode(arrayElementValue, elementName));
											} else {
												nodes.add(factory.makeArrayElementNode(elementName, array, i));
											}
										}
								    }
								} else {
									logger.trace("node '{}' is an opaque object", nodeName);
									nodes.add(factory.makeObjectNode(path, currentNode, field));								
								}
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
		protected String getName(String name, String path) {			
			StringBuilder buffer = new StringBuilder();
			if(path != null) {
				buffer.append(path);
			}
			if(buffer.length() != 0 && name != null && !name.startsWith("[")) {
				buffer.append(".");
			}
			buffer.append(name);
			logger.trace("OGNL name for node '{}' at path '{}' is '{}'", name, path, buffer.toString());
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
		protected Object getValue(Object object, Field field) throws VisitorException {
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
	}
}
