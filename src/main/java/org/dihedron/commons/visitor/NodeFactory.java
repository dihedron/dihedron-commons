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

import org.dihedron.commons.visitor.impl.ImmutableNodeFactory;
import org.dihedron.commons.visitor.impl.MutableNodeFactory;

/**
 * @author Andrea Funto'
 */
public abstract class NodeFactory {
	
	/**
	 * Factory method, returns an instance of the appropriate node factory.
	 * 
	 * @param mode
	 *   how the visit is being performed: whether nodes are accessed in  rad-only
	 *   mode or in read/write mode.
	 * @return
	 *   returns an instance of the appropriate node factory for the given mode.
	 */
	public static NodeFactory getNodeFactory(VisitMode mode) {
		switch(mode) {
		case READ_ONLY:	
			return new ImmutableNodeFactory();
		case READ_WRITE:
			return new MutableNodeFactory();
		}
		return null;
	}

	/**
	 * Creates a node exposing an unmodifiable object property; this method is 
	 * used exclusively to support sets. 
	 * 
	 * @param name
	 *   the name of the node in the hierarchy.
	 * @param value
	 *   the value of the property.
	 * @return
	 *   a <code>Node</code> of the appropriate type.
	 * @throws VisitorException
	 */
	public abstract Node makeNode(String name, Object value) throws VisitorException;
	
	/**
	 * Creates a node exposing a modifiable/unmodifiable object property.
	 * 
	 * @param name
	 *   the name of the node in the hierarchy.
	 * @param owner
	 *   the object on which the property can be found.
	 * @param field
	 *   the Java Reflection field object.
	 * @return
	 *   a <code>Node</code> of the appropriate type.
	 * @throws VisitorException
	 */
	public abstract Node makeNode(String name, Object owner, Field field) throws VisitorException;

	/**
	 * Creates a node exposing a modifiable/unmodifiable element in an indexed 
	 * container (list or array), given the field representing the container, 
	 * the object owning the field and the index of the element in the container.
	 * 
	 * @param name
	 *   the name of the node in the hierarchy.
	 * @param owner
	 *   the object on which the property can be found.
	 * @param field
	 *   the Java Reflection field object (the list or array).
	 * @param index
	 *   the index of the element in the list or array.
	 * @return
	 *   a <code>Node</code> of the appropriate type.
	 * @throws VisitorException
	 */
	public abstract Node makeNode(String name, Object owner, Field field, int index) throws VisitorException;

	/**
	 * Creates a node exposing a modifiable/unmodifiable element in a map 
	 * container, given the field representing the list, the object owning the 
	 * field and the key of the element in the map.
	 * 
	 * @param name
	 *   the name of the node in the hierarchy.
	 * @param owner
	 *   the object on which the property can be found.
	 * @param field
	 *   the Java Reflection field object (the map).
	 * @param key
	 *   the key of the element in the map.
	 * @return
	 *   a <code>Node</code> of the appropriate type.
	 * @throws VisitorException
	 */
	public abstract Node makeNode(String name, Object owner, Field field, Object key) throws VisitorException;
	
	/**
	 * Protected constructor, to prevent improper instantiation.
	 */
	protected NodeFactory() {		
	}
}
