/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities;



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
