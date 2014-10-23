/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.exceptions;


/**
 * @author Andrea Funto'
 */
public class ActivityException extends Exception {

	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = -8810042225080964171L;

	/**
	 * Constructor.
	 */
	public ActivityException() {
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 */
	public ActivityException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *   the exception cause.
	 */
	public ActivityException(Throwable cause) {
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
	public ActivityException(String message, Throwable cause) {
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
//	public ActivityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//	}
}
