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
import java.util.Map;
import java.util.Set;

/**
 * A map implementing and easy way to iterate over its elements in a 
 * pseudo-functional way.
 * 
 * @author Andrea Funto'
 */
public class FunctionalMap<K, V> implements Map<K, V> {
	
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
	 * Iterates over the entries in the map, and invokes the 
	 * {@link $#_(Entry<K, V>, S)} method on each of them, propagating
	 * the state as it goes.
	 * 
	 * @param item
	 *   an implementation of the {@code $} interface. 
	 * @return
	 *   the result of the iteration, as returned by the functor.
	 */
	public <E, S> S forEach($<E, S> item) {
		S result = null;
		for(Entry<K, V> entry : entrySet()) {
			result = item._((E)entry, result);
		}
		return result;
	}
	
//	public <S> S forEach($<Entry<K, V>, S> item) {
//		S result = null;
//		for(Entry<K, V> entry : entrySet()) {
//			result = item._(entry, result);
//		}
//		return result;
//	}
}
