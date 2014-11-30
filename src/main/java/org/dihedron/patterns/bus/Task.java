/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import java.util.concurrent.Callable;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The common base task for internal bus tasks.
 * 
 * @author Andrea Funto'
 */
@License
class Task<M> implements Callable<Void> {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Task.class);
	
	/**
	 * The destination to which the message must be delivered.
	 */
	protected Destination<M> destination;
	
	/**
	 * The message to dispatch.
	 */
	protected M message;
	
	/**
	 * Constructor.
	 *
	 * @param destination
	 *   the destination to which the message must be delivered.
	 * @param message
	 *   the message to dispatch.
	 */
	Task(Destination<M> destination, M message) {
		this.destination = destination;
		this.message = message;
	}
	
	/**
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Void call() throws Exception {
		logger.trace("dispatching message {} to destination {} in thread {}", message, destination, Thread.currentThread().getId());
		destination.onMessage(message);
		return null;
	}
}
