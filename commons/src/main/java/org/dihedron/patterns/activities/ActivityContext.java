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
package org.dihedron.patterns.activities;



/**
 * The business activity context; this context is used to store any  data that 
 * should be kept as "state" of the processing.
 *  
 * @author Andrea Funto'
 */
public interface ActivityContext {
	
	/**
	 * Retrieves a value from the memory store; how this is achieved is 
	 * implementation dependent.
	 * 
	 * @param key
	 *   the key under which the value is stored.
	 * @return
	 *   the value, if found, {@code null} otherwise.
	 */
	Object getValue(String key);
	
	/**
	 * Sets a value in the memory store.
	 * 
	 * @param key
	 *   the key under which the value is to be stored.
	 * @param value
	 *   the value to store.
	 * @return
	 *   the previous value for the given key, if any, or {@code null}.
	 */
	Object setValue(String key, Object value);
	
	/**
	 * Removes a value from the memory store.
	 * 
	 * @param key
	 *   the key to remove (along with value).
	 * @return
	 *   the previous value for the given key, if any, or {@code null}.
	 */
	Object removeValue(String key);
	
	/**
	 * Returns whether the given key is available in the context.
	 * 
	 * @param key
	 *   the name of the key whose existence is to be checked.
	 * @return
	 *   whether the given key exists in the context.
	 */
	boolean hasValue(String key);	
}
