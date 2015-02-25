/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.properties;

import org.dihedron.core.License;


/**
 * Class of exceptions thrown by the {@code Properties} methods.
 * 
 * @author Andrea Funto'
 */
@License
public class PropertiesException extends Exception {

	/**
	 * Serial version id. 
	 */
	private static final long serialVersionUID = 8902276931178671537L;
	
	/**
	 * Constructor. 
	 */
	public PropertiesException() {
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *   the exception message.
	 */
	public PropertiesException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *   the exception's root cause.
	 */
	public PropertiesException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 *   the exception message.
	 * @param cause
	 *   the exception's root cause.
	 */
	public PropertiesException(String message, Throwable cause) {
		super(message, cause);
	}
}
