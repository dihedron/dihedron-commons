/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.base;

import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.engine.SequentialEngine;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class SequentialActivity extends CompoundActivity {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(SequentialActivity.class);

	/**
	 * Constructor; as a side effect, it initialises the execution engine.
	 */
	public SequentialActivity() {
		logger.trace("initialising sequential activity");
		setEngine(new SequentialEngine());
	}

	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		throw new ActivityException("Not implemented");
	}
}
