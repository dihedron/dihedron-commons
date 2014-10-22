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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides a single message bus to propagate information that's not 
 * model-specific around: all registered observers will be notified synchronously 
 * as soon as a new message is posted on the bus.
 * 
 *  @author Andrea Funto'
 */
public class SynchronousBus<M> extends Bus<M> {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(SynchronousBus.class);

	/**
	 * @see org.dihedron.patterns.bus.Bus#broadcast(java.lang.Object, java.lang.Object, java.lang.Object[])
	 */
	public SynchronousBus<M> broadcast(Object sender, M message, Object ... args) {
		if(message != null) {			
			logger.trace("'{}' dispatching message '{}' with arguments '{}'", sender != null ? sender : "<static>", message, args != null ? args : "[]");
			for(BusObserver<M> observer : observers) {
				logger.trace("dispatching to observer '{}'...", observer.getClass().getSimpleName()); 
				observer.onMessage(sender, message, args);
			}
		}
		return this;
	}	
}
