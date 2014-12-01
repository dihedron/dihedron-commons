/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides a single message bus to propagate information that's not 
 * model-specific around: all registered observers will be notified as soon as a 
 * new message is posted on the bus; subclasses may implement message dispatching
 * in a synchronous or asynchronous way.
 * 
 *  @author Andrea Funto'
 */
@License
public abstract class Bus<M> implements AutoCloseable {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(Bus.class);

	/** 
	 * The destinations of bus messages. 
	 */
	protected Collection<Destination<M>> destinations = 
			//Collections.synchronizedSet(new HashSet<Destination<M>>());
			Collections.synchronizedList(new ArrayList<Destination<M>>());
	
	/**
	 * Adds a destination to this message bus.
	 * 
	 * @param destination
	 *   the destination of bus messages.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public Bus<M> addDestination(Destination<M> destination) {
		if(destination != null) {
			logger.info("adding destination '{}' of class '{}'", destination.getId(), destination.getClass().getSimpleName());
			destinations.add(destination);
		}
		return this;
	}
	
	/**
	 * Removes the given destinations from the set of those registered on this bus.
	 * 
	 * @param destination
	 *   the destination to remove.
	 * @return
	 *   {@code true} if the object was in the set of registered destinations,
	 *   and thus removed, {@code false} otherwise.
	 */
	public boolean removeDestination(Destination<M> destination) {
		return destinations.remove(destination);		
	}
	
	/**
	 * Removes the given object from the set of registered destinations.
	 * 
	 * @param id
	 *   the unique id of the destination to be removed.
	 * @return
	 *   {@code true} if the object was in the set of registered destinations,
	 *   and thus removed, {@code false} otherwise.
	 */
	public boolean removeDestination(String id) {
		logger.trace("removing destination {}", id);
		for(Destination<M> destination : destinations) {
			if(destination.getId().equals(id)) {
				destinations.remove(destination);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes all destinations from the bus.
	 * 
	 * @return
	 *   the object itself, for method chaining.
	 */
	public Bus<M> removeAllDestinations() {
		logger.trace("removing all destinations");
		destinations.clear();
		return this;
	}
	
	/**
	 * Broadcasts an event on the bus to all registered destinations; the sender 
	 * waits until all destinations have been notified.
	 * 
	 * @param message
	 *   the message to be broadcast.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public abstract Bus<M> send(M message);

	/**
	 * Broadcasts an event on the bus to all registered destinations; the sender 
	 * is freed immediately and the bus takes charge of the message, which will 
	 * be delivered at a later time and in a different (possibly not shared) 
	 * thread context.
	 * 
	 * @param message
	 *   the message to be broadcast.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public abstract Bus<M> post(M message);	
	
	/**
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() {
		// do nothing, sub-classes will implement if needed.
	}
}
