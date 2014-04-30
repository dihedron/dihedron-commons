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
