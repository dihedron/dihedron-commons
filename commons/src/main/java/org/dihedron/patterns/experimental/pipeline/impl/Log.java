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
package org.dihedron.patterns.experimental.pipeline.impl;

import org.dihedron.patterns.experimental.pipeline.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
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
