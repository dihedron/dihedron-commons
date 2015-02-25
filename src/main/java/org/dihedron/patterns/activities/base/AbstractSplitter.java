/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.base;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.ActivityData;
import org.dihedron.patterns.activities.types.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public abstract class AbstractSplitter extends AbstractActivity {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbstractSplitter.class);

	/**
	 * @see org.dihedron.patterns.activities.Activity#execute(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Vector)
	 */
	@Override
	public ActivityData perform(ActivityContext context, ActivityData data) throws ActivityException {
		if(data instanceof Scalar) {
			return split(context, (Scalar)data);
		}
		logger.error("cardinality mismatch: a splitter should only be invoked on a scalar object");
		throw new ActivityException("Cardinality mismatch: a splitter should only be invoked on a scalar object");
	}
}
