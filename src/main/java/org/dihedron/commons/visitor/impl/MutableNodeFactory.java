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

import java.lang.reflect.Field;

import org.dihedron.commons.visitor.Node;
import org.dihedron.commons.visitor.NodeFactory;
import org.dihedron.commons.visitor.VisitorException;
import org.dihedron.commons.visitor.VisitorHelper;

/**
 * @author Andrea Funto'
 */
public class MutableNodeFactory extends NodeFactory {

	/**
	 * Constructor.
	 */
	public MutableNodeFactory() {
		super();
	}

	/**
	 * @see org.dihedron.commons.visitor.NodeFactory#makeNode(java.lang.String, java.lang.Object)
	 */
	@Override
	public Node makeNode(String name, Object value) throws VisitorException {
		throw new VisitorException("Unsupported node type");
	}	
	
	/**
	 * @see org.dihedron.commons.visitor.NodeFactory#makeNode(java.lang.String, java.lang.Object, java.lang.reflect.Field)
	 */
	@Override
	public Node makeNode(String name, Object owner, Field field) throws VisitorException {		
		return new MutableProperty(name, owner, field);
	}

	/**
	 * @see org.dihedron.commons.visitor.NodeFactory#makeNode(java.lang.String, java.lang.Object, java.lang.reflect.Field, int)
	 */
	@Override
	public Node makeNode(String name, Object owner, Field field, int index) throws VisitorException {
		Object value = VisitorHelper.getValue(owner, field);
		if(VisitorHelper.isList(value)) {
			return new MutableListElement(name, owner, field, index);
		} else if(VisitorHelper.isArray(value)){
			return new MutableArrayElement(name, owner, field, index);
		}
		return null;
	}

	/**
	 * @see org.dihedron.commons.visitor.NodeFactory#makeNode(java.lang.String, java.lang.Object, java.lang.reflect.Field, java.lang.Object)
	 */
	@Override
	public Node makeNode(String name, Object owner, Field field, Object key) throws VisitorException {
		return new MutableMapElement(name, owner, field, key);
	}
}
