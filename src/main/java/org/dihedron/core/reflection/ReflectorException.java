/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.reflection;

import org.dihedron.core.License;


/**
 * Class of exceptions thrown by the {@code Reflector}.
 * 
 * @author Andrea Funto'
 */
@License
public class ReflectorException extends Exception {

	/**
	 * Serial version id. 
	 */
	private static final long serialVersionUID = 8902276931178671537L;
	
	/**
	 * Constructor. 
	 */
	public ReflectorException() {
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *   the exception message.
	 */
	public ReflectorException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *   the exception's root cause.
	 */
	public ReflectorException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 * @param cause
	 *   the exception's root cause.
	 */
	public ReflectorException(String message, Throwable cause) {
		super(message, cause);
	}
}
