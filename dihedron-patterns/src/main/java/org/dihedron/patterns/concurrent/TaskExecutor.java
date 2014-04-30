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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The engine that takes care of executing the tasks asynchronously (in parallel), 
 * waiting for their completion and optionally notifying observers of their 
 * advancement.
 * 
 * @author Andrea Funto'
 */
public class TaskExecutor<T> {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);
	
	/**
	 * The actual executor service.
	 */
	private ExecutorService executor;
	
	/**
	 * The queue used to synchronise task execution.
	 */
	private BlockingQueue<Integer> queue;
	
	/**
	 * The list of tasks being executed.
	 */
	private List<Task<T>> tasks = new ArrayList<Task<T>>();
	
	/**
	 * The (optional) list of task observers.
	 */
	private List<TaskObserver<T>> observers = new ArrayList<TaskObserver<T>>();
	
	/**
	 * Constructor.
	 *
	 * @param executor
	 *   the actual executor service.
	 * @param observers
	 *   an optional list of observers that will be notified of the several phases 
	 *   in a task execution life cycle. 
	 */
	public TaskExecutor(ExecutorService executor, TaskObserver<T>... observers) {
		this.executor = executor;
		this.queue = new LinkedBlockingQueue<Integer>();
		addObservers(observers);
	}

	/**
	 * Adds task observers to the set of registered observers.
	 *   
	 * @param observers
	 *   an list of observers that will be notified of the several phases in a 
	 *   task execution life cycle. 
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public final TaskExecutor<T> addObservers(TaskObserver<T>... observers) {
		if(observers != null) {
			for(TaskObserver<T> observer : observers) {
				this.observers.add(observer);
			}
		}
		return this;
	}
	
	/**
	 * Removes all registered observers.
	 * 
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public TaskExecutor<T> clearObservers() {
		this.observers.clear();
		return this;
	}
	
	/**
	 * Starts the given set of tasks asynchronously, returning their futures.
	 * 
	 * @param tasks
	 *   the set of tasks to execute.
	 * @return
	 *   the corresponding set of futures.
	 */
	public List<Future<T>> execute(Task<T>... tasks) {
		List<Future<T>> futures = new ArrayList<Future<T>>();
		synchronized(queue) {
			int i = 0;
			for(Task<T> task : tasks) {
				if(task !=  null) {
					this.tasks.add(task);
					TaskCallable<T> callable = new TaskCallable<T>(i++, queue, task);
					for(TaskObserver<T> observer : observers) {
						observer.onTaskStarting(task);
					}
					futures.add(executor.submit(callable));
					for(TaskObserver<T> observer : observers) {
						observer.onTaskStarted(task);
					}
				}
			}
		}		
		return futures;
	}
	
	/**
	 * Starts the given set of tasks asynchronously, returning their futures.
	 * 
	 * @param tasks
	 *   the set of tasks to execute.
	 * @return
	 *   the corresponding set of futures.
	 */
	public List<Future<T>> execute(List<Task<T>> tasks) {
		List<Future<T>> futures = new ArrayList<Future<T>>();
		synchronized(queue) {
			int i = 0;
			for(Task<T> task : tasks) {
				if(task != null) {
					this.tasks.add(task);
					TaskCallable<T> callable = new TaskCallable<T>(i++, queue, task);
					for(TaskObserver<T> observer : observers) {
						observer.onTaskStarting(task);
					}
					futures.add(executor.submit(callable));
					for(TaskObserver<T> observer : observers) {
						observer.onTaskStarted(task);
					}
				}
			}
		}		
		return futures;
	}
	
	/**
	 * Waits for all tasks to complete before returning; if observers are provided,
	 * they are called each time a task completes.
	 * 
	 * @param futures
	 *   the list of futures to wait for.
	 * @param observers
	 *   an optional set of observers.
	 * @return
	 *   the list of results, once all tasks are done.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<T> waitForAll(List<Future<T>> futures) throws InterruptedException, ExecutionException {
		Map<Integer, T> results = new HashMap<Integer, T>();
		int count = futures.size();
		while(count-- > 0) {
			int id = queue.take();
			logger.trace("task '{}' complete (count: {}, queue: {})", id, count, queue.size());
			T result = futures.get(id).get();
			results.put(id, result);
			for(TaskObserver<T> observer : observers) {
				observer.onTaskComplete(tasks.get(id), result);
			}
		}
		
		logger.debug("all tasks completed");
		List<T> values = new ArrayList<T>();
		for(int i = 0; i < results.size(); ++i) {
			values.add(results.get(i));
		}
		return values;
	}

	/**
	 * Waits until the first task completes, then calls the (optional) observers 
	 * to notify the completion and returns the result.
	 * 
	 * @param futures
	 *   the list of futures to wait for.
	 * @param observers
	 *   an optional set of observers.
	 * @return
	 *   the result of the first task to complete.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public T waitForAny(List<Future<T>> futures, TaskObserver<T>... observers) throws InterruptedException, ExecutionException {
		int count = futures.size();
		while(count-- > 0) {
			int id = queue.take();
			logger.debug("task '{}' complete (count: {}, queue: {})", id, count, queue.size());
			T result = futures.get(id).get();
			for(TaskObserver<T> observer : observers) {
				observer.onTaskComplete(tasks.get(id), result);
			}
			return result;
		}
		return null;
	} 
	
	/**
	 * Closes the task executor, releasing all associated resources.
	 */
	public void dispose() {
		executor.shutdown();		
	}
}