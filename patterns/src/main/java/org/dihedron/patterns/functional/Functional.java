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
package org.dihedron.patterns.functional;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Andrea Funto'
 */
public abstract class Functional<S> {
	
	/**
	 * Returns a functional wrapper for the input list, providing a way to apply
	 * a function on all its elements in a functional style.
	 * 
	 * @param list
	 *   the list to which the functor will be applied.
	 * @return
	 *   a list that also extends this abstract class.
	 */
	public static final <S, E> Functional<S> functionalList(List<E> list) {
		return new FunctionalList<S, E>(list); 
	}
	
	/**
	 * Returns a functional wrapper for the input set, providing a way to apply
	 * a function on all its elements in a functional style.
	 * 
	 * @param set
	 *   the set to which the functor will be applied.
	 * @return
	 *   a set that also extends this abstract class.
	 */
	public static final <S, E> Functional<S> functionalSet(Set<E> set) {
		return new FunctionalSet<S, E>(set);
	}
	
	/**
	 * Returns a functional wrapper for the input map, providing a way to apply
	 * a function on all its entries in a functional style.
	 * 
	 * @param map
	 *   the map to which the functor will be applied.
	 * @return
	 *   a map that also extends this abstract class.
	 */	
	public static final <S, K, V> Functional<S> functionalMap(Map<K, V> map) {
		return new FunctionalMap<S, K, V>(map);
	}

	/**
	 * Applies the given functor to all elements in the input list.
	 * 
	 * @param list
	 *   the list to which the functor will be applied.
	 * @param state
	 *   an state variable, that will be used as the return vale for the iteration.
	 * @param functor
	 *   a function to be applied to all elements in the list.
	 * @return
	 *   the state object after the processing.
	 */
	public static final <S, E> S forEach(List<E> list, S state, Fx<S, E> functor) {
		return new FunctionalList<S, E>(list).forEach(state, functor);
	}
	
	/**
	 * Applies the given functor to all elements in the input set.
	 * 
	 * @param set
	 *   the set to which the functor will be applied.
	 * @param state
	 *   an state variable, that will be used as the return vale for the iteration.
	 * @param functor
	 *   a function to be applied to all elements in the set.
	 * @return
	 *   the state object after the processing.
	 */
	public static final <S, E> S forEach(Set<E> set, S state, Fx<S, E> functor) {
		return new FunctionalSet<S, E>(set).forEach(state, functor);
	}

	/**
	 * Applies the given functor to all entries in the input map.
	 * 
	 * @param map
	 *   the map to which the functor will be applied.
	 * @param state
	 *   an state variable, that will be used as the return vale for the iteration.
	 * @param functor
	 *   a function to be applied to all entries in the map.
	 * @return
	 *   the state object after the processing.
	 */
	public static final <S, K, V> S forEach(Map<K, V> map, S state, Fx<S, Entry<K, V>> functor) {
		return new FunctionalMap<S, K, V>(map).forEach(state, functor);
	}
	
	/**
	 * Iterates over the collection elements or entries and passing each of them 
	 * to the given implementation of the functor interface; if state needs to be 
	 * propagated, it can be instantiated and returned by the first invocation of 
	 * the functor and it will be passed along the next elements of the collection.
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
	 * Iterates over the collection elements or entries and passing each of them 
	 * to the given implementation of the functor interface; if state is provided
	 * it will be it will be passed along the elements of the collection to the
	 * functor.
	 * 
	 * @param state
	 *   a state variable to be used during the processing.
	 * @param functor
	 *   an implementation of the {@code $} functor interface.
	 * @return
	 *   the result of the iteration.
	 */
	public abstract <E> S forEach(S state, Fx<S, E> functor);
}
