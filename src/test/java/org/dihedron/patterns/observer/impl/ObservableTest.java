/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.observer.impl;

import org.dihedron.core.License;
import org.dihedron.patterns.observer.Observable;
import org.dihedron.patterns.observer.Observer;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class ObservableTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ObservableTest.class);

	/**
	 * The synchronous observable.
	 */
	private Observable<String> synchronous = new SynchronousObservable<>();
	
	/**
	 * The asynchronous observer.
	 */
	private Observable<String> asynchronous = new AsynchronousObservable<>();
	
	@License
	class TestObserver implements Observer<String> {

		private int id;
		
		TestObserver(int id) {
			this.id = id;
		}
		
		/**
		 * @see org.dihedron.patterns.observer.Observer#onEvent(org.dihedron.patterns.observer.Observable, java.lang.Object, java.lang.Object[])
		 */
		@Override
		public void onEvent(Observable<String> source, String event, Object... args) {
			logger.trace("{} in thread {} - received event: '{}'", id, Thread.currentThread().getId(), event);			
		}
		
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		for(int i = 0; i < 20; ++i) {
			synchronous.addObserver(new TestObserver(i));
			asynchronous.addObserver(new TestObserver(i));
		}
	}

	/**
	 * Test method for {@link org.dihedron.patterns.observer.impl.SynchronousObservable#fire(java.lang.Object, java.lang.Object[])}.
	 */
	@Test
	public void testSynchronousFire() {
		for(int i = 0; i < 10; ++i) {
			synchronous.fire("hallo");
		}
	}
	
	/**
	 * Test method for {@link org.dihedron.patterns.observer.impl.AsynchronousObservable#fire(java.lang.Object, java.lang.Object[])}.
	 */
	@Test
	public void testAsynchronousFire() {
		for(int i = 0; i < 10; ++i) {
			asynchronous.fire("hallo");
		}
//		fail("Not yet implemented");
	}
	
}
