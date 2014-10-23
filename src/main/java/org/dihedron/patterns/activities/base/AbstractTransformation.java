/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.base;


import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.ActivityData;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;

/**
 * @author Andrea Funto'
 */
public abstract class AbstractTransformation extends AbstractActivity {

	/**
	 * @see org.dihedron.patterns.activities.Activity#perform(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.ActivityData)
	 */
	@Override
	public ActivityData perform(ActivityContext context, ActivityData data) throws ActivityException {
		if(data instanceof Scalar) {
			return transform(context, (Scalar)data);
		} else {
			return transform(context, (Vector)data);
		}
	}
	
	@Override
	protected abstract Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException;
	
	@Override
	protected abstract Vector transform(ActivityContext context, Vector vector) throws ActivityException;	
	
}
