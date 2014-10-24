/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.bus;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A synchronous flavour of the message bus.
 * 
 *  @author Andrea Funto'
 */
@License
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
