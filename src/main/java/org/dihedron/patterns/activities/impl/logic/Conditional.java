/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.impl.logic;

import org.dihedron.core.values.Booleans;
import org.dihedron.patterns.activities.Activity;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.AbstractTransformation;
import org.dihedron.patterns.activities.base.Transformation;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runs a test activity, which is expected to return a boolean scalar/vector value,
 * or an object that is convertible to a boolean value according to the mapping
 * rules in defined by the {@code Booleans} utility class. 
 *  
 * @author Andrea Funto'
 */
public class Conditional extends Transformation {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Conditional.class);
	
	/**
	 * The activity that will perform the test on the input values.
	 */
	private Activity testActivity;
	
	/**
	 * The activity that will be executed if the test was successful ({@code true}).
	 */
	private Activity successActivity;
	
	/**
	 * The activity that will be executed if the test did not pass ({@code false}).
	 */
	private Activity failureActivity;
	
	/**
	 * Sets the activity that will decide on the execution of the following 
	 * activities. The test will be run on the whole input, be it a scalar or a
	 * vector, and each output value will decide on the execution on its
	 * reference value. 
	 * 
	 * @param activity
	 *   the test activity.
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public Conditional setTestActivity(AbstractTransformation activity) {
		this.testActivity = activity;
		return this;
	}

	/**
	 * Sets the activity that will be executed on the input value if the test 
	 * proved successful ({@code true}); the activity will be run against all 
	 * values in the input.
	 * 
	 * @param activity
	 *   the activity to be run if the test was successful ({@code true}: this is 
	 *   the "then" case in a typical "if... then... else..." scenario. 
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public Conditional setSuccessActivity(AbstractTransformation activity) {
		this.successActivity = activity;
		return this;
	}
	
	/**
	 * Sets the activity that will be executed on the input value if the test 
	 * proved unsuccessful ({@code false}); the activity will be run against all 
	 * values in the input. This activity is optional: if not specified, the
	 * input will be simply passed on to the next activity in the chain with no
	 * alteration.
	 * 
	 * @param activity
	 *   the activity to be run if the test was unsuccessful ({@code false}: this 
	 *   is the "else" case in a typical "if... then... else..." scenario. 
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public Conditional setFailureActivity(AbstractTransformation activity) {
		this.failureActivity = activity;
		return this;
	}
	
	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		Scalar result = (Scalar)testActivity.perform(context, scalar);
		if(Booleans.toBoolean(result.get())) {
			logger.trace("executing 'then' case activity");
			return (Scalar)successActivity.perform(context, scalar);
		} else {
			if(failureActivity != null) {
				logger.trace("executing 'else' case activity");
				return (Scalar)failureActivity.perform(context, scalar);
			}
		}
		logger.trace("returning input object as no 'else' case activity was available");
		return scalar;
	}
}
