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
package org.dihedron.activities.engine.javaee;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Stateless;

import org.dihedron.activities.engine.ActivityCallable;
import org.dihedron.activities.exceptions.ActivityException;
import org.dihedron.activities.types.ActivityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@Stateless
public class AsynchronousBean {
	
	private final static Logger logger = LoggerFactory.getLogger(AsynchronousBean.class);
	
	/**
	 * Assigns the given activity to the asynchronous EJB for background execution.
	 * 
	 * @param callable
	 *   the {@code Activity} to be executed asynchronously.
	 * @return
	 *   the activity's {@code Future} object, for result retrieval.
	 * @see 
	 *   org.dihedron.activities.concurrent.ActivityExecutor#submit(org.dihedron.activities.Activity)
	 */
	public Future<ActivityData> submit(ActivityCallable callable) throws ActivityException {
		logger.info("submitting activity to asynchronous EJB...");
		return new AsyncResult<ActivityData>(callable.call());
	}	
}
