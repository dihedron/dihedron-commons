/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.observer;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public interface Observer<E> {

	/**
	 * A method invoked whenever the a new event is available; this way of 
	 * dispatching information around caters for loose coupling among components.
	 * 
	 * @param source
	 *   the event source; could be null if no source was specified.
	 * @param event
	 *   the actual event.
	 * @param args
	 *   a set of optional untyped arguments.
	 */
	void onEvent(Observable<E> source, E event, Object ... args);	
}
