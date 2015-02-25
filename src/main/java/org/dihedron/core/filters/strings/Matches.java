/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.filters.strings;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;
import org.dihedron.core.regex.Regex;

/**
 * @author Andrea Funto'
 */
@License
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
