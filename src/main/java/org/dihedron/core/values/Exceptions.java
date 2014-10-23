/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.values;

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
