/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The common interface to all node factories; the factory will generate node 
 * instances that will allow read-only or read/write access to the underlying 
 * nodes.
 * 
 * @author Andrea Funto'
 */
public interface NodeFactory {
	
	/**
	 * Creates a new node instance representing the given object field.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the object represented by the node being created.
	 * @param object
	 *   the object on which the field resides.
	 * @param field
	 *   the actual underlying field.
	 * @return
	 *   the {@link Node} object.
	 */
	Node makeObjectNode(String name, Object object, Field field);
	
	/**
	 * Creates a new node instance representing an element in a list.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the list represented by the node being created.
	 * @param list
	 *   the list on which the object is contained.
	 * @param index
	 *   the index of the element in the list.
	 * @return
	 *   the {@link Node} object.
	 */
	Node makeListElementNode(String name, List<?> list, int index);

	/**
	 * Creates a new node instance representing an element in an array.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the array represented by the node being created.
	 * @param array
	 *   the array in which the object/element resides.
	 * @param index
	 *   the index of the object in the array.
	 * @return
	 *   the {@link Node} object.
	 */
	Node makeArrayElementNode(String name, Object[] array, int index);

	/**
	 * Creates a new node instance representing an element in a set.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the array represented by the node being created.
	 * @param set
	 *   the set in which the object resides
	 * @param element
	 *   the actual element in the set; this allows to simulate an "update" 
	 *   semantics on modifiable sets.
	 * @return
	 *   the {@link Node} object.
	 */
	Node makeSetElementNode(String name, Set<?> sets, Object element);
	
	/**
	 * Creates a new node instance representing the value of an entry in a set.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the array represented by the node being created.
	 * @param object
	 *   the map containing the object.
	 * @param key
	 *   the key of the entry in the underlying map.
	 * @return
	 *   the {@link Node} object.
	 */
	Node makeMapEntryNode(String name, Map<?, ?> map, Object key);
}
