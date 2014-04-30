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

package org.dihedron.commons.bus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.dihedron.commons.bus.messages.BusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides a single message bus to propagate information that's not 
 * model-specific around: all registered observers will be notified synchronously 
 * as soon as a new message is posted on the bus.
 * 
 *  @author Andrea Funto'
 */
public class Bus {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(Bus.class);

	/** 
	 * The singleton. 
	 */
	private static Bus SINGLETON = new Bus();

	/** 
	 * The message bus observers. 
	 */
	private Set<BusObserver> observers = Collections.synchronizedSet(new HashSet<BusObserver>());

	/**
	 * Adds an observer to this message bus.
	 * 
	 * @param observer
	 *   the observer.
	 */
	public static void addObserver(BusObserver observer) {
		if(observer != null) {
			Bus.getInstance().observers.add(observer);
		}
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
	public static boolean removeObserver(BusObserver observer) {
		return getInstance().observers.remove(observer);
	}
	
	/**
	 * Removes all observers from the set.
	 */
	public static void removeAllObservers() {
		getInstance().observers.clear();
	}
	
	/**
	 * Broadcasts an event on the bus to all registered observers.
	 * 
	 * @param message
	 *   the message to be broadcast.
	 */
	public static void broadcast(Object sender, BusMessage message) {
		if(message != null) {
			logger.trace("'{}' dispatching message:\n{}", sender != null ? sender : "<static>", message);
			for(BusObserver observer : getInstance().observers) {
				logger.trace("dispatching to observer '{}'...", observer.getClass().getSimpleName()); 
				observer.onMessage(message);
			}
		}
	}
		
	/**
	 * Retrieves the single instance.
	 * 
	 * @return 
	 *   the single instance.
	 */
	public static Bus getInstance() {		
		return SINGLETON;
	}	
	
	/**
	 * Constructor.
	 */
	Bus() {
		logger.debug("BUS created");		
	}
}
