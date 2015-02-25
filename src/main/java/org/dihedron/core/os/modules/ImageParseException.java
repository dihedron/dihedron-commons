/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.os.modules;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public class ImageParseException extends Exception {

	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = 2252358964479988679L;

	/**
	 * Constructor.
	 */
	public ImageParseException() {
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 */
	public ImageParseException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *   the exception root cause.
	 */
	public ImageParseException(Throwable cause) {
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
	public ImageParseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *   the exception message.
	 * @param cause
	 *   the exception root cause.
	 * @param enableSuppression
	 *   whether the exception can be suppressed in a try-with-resources block.
	 * @param writableStackTrace
	 *   whether the exception stack trace is writable in a try-with-resources block.
	 */
	public ImageParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
