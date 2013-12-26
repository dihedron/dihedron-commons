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

package org.dihedron.commons.concurrent;

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


public class TaskExecutor<T> {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(TaskExecutor.class);
	
	/**
	 * The actual executor service.
	 */
	private ExecutorService executor;
	
	/**
	 * The queue used to synchronise task execution.
	 */
	private BlockingQueue<Integer> queue;
	
	/**
	 * Constructor.
	 *
	 * @param executor
	 *   the actual executor service.
	 */
	public TaskExecutor(ExecutorService executor) {
		this.executor = executor;
		this.queue = new LinkedBlockingQueue<Integer>();
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
				task.setId(i++);
				task.setQueue(queue);
				futures.add(executor.submit(task));
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
				task.setId(i++);
				task.setQueue(queue);
				futures.add(executor.submit(task));
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
	public List<T> waitForAll(List<Future<T>> futures, TaskObserver<T>... observers) throws InterruptedException, ExecutionException {
		Map<Integer, T> results = new HashMap<Integer, T>();
		int count = futures.size();
		while(count-- > 0) {
			int id = queue.take();
			logger.debug("task '{}' complete (count: {}, queue: {})", id, count, queue.size());
			T result = futures.get(id).get();
			results.put(id, result);
			for(TaskObserver<T> observer : observers) {
				observer.onTaskComplete(result);
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
				observer.onTaskComplete(result);
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

	/*
	public static void test() throws Exception {

		long start = System.currentTimeMillis();
		
		Random random = new SecureRandom();
		
		
		
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
				
		List<Future<String>> results = new ArrayList<Future<String>>();

		synchronized(queue) {
			for (int i = 0; i < NUMBER_OF_TASKS; i++) {
				Callable<String> worker = new TaskImpl(i, queue, random.nextInt(MAX_MS_TO_WAIT));
				Future<String> submit = executor.submit(worker);
				results.add(submit);							
			}
		}
		
		int count = NUMBER_OF_TASKS;
		while(count-- > 0) {
			int index = queue.take();
			System.out.println("----> notified of completion of task " + index + "(count: " + count + ", queue: " + queue.size() + ")");
			System.out.println("       + result: '" + results.get(index).get() + "'");
		}
		
		System.out.println("all tasks complete in " + (System.currentTimeMillis() - start) + " ms, exiting!");

		executor.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
		for(int i = 0; i < NUMBER_OF_TESTS; ++i) {
			System.out.println("-------------------------------------------------");
			test();
		}
	}
	*/
	
}