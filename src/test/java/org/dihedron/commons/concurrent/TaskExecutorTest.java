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

import static org.junit.Assert.fail;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class TaskExecutorTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(TaskExecutorTest.class);

	private static final int THREAD_POOL_SIZE = 50;
	
	private static final int NUMBER_OF_TASKS = 100;
	
	private static final int NUMBER_OF_TESTS = 10;
	
	private static final int MAX_MS_TO_WAIT = 20;
	
	private Random random;
	
	private ExecutorService executor;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		random = new SecureRandom();
		executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}
	
	@After
	public void tearDown() throws Exception {
		executor.shutdown();
	}

	@Test
	public void testWaitForAll() {

		for(int j = 0; j < NUMBER_OF_TESTS; ++j) {
			try {
				List<Task<String>> tasks = new ArrayList<Task<String>>();
				
				for (int i = 0; i < NUMBER_OF_TASKS; i++) {
					tasks.add(new TestTask(random.nextInt(MAX_MS_TO_WAIT)));
				}
	
				TaskExecutor<String> engine = new TaskExecutor<String>(executor);
				List<Future<String>> futures = engine.execute(tasks);
				List<String> results = engine.waitForAll(futures, new TestObserver());
				
			} catch (Exception e) {
				logger.error("error...", e);
				fail("got exception " + e.getMessage());			
			}
		}		
	}

}
