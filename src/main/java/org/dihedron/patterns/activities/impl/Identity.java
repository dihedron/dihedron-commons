/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.impl;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.AbstractTransformation;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;

/**
 * @author Andrea Funto'
 */
@License
public class Identity extends AbstractTransformation {

	/**
	 * @see org.dihedron.patterns.activities.base.AbstractTransformation#transform(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Scalar)
	 */
	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		return scalar;
	}

	/**
	 * @see org.dihedron.patterns.activities.base.AbstractTransformation#transform(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Vector)
	 */
	@Override
	protected Vector transform(ActivityContext context, Vector vector) throws ActivityException {		
		return vector;
	}
}
