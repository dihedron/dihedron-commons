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
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TaskQueue<T> {

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TaskQueue.class);
	
	/**
	 * Adds an observer to the current thread's observers of task execution life
	 * cycle.
	 * 
	 * @param observer
	 */
	public static void addObserver(TaskObserver<?> observer) {
		if(observer != null) {
			if(tls.get().observers == null) {
				tls.get().observers = new ArrayList<TaskObserver<?>>();
			}
		}
		tls.get().observers.add(observer);
	}
	
	public static void addObservers(TaskObserver<?>... observers) {
		if(observers != null) {
			for(TaskObserver<?> observer : observers) {
				tls.get().observers.add(observer);
			}
		}
	}
	
	/**
	 * Removes all registered observers.
	 * 
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public static void clearObservers() {
		if(tls.get().observers != null) {
			tls.get().observers.clear();
		}
	}
	
	/**
	 * Enqueues a task
	 * @param task
	 */
	public static void enqueueTask(Task<?> task) {
		
	}
	
	/**
	 * The per-thread data: this allows a single executor to handle tasks submitted by
	 * multiple client threads using a single thread pool while keeping individual tasks
	 * separated. Thus, a single thread pool can be used to handle tasks submitted 
	 * by many client threads, each of which can then wait only on its own tasks. 
	 */
	private static ThreadLocal<TaskQueue<?>> tls = new ThreadLocal<TaskQueue<?>>() {
		@SuppressWarnings("rawtypes")
		@Override protected TaskQueue<?> initialValue() {
			logger.debug("initialising task queue for thread {}", Thread.currentThread().getId());
			return new TaskQueue();
		}
	};

	/**
	 * The queue used to synchronise task execution.
	 */
	private BlockingQueue<Integer> queue;	
	
	/**
	 * A set of observers which will be notified at various stages of the tasks
	 * life cycle (such as when they're about to start, or complete).
	 */
	private List<TaskObserver<?>> observers;
	
	/**
	 * Private constructor, initialises the internal data structures to provide per-thread
	 * synchronisation of background tasks..
	 */
	private TaskQueue() {
		this.queue = new LinkedBlockingQueue<Integer>();
	}
}
