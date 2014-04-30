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
package org.dihedron.commons.values;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public final class Exceptions {
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Exceptions.class);
	
	/**
	 * The default string used to separate nested exceptions.
	 */
	private static final String DEFAULT_CAUSED_BY = "\ncaused by: ";

	/**
	 * Prints the chain of exceptions to a string.
	 * 
	 * @param th
	 *   the exception to be printed.
	 * @return
	 *   the exceptions chain.
	 */
	public static String toString(Throwable th) {
		return toString(th, null);
	}

	/**
	 * Prints the chain of exceptions to a string.
	 * 
	 * @param th
	 *   the exception to be printed.
	 * @param causedBy
	 *   the string to be used for chaining the exceptions.
	 * @return
	 *   the exceptions chain.
	 */
	public static String toString(Throwable th, String causedBy) {
		String separator = causedBy;
		if(separator == null) {
			separator = DEFAULT_CAUSED_BY;		
		}
		StringBuilder buffer = new StringBuilder();
		logger.trace("causedBy string: '{}'", separator);
		Throwable current = th;
		while(current != null) {
			if(buffer.length() > 0) buffer.append(separator);
			buffer.append(current.toString());
			current = current.getCause();
		}
		logger.trace("exceptions chain:\n{}", buffer.toString());
		return buffer.toString();
	}
	
	/**
	 * Private constructor (to prevent instantiation).
	 */
	private Exceptions() {
	}
}
