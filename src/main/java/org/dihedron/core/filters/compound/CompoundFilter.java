/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.filters.compound;

import java.util.ArrayList;
import java.util.List;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
@License
public abstract class CompoundFilter<T> extends Filter<T> {

	/**
	 * The set of sub-filters.
	 */
	private List<Filter<T>> subfilters = new ArrayList<Filter<T>>();
	
	/**
	 * Constructor.
	 */
	@SafeVarargs
	protected CompoundFilter(Filter<T> ...filters) {
		if(filters != null) {
			for(Filter<T> filter : filters) {
				subfilters.add(filter);
			}
		}
	}
	
	/**
	 * Returns the list of sub-filters.
	 * 
	 * @return
	 *   the list of sub-filters.
	 */
	protected List<Filter<T>> getSubFilters() {
		return subfilters;
	}
	
	/**
	 * @see org.dihedron.core.filters.Filter#reset()
	 */
	@Override
	public void reset() {
		for(Filter<T> subfilter : subfilters) {
			subfilter.reset();
		}
	}
}
