/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.observer.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dihedron.patterns.observer.Observable;
import org.dihedron.patterns.observer.Observer;

/**
 * A class implementing the simple, synchronous observer pattern.
 * 
 * @author Andrea Funto'
 */
public class AsynchronousObservable<E> implements Observable<E> {

	public static final int DEFAULT_THREADPOOL_SIZE = 4; 
	
	/**
	 * The list of registered observers.
	 */
	private List<Observer<E>> observers = Collections.synchronizedList(new ArrayList<Observer<E>>());
	
	/**
	 * The thread pool used to dispatch events to observers. 
	 */
	private final ExecutorService pool = Executors.newFixedThreadPool(DEFAULT_THREADPOOL_SIZE);

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
			pool.execute(new Handler(this, observer, event, args));
			observer.onEvent(this, event, args);
		}		
	}
	
	/**
	 * Runs the event dispatching in a separate thread.
	 * 
	 * @author Andrea Funto'
	 */
	private class Handler implements Runnable {
		
		/**
		 * The source of the event.
		 */
		private Observable<E> source;
		
		/**
		 * The observer, i.e. the event sink.
		 */
		private Observer<E> target;
		
		/**
		 * The event being dispatched.
		 */
		private E event;
		
		/**
		 * The optional, untyped arguments.
		 */
		private Object[] args;
		
		/**
		 * Constructor.
		 *
		 * @param source
		 *   the observable, i.e. the event source.
		 * @param target
		 *   the observer, i.e. the event sink.
		 * @param event
		 *   the actual event.
		 * @param args
		 *   the optional, untyped arguments to the event.
		 */
		Handler(Observable<E> source, Observer<E> target, E event, Object ... args) {
			this.source = source;
			this.target = target;
			this.event = event;
			this.args = args;
		}
		
		/**
		 * The method performing the actual dispatching.
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			// dispatch the event, running the event handling in a different thread
			target.onEvent(source, event, args);
		}
	}	
}
