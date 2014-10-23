/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.functional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A map implementing and easy way to iterate over its elements in a 
 * pseudo-functional way.
 * 
 * @author Andrea Funto'
 */
public class FunctionalMap<S, K, V> extends Functional<S> implements Map<K, V> {
	
	/**
	 * The wrapped map.
	 */
	private final Map<K, V> map;
	
	/**
	 * Constructor.
	 *
	 * @param map
	 *  the wrapped map.
	 */
	public FunctionalMap(Map<K, V> map) {
		this.map = map;
	}

	/**
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return map.size();
	}

	/**
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	/**
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	/**
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		return map.get(key);
	}

	/**
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		return map.put(key, value);
	}

	/**
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public V remove(Object key) {		
		return map.remove(key);
	}

	/**
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);		
	}

	/**
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		map.clear();
	}

	/**
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	/**
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<V> values() {
		return map.values();
	}

	/**
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	/**
	 * @see org.dihedron.patterns.functional.Functional#forEach(java.lang.Object, org.dihedron.patterns.functional.Fx)
	 */
	@Override
	@SuppressWarnings("unchecked")	
	public <ME> S forEach(S state, Fx<S, ME> functor) {
		S newState = state;
		for(Entry<K, V> entry : entrySet()) {
			newState = functor.apply(newState, (ME)entry);
		}
		return newState;
	}
}
