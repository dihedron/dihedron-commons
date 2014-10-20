/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Activities library ("Activities").
 *
 * Activities is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Activities is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Activities. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.activities.context;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dihedron.activities.ActivityContext;
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
	 * @see org.dihedron.activities.ActivityContext#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String key) {
		Object value = state.get(key);
		logger.trace("reading value for key '{}': '{}'", key, value);
		return value;
	}

	/**
	 * @see org.dihedron.activities.ActivityContext#setValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object setValue(String key, Object value) {
		Object previous = state.put(key,  value); 
		logger.trace("setting value of key '{}': '{}' (was '{}')", key, value, previous);
		return previous;
	}

	/**
	 * @see org.dihedron.activities.ActivityContext#removeValue(java.lang.String)
	 */
	@Override
	public Object removeValue(String key) {
		Object previous = state.remove(key);
		logger.trace("removing value for key '{}' (was '{}'", key, previous);
		return previous;
	}

	/**
	 * @see org.dihedron.activities.ActivityContext#hasValue(java.lang.String)
	 */
	@Override
	public boolean hasValue(String key) {
		return state.containsKey(key);
	}
}
