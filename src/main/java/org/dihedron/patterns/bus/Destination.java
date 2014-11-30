/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.bus;

import org.dihedron.core.License;

/**
 * The base interface for all bus destinations.
 * 
 * @author Andrea Funto'
 */
@License
public interface Destination<M> {
	
	/**
	 * A method invoked whenever the a new message is available on the internal 
	 * message bus; this way of dispatching information around caters for loose
	 * coupling among components.
	 * 
	 * @param message
	 *   the actual message.
	 */
	void onMessage(M message);
}	
