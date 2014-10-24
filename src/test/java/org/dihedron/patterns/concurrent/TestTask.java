/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.concurrent;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Andrea Funto'
 */
@License
public class TestTask implements Task<String> {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TestTask.class);

	private int id;
	
	private long sleepTime;
	
	public TestTask(int id, long sleepTime) {
		this.id = id;
		this.sleepTime = sleepTime;
	}
	
	/**
	 * @see org.dihedron.concurrent.TaskCallable#execute()
	 */
	@Override
	public String execute() throws TaskException {
        try {
			Thread.sleep(sleepTime);
			return "task " + id + " complete after having slept for " + sleepTime + " ms";
		} catch (InterruptedException e) {
			logger.error("error: thread interrupted", e);
			throw new TaskException("thread interrupted", e);
		}
	}
	
	public String toString() {
		return "task " + id + ": sleep for " + sleepTime + " ms";
	}
}
