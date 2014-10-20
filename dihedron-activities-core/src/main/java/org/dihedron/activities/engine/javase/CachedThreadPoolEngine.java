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
package org.dihedron.activities.engine.javase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.dihedron.activities.engine.ActivityCallable;
import org.dihedron.activities.engine.ParallelEngine;
import org.dihedron.activities.types.ActivityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes the given activities as background operations in a thread pool, 
 * leveraging JavaSE standard concurrent facilities (executor services etc.).

 * @author Andrea Funto'
 */
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
	 *   org.dihedron.activities.concurrent.ActivityExecutor#submit(org.dihedron.activities.Activity)
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
