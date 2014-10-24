/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.cache;

import java.util.List;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public class CacheException extends Exception {
	
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = -8733027292263547244L;

	/**
	 * A list of nested exceptions.
	 */
	private List<Exception> subexceptions;
	
	/**
	 * Constructor.
	 */
	public CacheException() {
	}

	/**
	 * Constructor.
	 * 
	 * @param messages
	 *   the exception messages.
	 */
	public CacheException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *   the exceptions root cause.
	 */
	public CacheException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param messages
	 *   the exception messages.
	 * @param cause
	 *   the exception root cause.
	 */
	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message
	 *   the exception message.
	 * @param subexceptions
	 *   the exception causes.
	 */
	public CacheException(String message, List<Exception> subexceptions) {
		super(message);
		this.subexceptions = subexceptions;
	}
	
	/**
	 * Returns the list of nested exceptions.
	 * 
	 * @return
	 *   the list of nested exceptions.
	 */
	public List<Exception> getNestedExceptions() {
		return this.subexceptions;
	}
}
