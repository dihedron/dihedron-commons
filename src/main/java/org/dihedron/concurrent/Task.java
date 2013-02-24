/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
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
package org.dihedron.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Task<T> implements Callable<T> {
	
	/**
	 * The constant representing an unidentified task;
	 */
	public final static int UNDEFINED_ID = -1;
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(Task.class);
	
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
	 * Sets the Task's id; it should be used only by the TaskExecutor, never by 
	 * clients, lest the internal workings of the executor be broken or compromised.
	 * 
	 * @param id
	 *   the unique id of the task.
	 */
	void setId(int id) {
		this.id = id;
	}
	
	/**
	 * The queue used to notify when the task is complete.
	 * 
	 * @param queue
	 *   the queue used to notify the task's completion.
	 */
	void setQueue(BlockingQueue<Integer> queue) {
		this.queue = queue;
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
			return execute();
		} catch(InterruptedException e) {
			logger.error("thread interrupted while executing task '{}'", id);
			throw e;
		} finally {
			// signal that the task is complete
			logger.debug("task '{}' is complete (queue size: {})", id, queue.size());
			queue.offer(id);			
		}
	}	
	
	/**
	 * Actual business logic method; subclasses must implement this in order to 
	 * be run in an ExecutorService thread.
	 * 
	 * @return
	 *   a return value.
	 * @throws Exception
	 */
	protected abstract T execute() throws Exception;
}