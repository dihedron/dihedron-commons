/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.concurrent;

import org.dihedron.core.License;


/**
 * The interface that all observers must implement in order to receive notifications
 * about the progress of each task. To inherit dummy implementations of the
 * callback methods, subclasses can extend DefaultTaskObserver.
 * 
 * @author Andrea Funto'
 */
@License
public interface TaskObserver<T> {

	/**
	 * Called when a task is about to be submitted.
	 * 
	 * @param task
	 *   the task being submitted; please note that access to the given task is
	 *   <b>not</b> synchronised, so if the observer will access task methods and 
	 *   fields, the task must guarantee that it is able to support concurrent 
	 *   access.
	 */
	void onTaskStarting(Task<T> task);

	/**
	 * Called when a task has just been submitted for execution.
	 * 
	 * @param task
	 *   the submitted task; please note that access to the given task is
	 *   <b>not</b> synchronised, so if the observer will access task methods and 
	 *   fields, the task must guarantee that it is able to support concurrent 
	 *   access.
	 */
	void onTaskStarted(Task<T> task);
	
	/**
	 * Called when a task has completed its execution.
	 * 
	 * @param task
	 *   the completed task; please note that access to the given task is
	 *   <b>not</b> synchronised, so if the observer will access task methods and 
	 *   fields, the task must guarantee that it is able to support concurrent 
	 *   access.
	 * @param result
	 *   the result of the task execution.
	 */	
	void onTaskComplete(Task<T> task, T result);

}
