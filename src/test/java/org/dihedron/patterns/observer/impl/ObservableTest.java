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
package org.dihedron.patterns.observer.impl;

import org.dihedron.patterns.observer.Observable;
import org.dihedron.patterns.observer.Observer;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
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
	 * The asynchornous observer.
	 */
	private Observable<String> asynchronous = new AsynchronousObservable<>();
	
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
