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
package org.dihedron.commons.functional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrea Funto'
 */
public class $<S> {

	/**
	 * The iterator, used to scan the the input collection.
	 */
	Iterator<?> iterator;
	
	/**
	 * Constructor.
	 * 
	 * @param list
	 *   a list to iterate on.
	 */
	public <E> $(List<E> list) {
		iterator = list.iterator();
	}
	
	/**
	 * Constructor.
	 *
	 * @param set
	 *   a set to iterate on.
	 */
	public <E> $(Set<E> set) {
		iterator = set.iterator();
	}
	
	/**
	 * Constructor.
	 *
	 * @param map
	 *   a map to iterate on, entry-wise.
	 */
	public <K, V> $(Map<K, V> map) {
		iterator = map.entrySet().iterator();
	}
	
	/**
	 * Iterates over the collection elements or entries and passing each of them 
	 * to the given implementation of the functor interface; if state needs to be 
	 * propagated, it can be instantiated and returned by the first invocation of 
	 * the functor and it will be passed as a parameter to the invocation of the
	 * functor on the the following elements of the collection.
	 * 
	 * @param functor
	 *   an implementation of the {@code $} functor interface.
	 * @return
	 *   the result of the iteration.
	 */
	public <E> S forEach(Fx<S, E> functor) {
		return forEach(null, functor);
	}
	
	/**
	 * Iterates over the collection of elements or entries, passing each of them 
	 * to the given implementation of the functor interface; if state is provided
	 * it will be it passed as a parameter to the invocations of the functor
	 * on the following elements of the collection.
	 * 
	 * @param state
	 *   a state variable to be used during the processing.
	 * @param functor
	 *   an implementation of the {@code $} functor interface.
	 * @return
	 *   the result of the iteration.
	 */
	@SuppressWarnings("unchecked")
	public <E> S forEach(S state, Fx<S, E> functor) {
		while(iterator.hasNext()) {
			state = functor.apply(state, (E)iterator.next());
		}
		return state;		
	}
}
