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
package org.dihedron.commons.filters.strings;

import org.dihedron.commons.filters.Filter;

/**
 * @author Andrea Funto'
 */
public class Equals extends Filter<String> {

	/**
	 * The stream to match against.
	 */
	private String string;
	
	/**
	 * Whether the comparison should be case sensitive.
	 */
	private boolean caseSensitive = true;
	
	/**
	 * Constructor.
	 *
	 * @param string
	 *   the string to match against.
	 */
	public Equals(String string) {
		this(string, true);
	}
	
	/**
	 * Constructor.
	 *
	 * @param string
	 *   the string to match against.
	 * @param caseSensitive
	 *   whether the match should be case sensitive (default: {@code true}).
	 */
	public Equals(String string, boolean caseSensitive) {
		this.string = string;
		this.caseSensitive = caseSensitive;
	}	

	/**
	 * @see org.dihedron.commons.filters.Filter#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(String object) {
		if(caseSensitive) {
			return string.equals(object);
		} else {
			return string.equalsIgnoreCase(object);
		}
	}
}
