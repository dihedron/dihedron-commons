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
package org.dihedron.activities.base;

import org.dihedron.activities.Activity;
import org.dihedron.activities.ActivityContext;
import org.dihedron.activities.engine.ActivityInfo;
import org.dihedron.activities.exceptions.ActivityException;
import org.dihedron.activities.types.ActivityData;
import org.dihedron.activities.types.Scalar;
import org.dihedron.commons.TypedVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
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
