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
package org.dihedron.commons.filters.compound;

import java.util.ArrayList;
import java.util.List;

import org.dihedron.commons.filters.Filter;

/**
 * @author Andrea Funto'
 */
public abstract class CompoundFilter<T> extends Filter<T> {

	/**
	 * The set of sub-filters.
	 */
	private List<Filter<T>> subfilters = new ArrayList<Filter<T>>();
	
	/**
	 * Constructor.
	 */
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
	 * @see org.dihedron.commons.filters.Filter#reset()
	 */
	@Override
	public void reset() {
		for(Filter<T> subfilter : subfilters) {
			subfilter.reset();
		}
	}
}
