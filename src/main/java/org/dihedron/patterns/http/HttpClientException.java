/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.http;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public class HttpClientException extends Exception {
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = 6109976920325678249L;

	/**
	 * Constructor.
	 */
	public HttpClientException() {
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 */
	public HttpClientException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *   the exception root cause.
	 */
	public HttpClientException(Throwable cause) {
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
	public HttpClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
