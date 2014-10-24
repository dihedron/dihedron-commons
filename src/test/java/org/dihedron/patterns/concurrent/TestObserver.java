/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.concurrent;

import org.dihedron.patterns.concurrent.Task;
import org.dihedron.patterns.concurrent.TaskObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class TestObserver implements TaskObserver<String> {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(TestObserver.class);


	/**
	 * @see org.dihedron.patterns.concurrent.TaskObserver#onTaskStarting(org.dihedron.patterns.concurrent.Task)
	 */
	@Override
	public void onTaskStarting(Task<String> task) {
		logger.debug("task '{}' starting", task);
	}

	/**
	 * @see org.dihedron.patterns.concurrent.TaskObserver#onTaskStarted(org.dihedron.patterns.concurrent.Task)
	 */
	@Override
	public void onTaskStarted(Task<String> task) {
		logger.debug("task '{}' started", task);			
	}

	/**
	 * @see org.dihedron.patterns.concurrent.TaskObserver#onTaskComplete(org.dihedron.patterns.concurrent.Task, java.lang.Object)
	 */
	@Override
	public void onTaskComplete(Task<String> task, String result) {
		logger.debug("result of task '{}' is '{}'", task, result);	
	}
}
