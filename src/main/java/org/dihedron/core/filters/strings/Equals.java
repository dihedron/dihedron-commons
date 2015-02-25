/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.filters.strings;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
@License
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
	 * @see org.dihedron.core.filters.Filter#matches(java.lang.Object)
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
