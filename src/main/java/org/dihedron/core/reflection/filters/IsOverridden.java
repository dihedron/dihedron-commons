/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.reflection.filters;

import java.lang.reflect.Member;
import java.util.HashSet;
import java.util.Set;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
@License
public class IsOverridden<T extends Member> extends Filter<T> {

	/**
	 * A collection of names encountered so far; if the name of the member is
	 * already available in the set, then this object is masked out.
	 */
	private Set<String> names = new HashSet<String>();
	
	/**
	 * A member masked (overridden) if there is an instance of its name in the 
	 * collection that gets built up as we iterate through a class members, from 
	 * the bottom to the top of the hierarchy. This behaviour is consistent and
	 * predictable only if we navigate the class hierarchy from bottom to top.
	 * 
	 * @see org.dihedron.commons.reflection.ReflectorFilter#matches(java.lang.reflect.Member)
	 */
	@Override
	public boolean matches(T member) {
		String name = member.getName();
		if(names.contains(name)) {
			return true;
		}
		names.add(name);
		return false;
	}
}
