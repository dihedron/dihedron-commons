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

package org.dihedron.patterns.bus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides a single message bus to propagate information that's not 
 * model-specific around: all registered observers will be notified synchronously 
 * as soon as a new message is posted on the bus.
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
