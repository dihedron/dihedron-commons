/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class LoggingFilterDestination<M> extends FilterDestination<M> {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(LoggingFilterDestination.class);

	/**
	 * Constructor.
	 *
	 * @param destination
	 *   the filtered destination. 
	 */
	LoggingFilterDestination(Destination<M> destination) {
		super(destination);
	}
	
	/**
	 * @see org.dihedron.patterns.bus.Destination#getId()
	 */
	@Override
	public String getId() {
		return "Logging@" + destination.getId(); 
	}
	
	/**
	 * @see org.dihedron.patterns.bus.Destination#onMessage(java.lang.Object)
	 */
	@Override
	public void onMessage(M message) {
		logger.trace("dispatching message '{}' to destination '{}'", message, destination);
		destination.onMessage(message);
		logger.trace("message message '{}' dispatched to destination '{}'", message, destination);
	}	
}
