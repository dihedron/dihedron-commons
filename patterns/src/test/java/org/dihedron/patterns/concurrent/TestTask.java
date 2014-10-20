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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Andrea Funto'
 */
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
