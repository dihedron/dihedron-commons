/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
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
