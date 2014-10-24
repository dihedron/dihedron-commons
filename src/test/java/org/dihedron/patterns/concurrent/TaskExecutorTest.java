/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.concurrent;

import static org.junit.Assert.fail;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.dihedron.core.License;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class TaskExecutorTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(TaskExecutorTest.class);

	private static final int THREAD_POOL_SIZE = 10;
	
	private static final int NUMBER_OF_TASKS = 50;
	
	private static final int NUMBER_OF_TESTS = 2;
	
	private static final int MAX_MS_TO_WAIT = 5;
	
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
					tasks.add(new TestTask(i, random.nextInt(MAX_MS_TO_WAIT)));
				}
	
				TaskExecutor<String> engine = new TaskExecutor<String>(executor).addObservers(new TestObserver());
				List<Future<String>> futures = engine.execute(tasks);
				@SuppressWarnings("unused")
				List<String> results = engine.waitForAll(futures);
				
			} catch (Exception e) {
				logger.error("error...", e);
				fail("got exception " + e.getMessage());			
			}
		}		
	}

}
