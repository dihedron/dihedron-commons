/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine;

import java.io.Serializable;

import org.dihedron.patterns.activities.Activity;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.types.ActivityData;

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
