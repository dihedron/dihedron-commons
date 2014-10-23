/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.bus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
public abstract class Bus<M> {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(Bus.class);

	/** 
	 * The message bus observers. 
	 */
	protected Set<BusObserver<M>> observers = Collections.synchronizedSet(new HashSet<BusObserver<M>>());

	/**
	 * Constructor.
	 */
	public Bus() {
		logger.trace("BUS created");		
	}
	
	/**
	 * Adds an observer to this message bus.
	 * 
	 * @param observer
	 *   the observer.
	 */
	public Bus<M> addObserver(BusObserver<M> observer) {
		if(observer != null) {
			observers.add(observer);
		}
		return this;
	} 

	/**
	 * Removes the given object from the set of registered observers.
	 * 
	 * @param observer
	 *   the observer to be removed.
	 * @return
	 *   <code>true</code> if the object was in the set of registered observers,
	 *   <code>false</code> otherwise.
	 */
	public boolean removeObserver(BusObserver<M> observer) {
		return observers.remove(observer);
	}
	
	/**
	 * Removes all observers from the set.
	 */
	public Bus<M> removeAllObservers() {
		observers.clear();
		return this;
	}
	
	/**
	 * Broadcasts an event on the bus to all registered observers.
	 * 
	 * @param message
	 *   the message to be broadcast.
	 * @param args
	 *   a set of optional untyped parameters.
	 */
	public Bus<M> broadcast(M message, Object ... args) {
		return broadcast(null, message, args);
	}	
	
	/**
	 * Broadcasts an event on the bus to all registered observers.
	 * 
	 * @param sender
	 *   the message sender (pass in "this").
	 * @param message
	 *   the message to be broadcast.
	 * @param args
	 *   a set of optional untyped parameters.
	 */
	public abstract Bus<M> broadcast(Object sender, M message, Object ... args);
}
