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

import org.dihedron.commons.visitor.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class representing an object property, a node in the object graph.
 *  
 * @author Andrea Funto'
 */
public class ImmutableProperty implements Node {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ImmutableProperty.class);

	/**
	 * The name of the property.
	 */
	private String name;
	
	/**
	 * The value of the property.
	 */
	private Object value;
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the name of the property.
	 * @param value
	 *   the field value. 
	 */
	ImmutableProperty(String name, Object value) {
		this.name = name;
		this.value = value;
		logger.trace("property '{}' has value '{}'", name, value);
	}

	/**
	 * Returns the value of the name.
	 *	
	 * @return 
	 *   the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the value of the property.
	 *	
	 * @return 
	 *   the property value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the new value of the property; this operation is unsupported on value
	 * nodes and results in an exception being thrown.
	 *	
	 * @param value 
	 *   the value to set.
	 */
	public void setValue(Object value) {
		logger.error("cannot set value on read-only value node");
		throw new UnsupportedOperationException("Setting the value of a node is unsupported in read-only visit mode.");
	}
}
