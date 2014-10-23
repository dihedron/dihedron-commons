/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.reflection.filters;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
public class IsOverloaded<T extends Member> extends Filter<T> {

	private Map<String, Member> members = new HashMap<String, Member>();
	
	/**
	 * @see org.dihedron.commons.reflection.ReflectorFilter#matches(java.lang.reflect.Member)
	 */
	@Override
	public boolean matches(T member) {
		if(members.containsKey(member.getName())) {
			Member previous = members.get(member.getName());
			if(previous.getDeclaringClass() == member.getDeclaringClass()) {
				// there are two members (methods!) with the same name in the 
				// same class, thus there's some overloading going on here!
				return true;
			}
		}
		members.put(member.getName(), member);
		return false;
	}
	
	/**
	 * @see org.dihedron.core.filters.Filter#reset()
	 */
	@Override
	public void reset() {
		this.members.clear();
	}
}
