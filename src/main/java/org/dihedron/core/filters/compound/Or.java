/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.filters.compound;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
@License
public class Or<T> extends CompoundFilter<T> {
	
	@SafeVarargs
	public Or(Filter<T> ... filters) {
		super(filters);
	}

	/**
	 * @see org.dihedron.core.filters.Filter#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(T object) {
		boolean matched = false;
		for(Filter<T> filter : getSubFilters()) {
			matched = matched || filter.matches(object);
			if(matched) {
				return true;
			}
		}
		return false;
	}
}
