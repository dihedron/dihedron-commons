/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.concurrent;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public class TaskException extends Exception {
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = 1505568488005107618L;

	/**
	 * Constructor.
	 */
	public TaskException() {
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 */
	public TaskException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *   the exception root cause.
	 */
	public TaskException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 * @param cause
	 *   the exception root cause.
	 */
	public TaskException(String message, Throwable cause) {
		super(message, cause);
	}
}
