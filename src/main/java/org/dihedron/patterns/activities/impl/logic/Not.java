/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.impl.logic;

import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.Transformation;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.exceptions.InvalidArgumentException;
import org.dihedron.patterns.activities.types.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class Not extends Transformation {
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Not.class);

	/**
	 * @see org.dihedron.patterns.activities.base.AbstractTransformation#transform(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Scalar)
	 */
	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		if(!(scalar.get() instanceof Boolean)) {
			// TODO: implement the type mapper!!!!
			logger.error("arguments to logic operators must be boolean, while an instance of {} was received", scalar.get().getClass().getName());
			throw new InvalidArgumentException("Arguments to boolean operators must be boolean"); 
		}
		return new Scalar(!((Boolean)scalar.get()));
	}
}
