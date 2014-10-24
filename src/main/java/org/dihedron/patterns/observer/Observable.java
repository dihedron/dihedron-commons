/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.observer;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public interface Observable<E> {
	
	/**
	 * Adds an observer to this observable object.
	 * 
	 * @param observer
	 *   the observer to be added.
	 * @return
	 *   the observable object, to allow method chaining.
	 */
	Observable<E> addObserver(Observer<E> observer);
	
	/**
	 * Removes the given observer.
	 * 
	 * @param observer
	 *   the observer to be removed.
	 * @return
	 *   the observable object, to allow method chaining.
	 */
	Observable<E> removeObserver(Observer<E> observer);
	
	/**
	 * Removes all registered observers.
	 * 
	 * @return
	 *   the observable object, to allow method chaining.
	 */
	Observable<E> removeAllObservers();
	
	/**
	 * Fires the given event and dispatches it to all registered listeners.
	 * 
	 * @param event
	 *   the event to fire.
	 * @param args
	 *   an optional list of untyped arguments to the event.
	 */
	void fire(E event, Object ... args);
}
