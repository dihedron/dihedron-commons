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
package org.dihedron.activities.engine;

import java.io.Serializable;

import org.dihedron.activities.Activity;
import org.dihedron.activities.ActivityContext;
import org.dihedron.activities.types.ActivityData;

/**
 * @author Andrea Funto'
 */
public class ActivityInfo implements Serializable {

	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = -8955872751886144853L;

	/**
	 * The activity this info is about.
	 */
	private Activity activity;
	
	/**
	 * The element of data the activity will be run against.
	 */
	private ActivityData data;
	
	/**
	 * The context of execution for the given activity.
	 */
	private ActivityContext context;
	
	/**
	 * Constructor.
	 */
	public ActivityInfo() {
	}
	
	/**
	 * Constructor.
	 *
	 * @param activity
	 *   the activity.
	 * @param data
	 *   the data on which the activity will run.
	 * @param context
	 *   the context the activity will use to access the execution state and
	 *   the underlying runtime services.
	 */
	public ActivityInfo(Activity activity, ActivityData data, ActivityContext context) {
		setActivity(activity);
		setData(data);
		setContext(context);
	}

	/**
	 * Returns the activity.
	 *
	 * @return 
	 *   the activity.
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Sets the value of the activity.
	 *
	 * @param activity 
	 *   the activity to set.
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public ActivityInfo setActivity(Activity activity) {
		this.activity = activity;
		return this;
	}

	/**
	 * Returns the element of data.
	 *
	 * @return 
	 *   the element of data.
	 */
	public ActivityData getData() {
		return data;
	}

	/**
	 * Sets the value of the element of data.
	 *
	 * @param data 
	 *   the element of data to set.
	 * @return
	 *   the object itself, to enable method chaining.   
	 */
	public ActivityInfo setData(ActivityData data) {
		this.data = data;
		return this;
	}

	/**
	 * Returns the context.
	 *
	 * @return 
	 *   the context.
	 */
	public ActivityContext getContext() {
		return context;
	}

	/**
	 * Sets the value of the context.
	 *
	 * @param context 
	 *   the context to set.
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public ActivityInfo setContext(ActivityContext context) {
		this.context = context;
		return this;
	}
}
