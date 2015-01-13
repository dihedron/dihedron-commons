/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
 
package org.dihedron.core.filters.compound;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
@License
public class And<T> extends CompoundFilter<T> {
	
	@SafeVarargs
	public And(Filter<T> ... filters) {
		super(filters);
	}

	/**
	 * @see org.dihedron.core.filters.Filter#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(T object) {
		boolean matched = true;
		for(Filter<T> filter : getSubFilters()) {
			matched = matched && filter.matches(object);
			if(!matched) {
				return false;
			}
		}
		return true;
	}
}
