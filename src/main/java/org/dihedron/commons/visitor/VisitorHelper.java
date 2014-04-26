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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class containing utilities shared by multiple classes in this package.
 * 
 * @author Andrea Funto'
 */
public final class VisitorHelper {
	
	/**
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(VisitorHelper.class);
	
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
	public static String getName(String name, String path) {
		logger.trace("getting OGNL name for node '{}' at path '{}'", name, path);
		StringBuilder buffer = new StringBuilder();
		if(path != null) {
			buffer.append(path);
		}
		if(buffer.length() != 0 && name != null && !name.startsWith("[")) {
			buffer.append(".");
		}
		buffer.append(name);
		logger.trace("OGNL path of node is '{}'", buffer.toString());
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
	public static Object getValue(Object object, Field field) throws VisitorException {
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
	
	/**
	 * Returns whether the object under inspection is an array of objects, e.g if 
	 * applied to <code>int[]</code>, it will return <code>true</code>.
	 * 
	 * @return
	 *   whether the object under inspection is an array of objects.
	 */
	public static boolean isArray(Object object) {
    	return object != null && object.getClass().isArray();
	}
    
	/**
	 * Returns whether the object under inspection is an instance of a <code>
	 * List<?></code>.
	 * 
	 * @return
	 *   whether the object under inspection is a <code>List</code>. 
	 */
	public static boolean isList(Object object) {
		return object instanceof List<?>;
	}
	
	/**
	 * Returns whether the object under inspection is an instance of a <code>
	 * Set<?></code>.
	 * 
	 * @return
	 *   whether the object under inspection is a <code>Set</code>. 
	 */
	public static boolean isSet(Object object) {
		return object instanceof Set<?>;
	}
	    
    /**
     * Returns whether the object under inspection is an instance of a <code>
     * Map<?, ?></code>.
     * 
     * @return
     *   whether the object under inspection is a <code>Map</code>.
     */
	public static boolean isMap(Object object) {
		return object instanceof Map<?, ?>;
	}	
	
	/**
	 * Private constructor, to prevent instantiation of library.
	 */
	private VisitorHelper() {
	}
}
