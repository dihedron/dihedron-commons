/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.functional;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Andrea Funto'
 */
public class FunctionalSet<S, E> extends Functional<S> implements Set<E> {

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
	 * @see org.dihedron.patterns.functional.Functional#forEach(java.lang.Object, org.dihedron.patterns.functional.Fx)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <SE> S forEach(S state, Fx<S, SE> functor) {
		S newState = state;
		for(E element : set) {
			newState = functor.apply(newState, (SE)element);
		}
		return newState;
	}
}
