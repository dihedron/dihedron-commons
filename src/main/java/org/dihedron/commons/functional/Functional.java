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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrea Funto'
 */
public abstract class Functional<S> {
	
	public static final <E, S> Functional<S> functionalList(List<E> list) {
		return new FunctionalList<E, S>(list); 
	}
	
	public static final <E, S> Functional<S> functionalSet(Set<E> set) {
		return new FunctionalSet<E, S>(set);
	}
	
	public static final <K, V, S> Functional<S> functionalMap(Map<K, V> map) {
		return new FunctionalMap<K, V, S>(map);
	}

	/**
	 * Iterates over the list elements, passing to the given implementation of 
	 * the {@code L$} interface the elements and propagating the state as returned 
	 * by the functor.
	 * 
	 * @param functor
	 *   an implementation of the {@code $} class.
	 * @return
	 *   the result of the iteration.
	 */
	public abstract <E> S forEach($<E, S> functor);
}
