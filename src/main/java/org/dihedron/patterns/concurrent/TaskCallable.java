/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Internal class used to adapt the task at hand with the Callable interface and
 * provide some of the internal workings of the framework.
 * 
 * @author Andrea Funto'
 */
class TaskCallable<T> implements Callable<T> {
	
	/**
	 * The constant representing an unidentified task;
	 */
	public static final int UNDEFINED_ID = -1;
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TaskCallable.class);
	
	/**
	 * The actual task being executed.
	 */
	private Task<T> task;
	
	/**
	 * The task identifier: it can be used to identify the task's future when
	 * the execution is complete.
	 */
	private int id = UNDEFINED_ID;
	
	/**
	 * The queue used to signal that the task is complete.
	 */
	private BlockingQueue<Integer> queue;
	
	/**
	 * Constructor.
	 *
	 * @param id
	 *   the task index, to associate it with its result.
	 * @param queue
	 *   a reference to the queue used to notify about the task's completion.
	 * @param task
	 *   the actual task being executed.
	 */
	public TaskCallable(int id, BlockingQueue<Integer> queue, Task<T> task) {
		this.id = id;
		this.queue = queue;
		this.task = task;
	}
			
	/**
	 * Actual thread's workhorse method; it implements a "code around" pattern, 
	 * and delegates actual business logic to subclasses, while retaining the
	 * logic necessary to signal completion to the caller.
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public T call() throws Exception {
		logger.debug("task '{}' starting", id);		
		try {
			// do the real work 
			return task.execute();
		} finally {
			// signal that the task is complete
			logger.debug("task '{}' is complete (queue size: {})", id, queue.size());
			queue.offer(id);			
		}
	}
}