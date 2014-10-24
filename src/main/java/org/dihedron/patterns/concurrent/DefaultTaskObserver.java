/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.concurrent;

import org.dihedron.core.License;


/**
 * A do-nothing implementation of the TaskObserver interface.
 * 
 * @author Andrea Funto'
 */
@License
public class DefaultTaskObserver<T> implements TaskObserver<T> {

	/**
	 * @see org.dihedron.patterns.concurrent.TaskObserver#onTaskStarting(org.dihedron.patterns.concurrent.Task)
	 */
	@Override
	public void onTaskStarting(Task<T> task) {
	}

	/**
	 * @see org.dihedron.patterns.concurrent.TaskObserver#onTaskStarted(org.dihedron.patterns.concurrent.Task)
	 */
	@Override
	public void onTaskStarted(Task<T> task) {
	}

	/**
	 * @see org.dihedron.patterns.concurrent.TaskObserver#onTaskComplete(org.dihedron.patterns.concurrent.Task, java.lang.Object)
	 */
	@Override
	public void onTaskComplete(Task<T> task, T result) {
	}

}
