/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.activities.types;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.Copyable;
import org.dihedron.patterns.activities.TypedScalar;


/**
 * An object representing a scalar element.
 * 
 * @author Andrea Funto'
 */
@License
public class Scalar extends TypedScalar<Object> implements ActivityData, Copyable {

	/**
	 * Creates an uninitialised scalar element.
	 */
	public Scalar() {
		super();
	}
	
	/**
	 * Creates a scalar element wrapping the given object.
	 * 
	 * @param object
	 *   the object being wrapped as a scalar element.
	 */
	public Scalar(Object object) {
		super(object);
	}
	
	/**
	 * A deprecated method used for backward compatibility, please replace with
	 * {@link #get()} as soon as possible, for it will be removed.
	 * 
	 * @return
	 *   the wrapped, untyped object.
	 */
	@Deprecated
	public Object getObject() {
		return super.get();
	}
}
