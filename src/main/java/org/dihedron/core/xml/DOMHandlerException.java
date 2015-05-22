/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.xml;


/**
 * A {@code RuntimeException} that enables use of {@code DOMReader}s
 * in lambda expressions.
 * 
 * @author Andrea Funto'
 */
public class DOMHandlerException extends RuntimeException {
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = 4462966503899456710L;
	
	/**
	 * Constructor.
	 *
	 */
	public DOMHandlerException() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 * @param cause
	 *   the original cause.
	 * @param enableSuppression
	 *   whether suppression is enabled.
	 * @param writableStackTrace
	 *   whether the stack should be writable.
	 */
	public DOMHandlerException(String message, Throwable cause,	boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 * @param cause
	 *   the original cause.
	 */
	public DOMHandlerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 */
	public DOMHandlerException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *   the original cause.
	 */
	public DOMHandlerException(Throwable cause) {
		super(cause);
	}
}
