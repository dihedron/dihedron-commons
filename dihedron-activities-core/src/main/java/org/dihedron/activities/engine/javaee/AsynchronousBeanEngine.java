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

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import org.dihedron.activities.engine.ActivityCallable;
import org.dihedron.activities.engine.ParallelEngine;
import org.dihedron.activities.types.ActivityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes the given tasks as background operations, leveraging the application
 * server's asynchronous EBJ 3.1 methods facilities.
 * 
 * @author Andrea Funto'
 */
@ManagedBean
public class AsynchronousBeanEngine extends ParallelEngine {

	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(AsynchronousBeanEngine.class);

	/**
	 * The default JNDI name of the asynchronous EJB, base on JBoss naming convention.
	 */
	private static final String DEFAULT_ASYNCH_BEAN_NAME = "java:module/AsynchronousBean";
	
	/**
	 * The JNDI name of the asynchronous EJB; this can be injected externally, 
	 * to replace the default value, if needed.
	 */
	private String asynchBeanName = DEFAULT_ASYNCH_BEAN_NAME;
	
	/**
	 * Default constructor, assumes the JNDI name of the underlying asynchronous 
	 * EJB is OK.
	 */
	public AsynchronousBeanEngine() {
	}
	
	/**
	 * Constructor.
	 * 
	 * @param asynchBeanName
	 *   the JNDI name of the underlying asynchronous EJB.
	 */
	public AsynchronousBeanEngine(String asynchBeanName) {
		this.asynchBeanName = asynchBeanName;
	}
	
	/**
	 * This method is used only for debug purposes, to write some debug output
	 * during the bean's initialization.
	 */
	@PostConstruct
	public void init(){
		logger.info("initializing executor correctly");
	}
	
	/**
	 * The (injected) EJB 3.1 whose asynchronous method will be used to assign
	 * the task to a background thread. 
	 */
	@EJB private AsynchronousBean ejb;
	
	/**
	 * Assigns the given activity to a worker thread in the JavaEE application 
	 * server, returning the task's Future object.
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
		try {
			if(getEjb() == null) {
				logger.info("locating AsynchronousBean EJB through service locator");
				setEjb(ServiceLocator.getService(asynchBeanName, AsynchronousBean.class));				
			} else {
				logger.info("asynchronousBean EJB correctly initialized through dependency injection");
			}
			return getEjb().submit(callable);
		} catch(Exception e) {
			logger.error("error in the AsynchronousBean service locator lookup", e);
		}
		return null;
	}
	
	/**
	 * Returns the asynchronous bean reference. 
	 * 
	 * @return
	 *   the asynchronous bean reference.
	 */
	public AsynchronousBean getEjb() {
		return ejb;
	}

	/**
	 * Sets the asynchronous bean reference.
	 * 
	 * @param ejb
	 *   the asynchronous bean reference.
	 */
	public void setEjb(AsynchronousBean ejb) {
		this.ejb = ejb;
	}	
}
