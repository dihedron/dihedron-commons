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

package org.dihedron.commons.visitor.nodes;

import org.dihedron.commons.visitor.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class representing an object property, a node in the object graph,
 * whose value can be set.
 *  
 * @author Andrea Funto'
 */
public class ReadWriteNode implements Node {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ReadWriteNode.class);

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
	 */
	public ReadWriteNode() {
		this.name = null;
		this.value = null;
		logger.trace("uninitialised property");
	}
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the name of the property.
	 * @param value
	 *   the field value. 
	 */
	public ReadWriteNode(String name, Object value) {
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
	 * Sets the new value of the name.
	 *	
	 * @param name 
	 *   the name to set.
	 */
	public void setName(String name) {
		logger.trace("setting name to '{}'", name);
		this.name = name;
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
	 * Sets the new value of the property.
	 *	
	 * @param value 
	 *   the value to set.
	 */
	public void setValue(Object value) {
		logger.trace("setting value to '{}'", value);
		this.value = value;
	}
}
