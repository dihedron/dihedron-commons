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
package org.dihedron.commons.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class TestObserver implements TaskObserver<String> {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(TestObserver.class);


	/**
	 * @see org.dihedron.commons.concurrent.TaskObserver#onTaskStarting(org.dihedron.commons.concurrent.Task)
	 */
	@Override
	public void onTaskStarting(Task<String> task) {
		logger.debug("task '{}' starting", task);
	}

	/**
	 * @see org.dihedron.commons.concurrent.TaskObserver#onTaskStarted(org.dihedron.commons.concurrent.Task)
	 */
	@Override
	public void onTaskStarted(Task<String> task) {
		logger.debug("task '{}' started", task);			
	}

	/**
	 * @see org.dihedron.commons.concurrent.TaskObserver#onTaskComplete(org.dihedron.commons.concurrent.Task, java.lang.Object)
	 */
	@Override
	public void onTaskComplete(Task<String> task, String result) {
		logger.debug("result of task '{}' is '{}'", task, result);	
	}
}
