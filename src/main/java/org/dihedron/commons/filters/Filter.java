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
package org.dihedron.commons.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Andrea Funto'
 */
public abstract class Filter<T> {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(Filter.class);
	
	/**
	 * Applies the given filter to the input and returns only those elements 
	 * that match it.
	 * 
	 * @param filter
	 *   a (possible compound) filter; elements in the input array matching the 
	 *   filter will be returned.
	 * @param elements
	 *   the collection of objects to filter.
	 * @return
	 *   a filtered set of elements.
	 */
	public static <T> Collection<T> apply(Filter<T> filter, T ... elements) {
		List<T> list = new ArrayList<T>();
		if(elements != null) {
			for(T element : elements) {
				list.add(element);
			}
		}
		return apply(filter, list);
	}	
	
	/**
	 * Applies the given filter to the input and returns only those elements 
	 * that match it.
	 * 
	 * @param filter
	 *   a (possible compound) filter; elements in the input array matching the 
	 *   filter will be returned.
	 * @param elements
	 *   the collection of objects to filter.
	 * @return
	 *   a filtered set of elements.
	 */
	public static <T> Collection<T> apply(Filter<T> filter, Collection<T> elements) {
		Collection<T> result = new ArrayList<T>();
		if(elements != null) {
			for(T element : elements) {
				logger.trace("analysing element '{}'...", element);
				if(filter.matches(element)) {
					logger.trace("... element '{}' matches", element);
					result.add(element);
				} else {
					logger.trace("... element '{}' skipped", element);
				}
			}
		}
		return result;
	}
	
	/**
	 * Returns whether the given object matches this filter.
	 *  
	 * @param object
	 *   the object to test against this filter.
	 * @return
	 *   {@code true} to state that the given object matches this filter, 
	 *   {@code false} otherwise.
	 */	
	public abstract boolean matches(T object);
	
	/**
	 * This is an optional method; it should be implemented by stateful filters,
	 * which need to be reset before being reused.
	 */
	public void reset() {		
	}
}
