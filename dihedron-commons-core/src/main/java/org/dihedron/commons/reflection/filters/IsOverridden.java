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
package org.dihedron.commons.reflection.filters;

import java.lang.reflect.Member;
import java.util.HashSet;
import java.util.Set;

import org.dihedron.commons.filters.Filter;

/**
 * @author Andrea Funto'
 */
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
