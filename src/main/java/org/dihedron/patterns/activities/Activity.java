/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.ActivityData;


/**
 * The base interface for all activities; activities can be asked to perform 
 * their duties on a set of elements (a vector) or on a single element (a scalar);
 * an activity can optionally implement only one of the two scenarios and throw 
 * an {@code ActivityException} on the unsupported one; moreover implementing
 * activities can leave the implementation of the more complex vector scenario
 * to a base class and simply implement the scalar one; the parent class will 
 * take care of delegating the actual task implementation to the child class, 
 * through its scalar method, while retaining control on how the overall processing 
 * occurs on the vector. 
 * 
 * @author Andrea Funto'
 */
@License
public interface Activity {
	
	/**
	 * Returns the activity's identifier.
	 * 
	 * @return
	 *   the activity's identifier.
	 */
	String getId();
	
	/**
	 * This is the business logic implementation of all operations; it can 
	 * operate on a scalar or a vector data type, and it should return either
	 * a vector of values or a single scalar value; extending classes may want 
	 * to implement these kinds of processing on different methods.
	 *  
	 * @param context
	 *   the activity context, to store state and propagate information along 
	 *   the processing pipeline.
	 * @param data
	 *   the data to operate upon, either a {@link #org.dihedron.types.Vector} 
	 *   or a {@link #org.dihedron.types.Scalar}.
	 * @return
	 *   the resulting data, either a {@link #org.dihedron.types.Vector} or a 
	 *   {@link #org.dihedron.types.Scalar}.
	 * @throws ActivityException
	 */
	ActivityData perform(ActivityContext context, ActivityData data) throws ActivityException;
	
}
