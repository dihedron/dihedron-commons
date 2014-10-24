/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine.javaee;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Stateless;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.engine.ActivityCallable;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.ActivityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@Stateless
@License
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
	 *   org.dihedron.patterns.activities.concurrent.ActivityExecutor#submit(org.dihedron.patterns.activities.Activity)
	 */
	public Future<ActivityData> submit(ActivityCallable callable) throws ActivityException {
		logger.info("submitting activity to asynchronous EJB...");
		return new AsyncResult<ActivityData>(callable.call());
	}	
}
