/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.reflection.filters;

import java.lang.reflect.Member;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
@License
public class NameIs<T extends Member> extends Filter<T> {

	/**
	 * This filter will return true only for fields or methods matching this name.
	 */
	private String name;
	
	/**
	 * Constructor.
	 *
	 * @param name
	 *   the name to match against.
	 */
	public NameIs(String name) {
		this.name = name;
	}

	/**
	 * @see org.dihedron.commons.reflection.ReflectorFilter#matches(java.lang.reflect.Member)
	 */
	@Override
	public boolean matches(T member) {
		return member.getName().equals(name);
	}
}
