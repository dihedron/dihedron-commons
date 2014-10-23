/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.reflection.filters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
public class HasAnnotation<T extends Member> extends Filter<T> {

	private Class<? extends Annotation> annotation;
	
	public HasAnnotation(Class<? extends Annotation> annotation) {
		this.annotation = annotation;
	}
	
	/**
	 * @see org.dihedron.commons.reflection.ReflectorFilter#matches(java.lang.reflect.Member)
	 */
	@Override
	public boolean matches(T member) {
		return member instanceof Method && ((Method)member).isAnnotationPresent(annotation);
	}
}
