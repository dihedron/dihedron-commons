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
public class Not<T> extends Filter<T> {


	private Filter<T> subfilter;
	
	/**
	 * Constructor.
	 */
	public Not(Filter<T> filter) {
		this.subfilter = filter;
	}

	/**
	 * @see org.dihedron.core.filters.Filter#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(T object) {
		return !subfilter.matches(object);
	}
	
	/**
	 * @see org.dihedron.core.filters.Filter#reset()
	 */
	@Override
	public void reset() {
		subfilter.reset();
	}
}
