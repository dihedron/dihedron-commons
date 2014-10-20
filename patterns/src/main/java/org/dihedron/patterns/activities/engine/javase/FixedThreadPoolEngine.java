/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Activities library ("Activities").
 *
 * Activities is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Activities is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Activities. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.patterns.activities.engine.javase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.dihedron.patterns.activities.engine.ActivityCallable;
import org.dihedron.patterns.activities.engine.ParallelEngine;
import org.dihedron.patterns.activities.types.ActivityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes the given activities as background operations in a thread pool, 
 * leveraging JavaSE standard concurrent facilities (executor services etc.).

 * @author Andrea Funto'
 */
public class FixedThreadPoolEngine extends ParallelEngine {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FixedThreadPoolEngine.class);

	/**
	 * The ExecutorService (from java.lang.concurrent).
	 */
	private ExecutorService service;
	
	/**
	 * The number of threads in the pool.
	 */
	private int threads = 0;
	
	/**
	 * Constructor; creates a new cached thread pool with one thread per CPU core.
	 */
	public FixedThreadPoolEngine() {		
		this(Runtime.getRuntime().availableProcessors());
		logger.info("using one thread per available logical CPU core: {}", threads);
	}
	
	/**
	 * Constructor. Remember to invoke dispose() when the object isn't needed 
	 * anymore.
	 *
	 * @param service
	 *   the {@code ExecutorService} that will asynchronously process the task 
	 *   in background. 
	 */
	public FixedThreadPoolEngine(int threads) {
		if(threads > 0) {			
			this.threads = threads;			
		} else {
			this.threads = Runtime.getRuntime().availableProcessors();
		}
		
		logger.info("using {} threads in the pool");
	}
	
	/**
	 * Assigns the given activity to the thread pool for background execution.
	 * 
	 * @param callable
	 *   the callable object for the {@code Activity} to be executed asynchronously.
	 * @return
	 *   the activity's {@code Future} object, for result retrieval.
	 * @see 
	 *   org.dihedron.patterns.activities.concurrent.ActivityExecutor#submit(org.dihedron.patterns.activities.Activity)
	 */
	@Override
	protected Future<ActivityData> submit(ActivityCallable callable) {
		if(service == null) {
			service = Executors.newFixedThreadPool(threads);
		}
		logger.trace("submitting activity to thread pool...");
		return service.submit(callable);
	}

	/**
	 * Closes the task executor, releasing all associated resources.
	 */
	public void dispose() {
		service.shutdown();		
	}	
}
