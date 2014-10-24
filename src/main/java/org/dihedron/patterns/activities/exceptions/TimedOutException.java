/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.exceptions;

import org.dihedron.core.License;


/**
 * An exception representing the fact that an asynchronous activity timed out.
 * 
 * @author Andrea Funto'
 */
@License
public class TimedOutException extends ActivityException {
	
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = 638449635269105267L;

	/**
	 * Constructor.
	 */
	public TimedOutException() {
	}
	
	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 */
	public TimedOutException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *   the exception cause.
	 */
	public TimedOutException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 * @param cause
	 *   the exception cause.
	 */
	public TimedOutException(String message, Throwable cause) {
		super(message, cause);
	}

//	/**
//	 * Constructor.
//	 *
//	 * @param message
//	 *   the exception message.
//	 * @param cause
//	 *   the exception cause.
//	 * @param enableSuppression
//	 *   whether the exception can be suppressed.
//	 * @param writableStackTrace
//	 *   whether the stack trace is writable.
//	 */
//	public TimedOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//	}	
}
