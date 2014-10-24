/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities;

import org.dihedron.core.License;



/**
 * The business activity context; this context is used to store any  data that 
 * should be kept as "state" of the processing.
 *  
 * @author Andrea Funto'
 */
@License
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
