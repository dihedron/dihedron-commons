/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.reflection.filters;

import java.lang.reflect.Member;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;
import org.dihedron.core.regex.Regex;

/**
 * @author Andrea Funto'
 */
@License
public class NameLike<T extends Member> extends Filter<T> {

	/**
	 * This filter will return true only for fields or methods not matching this 
	 * regular expression.
	 */
	private Regex regex;
	
	/**
	 * Constructor.
	 *
	 * @param regex
	 *   the regular expression to match against.
	 */
	public NameLike(String regex) {
		this.regex = new Regex(regex);
	}

	/**
	 * @see org.dihedron.commons.reflection.ReflectorFilter#matches(java.lang.reflect.Member)
	 */
	@Override
	public boolean matches(T member) {
		return regex.matches(member.getName());
	}
}
