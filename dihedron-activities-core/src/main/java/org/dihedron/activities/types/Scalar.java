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
package org.dihedron.activities.types;

import org.dihedron.activities.Copyable;
import org.dihedron.commons.TypedScalar;


/**
 * An object representing a scalar element.
 * 
 * @author Andrea Funto'
 */
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
