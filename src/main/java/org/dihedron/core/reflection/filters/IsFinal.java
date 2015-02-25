/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */  

package org.dihedron.core.reflection.filters;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
@License
public class IsFinal<T extends Member> extends Filter<T> {

	/**
	 * @see org.dihedron.commons.reflection.ReflectorFilter#matches(java.lang.reflect.Member)
	 */
	@Override
	public boolean matches(Member member) {
		return Modifier.isFinal(member.getModifiers());
	}
}
