/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.experimental.pipeline.impl;

import org.dihedron.core.License;
import org.dihedron.patterns.experimental.pipeline.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class Log<T> implements Filter<T> {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(Log.class);

	/**
	 * @see org.dihedron.patterns.experimental.pipeline.Consumer#consume(java.lang.Object)
	 */
	@Override
	public T consume(T data) {
		if(data != null) {
			logger.trace("data: '{}'", data);
		}
		return data;
	}
}
