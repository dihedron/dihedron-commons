/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.experimental.pipeline;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public class PipelineException extends Exception {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1082210530326945317L;

	/**
	 * Constructor.
	 */
	public PipelineException() {
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 */
	public PipelineException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 */
	public PipelineException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 * @param cause
	 */
	public PipelineException(String message, Throwable cause) {
		super(message, cause);
	}
}
