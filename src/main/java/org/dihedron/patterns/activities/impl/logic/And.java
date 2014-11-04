/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.activities.impl.logic;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.AbstractAggregator;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.exceptions.InvalidArgumentException;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class And extends AbstractAggregator {
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(And.class);

	/**
	 * @see org.dihedron.patterns.activities.base.AbstractAggregator#aggregate(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Vector)
	 */
	@Override
	protected Scalar aggregate(ActivityContext context, Vector vector) throws ActivityException {
		if(vector == null || vector.isEmpty()) {
			return new Scalar((Object)false);
		}
		boolean and = true;
		for(Object object : vector) {
			if(object instanceof Boolean) {
				Boolean bool = (Boolean)object;
				and = and && bool;
			} else {
				// TODO: implement the type mapper!!!!
				logger.error("arguments to logic operators must be boolean, while an instance of {} was received", object.getClass().getName());
				throw new InvalidArgumentException("Arguments to boolean operators must be boolean");
			}
			if(!and) break;
		}
		return new Scalar(and);
	}

}
