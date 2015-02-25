/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.base;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.Activity;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.TypedVector;
import org.dihedron.patterns.activities.engine.ActivityInfo;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.ActivityData;
import org.dihedron.patterns.activities.types.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class CompoundActivity extends Transformation {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(CompoundActivity.class);
	
	/**
	 * The list of sub-activities.
	 */
	protected TypedVector<Activity> activities = new TypedVector<Activity>();
	
	/**
	 * Adds an activity to the list of sub-activities.
	 * 
	 * @param activity
	 *   the sub-activity to add.
	 * @return
	 *   the activity itself, to enable method chaining.
	 */
	public CompoundActivity addActivity(Activity activity) {
		if(activity != null) {
			logger.trace("adding sub-activity '{}' to list", activity.getId());
			activities.add(activity);
		}
		return this;
	}
	
	/**
	 * Clears all the sub-activities.
	 */
	public void clearActivities() {
		activities.clear();
	}
	
	/**
	 * Runs a set of sub-activities in a sequence (one at a time), with no parallelism.
	 * As soon as one of the activities throws an exception the whole processing 
	 * is aborted and the exception is propagated to its wrapping activities.
	 *  
	 * @see org.dihedron.tasks.Task#execute(org.dihedron.tasks.ExecutionContext)
	 */
	@Override
	public ActivityData perform(ActivityContext context, ActivityData data) throws ActivityException {
		
		TypedVector<ActivityInfo> infos = new TypedVector<ActivityInfo>();
		int i = 0;
		for(Activity activity : activities) {
			logger.trace("adding activity number {} with id '{}'...", i, activity.getId());
			ActivityInfo info = new ActivityInfo();
			info
				.setActivity(activity)
				.setContext(context)
				//.setData(i == 0 ? data : null);
				.setData(data);
			infos.add(info);
			logger.trace("... activity {} added!", i);
			++i;
		}
		logger.trace("launching activity execution...");
		return engine.execute(infos);
	}

	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		throw new ActivityException("Not implemented");
	}	
}
