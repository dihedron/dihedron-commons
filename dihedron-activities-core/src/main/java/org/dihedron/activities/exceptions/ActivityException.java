/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Activities library ("Activities").
 *
 * Activities is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Activities is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Activities. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.activities.exceptions;


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
