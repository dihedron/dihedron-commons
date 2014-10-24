/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.functional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dihedron.core.License;

/**
 * @author Andrea Funto'
 */
@License
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
		S newState = state;
		while(iterator.hasNext()) {
			newState = functor.apply(newState, (E)iterator.next());
		}
		return newState;		
	}
}
