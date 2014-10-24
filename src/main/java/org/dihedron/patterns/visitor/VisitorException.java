/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.visitor;


/**
 * @author Andrea Funto'
 */
public class VisitorException extends Exception {
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = -1461779889544856758L;

	/**
	 * Constructor.
	 */
	public VisitorException() {
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 */
	public VisitorException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *   the exception cause.
	 */
	public VisitorException(Throwable cause) {
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
	public VisitorException(String message, Throwable cause) {
		super(message, cause);
	}
}
