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
package org.dihedron.patterns.activities.base;

import org.dihedron.patterns.activities.Activity;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;


/**
 * The base abstract class providing facility method for id handling and utility 
 * methods to describe the four possible operations: transformation (of a vector 
 * or a scalar), aggregation and splitting. Subclasses will implement these methods
 * and define the routing logic to the implementation methods in their version of
 * {@link #perform(ActivityContext, org.dihedron.patterns.activities.types.ActivityData)}.
 * 
 * @author Andrea Funto'
 */
public abstract class AbstractActivity implements Activity {

	/**
	 * The {@code Activity} id.
	 */
	protected String id;
		
	/**
	 * Sets the value of the {@code Activity} id.
	 * 
	 * @param id
	 *   the activity id.
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the {@code Activity} id.
	 * 
	 * @return
	 *   the {@code Activity} id.
	 * @see 
	 *   org.dihedron.patterns.activities.Activity#getId()
	 */
	@Override
	public String getId() {
		return (id != null ? id : "-") + "@" + getClass().getSimpleName();
	}
	
	/**
	 * The actual activity implementation on data to be considered as a vector
	 * of elements. 
	 * Note: subclasses may decide to operate on a {@code Vector} object
	 * as if it were a scalar: in that case the right method to invoke is
	 * {@link #transform(ActivityContext, Scalar)}.
	 * 
	 * @param context
	 *   the execution context, used to provide access to the underlying runtime
	 *   environment, and to store state variables.
	 * @param vector
	 *   a vector of elements on which the activity should be run; if the activity 
	 *   maintains the same cardinality (it's neither an aggregator nor a splitter)
	 *   it can be run in parallel on all the inputs.
	 * @return
	 *   an object representing the result of the operation; it can be either a
	 *   vector or a scalar.
	 * @throws ActivityException
	 *   if any error occurs during the processing, to signal that the overall
	 *   execution should be aborted unless a wrapping task stops.
	 */
	protected Vector transform(ActivityContext context, Vector vector) throws ActivityException {
		throw new ActivityException("Not implemented");
	}
	
	/**
	 * The actual activity implementation for scalar elements.
	 * 
	 * @param context
	 *   the execution context, used to provide access to the underlying runtime
	 *   environment, and to store state variables.
	 * @param scalar
	 *   a single, scalar element on which the activity should be run.
	 * @return
	 *   an object representing the result of the operation; it can be either a
	 *   vector or a scalar.
	 * @throws ActivityException
	 *   if any error occurs during the processing, to signal that the overall
	 *   execution should be aborted unless a wrapping task stops.
	 */	
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		throw new ActivityException("Not implemented");
	};
	
	protected Vector split(ActivityContext context, Scalar scalar) throws ActivityException {
		throw new ActivityException("Not implemented");
	}
	
	protected Scalar aggregate(ActivityContext context, Vector vector) throws ActivityException {
		throw new ActivityException("Not implemented");
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("{'class': '");
		result.append(getClass().getCanonicalName());
		result.append("'");
		
		if (id != null) {
			result.append(", 'id': '");
			result.append(id);
			result.append("'");
		}

		result.append("}");
		
		return result.toString();
	}
}
