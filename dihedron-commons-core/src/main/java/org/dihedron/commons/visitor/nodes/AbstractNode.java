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

import java.lang.reflect.Field;

import org.dihedron.commons.visitor.Node;
import org.dihedron.commons.visitor.VisitorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class AbstractNode implements Node {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(AbstractNode.class);

	/**
	 * The name of the node, as a pseudo-OGNL path (for it takes into account 
	 * Java Sets, which are not supported by OGNL).
	 */
	private String name;
	
	/** Constructor.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the node.
	 */
	protected AbstractNode(String name) {
		this.name = name;
	}

	/**
	 * @see org.dihedron.commons.visitor.Node#getName()
	 */
	@Override
	public String getName() {		
		return name;
	}

	/**
	 * @see org.dihedron.commons.visitor.Node#getValue()
	 */
	@Override
	public Object getValue() throws VisitorException {
		throw new VisitorException("Unsupported operation");
	}

	/**
	 * @see org.dihedron.commons.visitor.Node#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) throws VisitorException {
		throw new VisitorException("Unsupported operation");
	}
	
	/**
	 * Attempts to retrieve the object stored in this node's object field; the
	 * result will be cast to the appropriate class, as per the user's input.
	 * 
 	 * @param object
	 *   the object owning the field represented by this node object.s
	 * @param field
	 *   the field containing the object represented by this node object.
	 * @param clazz
	 *   the class to which to cast the non-null result.
	 * @throws VisitorException 
	 *   if an error occurs while evaluating the node's value.
	 */
	protected <T> T getFieldValue(Object object, Field field, Class<T> clazz) throws VisitorException {
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
			if(value != null) {
				logger.trace("field '{}' has value '{}'", field.getName(), value);
				return clazz.cast(value);
			}			
		} catch (IllegalArgumentException e) {
			logger.error("trying to access field '{}' on invalid object of class '{}'", field.getName(), object.getClass().getSimpleName());
			throw new VisitorException("Trying to access field on invalid object", e);
		} catch (IllegalAccessException e) {
			logger.error("illegal access to class '{}'", object.getClass().getSimpleName());
			throw new VisitorException("Illegal access to class", e);
		} finally {
			if(reprotect) {
				field.setAccessible(false);
			}
		}
		return null;
	}	

	/**
	 * Attempts to store a new object into this node's object field.
	 * 
 	 * @param object
	 *   the object owning the field represented by this node object.
	 * @param field
	 *   the field containing the object represented by this node object.
	 * @param value
	 *   the value to store into this node's object's field.
	 * @throws VisitorException 
	 *   if an error occurs while storing the node's value.
	 */
	protected void setFieldValue(Object object, Field field, Object value) throws VisitorException {
		boolean reprotect = false;
		if(object == null) {
			return;
		}
		try {
			if(!field.isAccessible()) {
				field.setAccessible(true);
				reprotect = true;
			}
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			logger.error("trying to access field '{}' on invalid object of class '{}'", field.getName(), object.getClass().getSimpleName());
			throw new VisitorException("Trying to access field on invalid object", e);
		} catch (IllegalAccessException e) {
			logger.error("illegal access to class '{}'", object.getClass().getSimpleName());
			throw new VisitorException("Illegal access to class", e);
		} finally {
			if(reprotect) {
				field.setAccessible(false);
			}
		}
	}	
}
