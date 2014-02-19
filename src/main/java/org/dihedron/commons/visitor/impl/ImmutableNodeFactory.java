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
package org.dihedron.commons.visitor.impl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.dihedron.commons.visitor.Node;
import org.dihedron.commons.visitor.NodeFactory;
import org.dihedron.commons.visitor.VisitorException;
import org.dihedron.commons.visitor.VisitorHelper;

/**
 * @author Andrea Funto'
 */
public class ImmutableNodeFactory extends NodeFactory {
	
	/**
	 * Constructor.
	 */
	public ImmutableNodeFactory() {
		super();
	}
	
	/**
	 * @see org.dihedron.commons.visitor.NodeFactory#makeNode(java.lang.String, java.lang.Object)
	 */
	@Override
	public Node makeNode(String name, Object value) throws VisitorException {
		return new ImmutableProperty(name, value);
	}
	
	/**
	 * @see org.dihedron.commons.visitor.NodeFactory#makeNode(java.lang.String, java.lang.Object, java.lang.reflect.Field)
	 */
	@Override
	public Node makeNode(String name, Object owner, Field field) throws VisitorException {
		return new ImmutableProperty(name, VisitorHelper.getValue(owner, field));
	}

	/**
	 * @see org.dihedron.commons.visitor.NodeFactory#makeNode(java.lang.String, java.lang.Object, java.lang.reflect.Field, int)
	 */
	@Override
	public Node makeNode(String name, Object owner, Field field, int index) throws VisitorException {
		Object value = null;
		Object collection = VisitorHelper.getValue(owner, field);
		if(VisitorHelper.isList(collection)) {
			List<?> list = (List<?>)collection;
			value = list.get(index);
		} else if(VisitorHelper.isArray(collection)) {
			value= Array.get(collection, index);
		} 
		return new ImmutableProperty(name, value);
	}

	/**
	 * @see org.dihedron.commons.visitor.NodeFactory#makeNode(java.lang.String, java.lang.Object, java.lang.reflect.Field, java.lang.Object)
	 */
	@Override
	public Node makeNode(String name, Object owner, Field field, Object key) throws VisitorException {
		Map<?, ?> map = (Map<?, ?>)VisitorHelper.getValue(owner, field);
		Object value = map.get(key);
		return new ImmutableProperty(name, value);
	}
}
