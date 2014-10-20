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
package org.dihedron.patterns.cache;

import java.util.List;


/**
 * @author Andrea Funto'
 */
public class CacheException extends Exception {
	
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = -8733027292263547244L;

	/**
	 * A list of nested exceptions.
	 */
	private List<Exception> subexceptions;
	
	/**
	 * Constructor.
	 */
	public CacheException() {
	}

	/**
	 * Constructor.
	 * 
	 * @param messages
	 *   the exception messages.
	 */
	public CacheException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *   the exceptions root cause.
	 */
	public CacheException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param messages
	 *   the exception messages.
	 * @param cause
	 *   the exception root cause.
	 */
	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message
	 *   the exception message.
	 * @param subexceptions
	 *   the exception causes.
	 */
	public CacheException(String message, List<Exception> subexceptions) {
		super(message);
		this.subexceptions = subexceptions;
	}
	
	/**
	 * Returns the list of nested exceptions.
	 * 
	 * @return
	 *   the list of nested exceptions.
	 */
	public List<Exception> getNestedExceptions() {
		return this.subexceptions;
	}
}
