/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.observer.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dihedron.patterns.observer.Observable;
import org.dihedron.patterns.observer.Observer;

/**
 * A class implementing the simple, synchronous observer pattern.
 * 
 * @author Andrea Funto'
 */
public class SynchronousObservable<E> implements Observable<E> {

	/**
	 * The list of registered observers.
	 */
	private List<Observer<E>> observers = Collections.synchronizedList(new ArrayList<Observer<E>>());

	/**
	 * @see org.dihedron.patterns.observer.Observable#addObserver(org.dihedron.patterns.observer.Observer)
	 */
	@Override
	public Observable<E> addObserver(Observer<E> observer) {
		if(observer != null) {
			observers.add(observer);
		}
		return this;
	}

	/**
	 * @see org.dihedron.patterns.observer.Observable#removeObserver(org.dihedron.patterns.observer.Observer)
	 */
	@Override
	public Observable<E> removeObserver(Observer<E> observer) {
		if(observer != null) {
			List<Observer<E>> pruned = Collections.synchronizedList(new ArrayList<Observer<E>>());
			synchronized(observers) {				
				for(Observer<E> o : observers) {
					if(o != observer) {
						pruned.add(o);
					}
				}
				observers = pruned;
			}
		}
		return this;
	}

	/**
	 * @see org.dihedron.patterns.observer.Observable#removeAllObservers()
	 */
	@Override
	public Observable<E> removeAllObservers() {
		observers.clear();
		return this;
	}

	/**
	 * @see org.dihedron.patterns.observer.Observable#fire(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public void fire(E event, Object... args) {
		for(Observer<E> observer : observers) {
			observer.onEvent(this, event, args);
		}		
	}
}
