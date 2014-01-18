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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Andrea Funto'
 */
public class FunctionalSet<E, S> extends Functional<S> implements Set<E> {

	/**
	 * The wrapped set.
	 */
	private final Set<E> set;
	
	/**
	 * Constructor.
	 *
	 * @param set
	 *   the wrapped set.
	 */
	public FunctionalSet(Set<E> set) {
		this.set = set;
	}

	/**
	 * @see java.util.Set#size()
	 */
	@Override
	public int size() {
		return set.size();
	}

	/**
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	/**
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	/**
	 * @see java.util.Set#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return set.iterator();
	}

	/**
	 * @see java.util.Set#toArray()
	 */
	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	/**
	 * @see java.util.Set#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	/**
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		return set.add(e);
	}

	/**
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	/**
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	/**
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return set.addAll(c);
	}

	/**
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	/**
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	/**
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		set.clear();
	}

	/**
	 * @see org.dihedron.commons.functional.Functional#forEach(java.lang.Object, org.dihedron.commons.functional.$)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <SE> S forEach(S state, $<SE, S> functor) {
		for(E element : set) {
			state = functor._((SE)element, state);
		}
		return state;
	}
}
