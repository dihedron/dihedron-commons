/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.exceptions;


/**
 * An exception representing the fact that one or more arguments to an activity 
 * are not valid, either for their type or for their number.
 * 
 * @author Andrea Funto'
 */
public class InvalidArgumentException extends ActivityException {
	
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = 638449635269105267L;

	/**
	 * Constructor.
	 */
	public InvalidArgumentException() {
	}
	
	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 */
	public InvalidArgumentException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *   the exception cause.
	 */
	public InvalidArgumentException(Throwable cause) {
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
	public InvalidArgumentException(String message, Throwable cause) {
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
//	public InvalidArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//	}	
}
