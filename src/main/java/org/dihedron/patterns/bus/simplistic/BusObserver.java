/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.bus.simplistic;

import org.dihedron.core.License;

/**
 * The base interface for all bus observers.
 * 
 * @author Andrea Funto'
 */
@License
public interface BusObserver<M> {
	
	/**
	 * A method invoked whenever the a new message is available on the internal 
	 * message bus; this way of dispatching information around caters for loose
	 * coupling among components.
	 * 
	 * @param message
	 *   the actual message.
	 * @param args
	 *   a set of optional untyped arguments.
	 */
	void onMessage(M message, Object ... args);

	/**
	 * A method invoked whenever the a new message is available on the internal 
	 * message bus; this way of dispatching information around caters for loose
	 * coupling among components. This default implementation simply ignores the 
	 * sender parameters; subclasses can always choose to override this method if 
	 * they feel they need to be notified about the actual sender object.
	 * 
	 * @param sender
	 *   the message sender; could be null if no sender was specified.
	 * @param message
	 *   the actual message.
	 * @param args
	 *   a set of optional untyped arguments.
	 */
	void onMessage(Object sender, M message, Object ... args);
}	
