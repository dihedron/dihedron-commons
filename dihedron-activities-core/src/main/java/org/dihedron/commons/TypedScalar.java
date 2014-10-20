/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Activities library ("Activities").
 *
 * Activities is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Activities is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Activities. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.commons;

import org.dihedron.activities.Copyable;


/**
 * An object representing a scalar value wrapper.
 * 
 * @author Andrea Funto'
 */
public class TypedScalar<E> implements Copyable {

	/**
	 * The wrapped object.
	 */
	private E element;
	
	/**
	 * Constructor.
	 */
	public TypedScalar() {
	}
	
	/**
	 * Constructor.
	 *
	 * @param object
	 *   the wrapped object.
	 */
	public TypedScalar(E element) {
		this.element = element;
	}

	/**
	 * Returns the value of the wrapped object.
	 *	
	 * @return 
	 *   the wrapped object.
	 */
	public E get() {
		return element;
	}

	/**
	 * Sets the new value of the object.
	 *	
	 * @param element 
	 *   the new value of the object to set.
	 */
	public void set(E element) {
		this.element = element;
	}
	
	/**
	 * Performs a deep copy of the {@code Scalar} object if supported by the
	 * wrapped type, a shallow copy otherwise.
	 * 
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object clone() throws CloneNotSupportedException {
		E wrapped = element;
		if(element instanceof Copyable) {
			wrapped = (E)((Copyable)element).clone();
		}
		return new TypedScalar(wrapped);
	}
}
