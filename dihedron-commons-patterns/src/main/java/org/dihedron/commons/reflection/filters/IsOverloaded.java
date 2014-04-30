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
import java.util.HashMap;
import java.util.Map;

import org.dihedron.commons.filters.Filter;

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
	 * @see org.dihedron.commons.filters.Filter#reset()
	 */
	@Override
	public void reset() {
		this.members.clear();
	}
}
