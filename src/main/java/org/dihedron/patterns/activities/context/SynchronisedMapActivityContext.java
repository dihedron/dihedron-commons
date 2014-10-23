/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.context;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dihedron.patterns.activities.ActivityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class SynchronisedMapActivityContext implements ActivityContext {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(SynchronisedMapActivityContext.class);

	/**
	 * The set of data stores as "state" (memory).
	 */
	private Map<String, Object> state;
	
	/**
	 * Constructor.
	 */
	public SynchronisedMapActivityContext() {
		state = Collections.synchronizedMap(new LinkedHashMap<String, Object>()); 
	}

	/**
	 * @see org.dihedron.patterns.activities.ActivityContext#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String key) {
		Object value = state.get(key);
		logger.trace("reading value for key '{}': '{}'", key, value);
		return value;
	}

	/**
	 * @see org.dihedron.patterns.activities.ActivityContext#setValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object setValue(String key, Object value) {
		Object previous = state.put(key,  value); 
		logger.trace("setting value of key '{}': '{}' (was '{}')", key, value, previous);
		return previous;
	}

	/**
	 * @see org.dihedron.patterns.activities.ActivityContext#removeValue(java.lang.String)
	 */
	@Override
	public Object removeValue(String key) {
		Object previous = state.remove(key);
		logger.trace("removing value for key '{}' (was '{}'", key, previous);
		return previous;
	}

	/**
	 * @see org.dihedron.patterns.activities.ActivityContext#hasValue(java.lang.String)
	 */
	@Override
	public boolean hasValue(String key) {
		return state.containsKey(key);
	}
}
