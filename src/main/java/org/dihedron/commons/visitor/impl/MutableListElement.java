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
import java.util.List;

import org.dihedron.commons.visitor.Node;
import org.dihedron.commons.visitor.VisitorException;
import org.dihedron.commons.visitor.VisitorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class representing an element in an array, whose value can be set.
 *  
 * @author Andrea Funto'
 */
public class MutableListElement implements Node {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(MutableListElement.class);

	/**
	 * The name of the property.
	 */
	private String name;
	
	/**
	 * A reference to the object owning this field.
	 */
	private Object owner;
	
	/**
	 * The Java Reflection object representing the list.
	 */
	private Field field;
	
	/**
	 * The index of the field on the list.
	 */
	private int index;
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the name of the property.
	 * @param owner
	 *   the object on which the list resides.
	 * @param field
	 *   the field representing the list.
	 * @param index
	 *   the index of the element in the list.
	 */
	MutableListElement(String name, Object owner, Field field, int index) {
		this.name = name;
		this.owner = owner;
		this.field = field;
		this.index = index;
		logger.trace("property '{}' resides on field '{}' of object of class '{}'", name, field.getName(), owner.getClass().getSimpleName());
	}

	/**
	 * Returns the value of the name.
	 *	
	 * @return 
	 *   the name.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns the value of the property using reflection.
	 *	
	 * @return 
	 *   the property value.
	 * @throws VisitorException 
	 */
	public Object getValue() throws VisitorException {
		try {
			List<?> list = (List<?>)VisitorHelper.getValue(owner, field);
			return list.get(index);
		} catch (IllegalArgumentException e) {
			logger.error("error accessing list element '{}' at index {} on object of class '{}'", field.getName(), index, owner.getClass().getSimpleName());
			throw new VisitorException("Error accessing list element " + field.getName() + " at index " + index + " on object of class " + owner.getClass().getSimpleName(), e);
		}
	}

	/**
	 * Sets the new value of the property.
	 *	
	 * @param value 
	 *   the value to set.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setValue(Object value) throws VisitorException {
		logger.trace("setting value to '{}'", value);
		try {
			List list = (List)VisitorHelper.getValue(owner, field);
			list.set(index, value);
		} catch (IllegalArgumentException e) {
			logger.error("error accessing list element '{}' at index {} on object of class '{}'", field.getName(), index, owner.getClass().getSimpleName());
			throw new VisitorException("Error accessing list element " + field.getName() + " at index " + index + " on object of class " + owner.getClass().getSimpleName(), e);
		}
	}
}
