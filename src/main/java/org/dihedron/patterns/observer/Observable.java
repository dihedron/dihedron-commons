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
package org.dihedron.patterns.observer;


/**
 * @author Andrea Funto'
 */
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
