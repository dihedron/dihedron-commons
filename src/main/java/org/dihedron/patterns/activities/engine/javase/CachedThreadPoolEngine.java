/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine.javase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.dihedron.core.License;
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
@License
public class CachedThreadPoolEngine extends ParallelEngine {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CachedThreadPoolEngine.class);

	/**
	 * The ExecutorService (from java.lang.concurrent).
	 */
	private ExecutorService service = Executors.newCachedThreadPool();

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
