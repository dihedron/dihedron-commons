/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
 */

package org.dihedron.patterns.concurrent;


/**
 * The interface that all observers must implement in order to receive notifications
 * about the progress of each task. To inherit dummy implementations of the
 * callback methods, subclasses can extend DefaultTaskObserver.
 * 
 * @author Andrea Funto'
 */
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
