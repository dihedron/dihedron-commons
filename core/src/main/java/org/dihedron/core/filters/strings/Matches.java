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
package org.dihedron.core.filters.strings;

import org.dihedron.core.filters.Filter;
import org.dihedron.core.regex.Regex;

/**
 * @author Andrea Funto'
 */
public class Matches extends Filter<String> {

	/**
	 * The regular expression to match against.
	 */
	private Regex regex;
	
	/**
	 * Constructor.
	 *
	 * @param regex
	 *   the regular expression to match against.
	 */
	public Matches(String regex) {
		this(regex, true);
	}
	
	/**
	 * Constructor.
	 *
	 * @param regex
	 *   the regular expression to match against.
	 * @param caseSensitive
	 *   whether the match should be case sensitive (default: {@code true}).
	 */
	public Matches(String regex, boolean caseSensitive) {
		this.regex = new Regex(regex, caseSensitive);
	}	

	/**
	 * @see org.dihedron.core.filters.Filter#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(String object) {
		return regex.matches(object);
	}
}
