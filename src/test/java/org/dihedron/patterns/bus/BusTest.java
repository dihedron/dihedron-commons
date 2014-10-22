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
package org.dihedron.patterns.bus;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class BusTest {
	
	private enum TestMessage {
		MESSAGE1,
		MESSAGE2,
		MESSAGE3,
		MESSAGE4
	}
	
	private static class TestObserver extends DefaultBusObserver<TestMessage> {

		/**
		 * The logger.
		 */
		private static final Logger logger = LoggerFactory.getLogger(TestObserver.class);
		
		private TestMessage value;
		
		private int count = 0;
		
		public TestObserver(TestMessage value) {
			this.value = value;
		}
		
		public int getCount() {
			return count;
		}
		
		public void resetCount() {
			count = 0;
		}
		
		/**
		 * @see org.dihedron.patterns.bus.BusObserver#onMessage(java.lang.Enum)
		 */
		@Override
		public void onMessage(TestMessage message, Object ... args) {
			logger.trace("received message: '{}' (args: {})", message.name(), args);
			if(message == value) {
				count++;
			}
		}
		
	}

	/**
	 * The synchronous bus under test.
	 */
	private Bus<TestMessage> synchronous;
	
	private TestObserver observer1;
	private TestObserver observer2;
	private TestObserver observer3;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {		
		observer1 = new TestObserver(TestMessage.MESSAGE1);
		observer2 = new TestObserver(TestMessage.MESSAGE2);
		observer3 = new TestObserver(TestMessage.MESSAGE3);
		
		synchronous = new SynchronousBus<>();
		synchronous.addObserver(observer1);
		synchronous.addObserver(observer2);
		synchronous.addObserver(observer3);
	}

	/**
	 * Test method for {@link org.dihedron.patterns.bus.Bus#broadcast(java.lang.Enum)}.
	 */
	@Test
	public void testSynchronousBroadcastMessage() {
		
		observer1.resetCount();
		observer2.resetCount();
		observer3.resetCount();
				
		for(int i = 0; i < 10; ++i) {
			synchronous.broadcast(TestMessage.MESSAGE1, i);
		}
		for(int i = 0; i < 20; ++i) {
			synchronous.broadcast(TestMessage.MESSAGE2, i, "a string");
		}
		for(int i = 0; i < 30; ++i) {
			synchronous.broadcast(TestMessage.MESSAGE3, i, new Object());
		}
		
		assertTrue(observer1.getCount() == 10);
		assertTrue(observer2.getCount() == 20);
		assertTrue(observer3.getCount() == 30);
	}

	/**
	 * Test method for {@link org.dihedron.patterns.bus.Bus#broadcast(java.lang.Object, java.lang.Enum)}.
	 */
	@Test
	public void testSynchronousBroadcastSenderAndMessage() {
		observer1.resetCount();
		observer2.resetCount();
		observer3.resetCount();
				
		for(int i = 0; i < 10; ++i) {
			synchronous.broadcast(this, TestMessage.MESSAGE1, i, i, i);
		}
		for(int i = 0; i < 20; ++i) {
			synchronous.broadcast(this, TestMessage.MESSAGE2, i, i, i);
		}
		for(int i = 0; i < 30; ++i) {
			synchronous.broadcast(this, TestMessage.MESSAGE3, i, i, i);
		}
		
		assertTrue(observer1.getCount() == 10);
		assertTrue(observer2.getCount() == 20);
		assertTrue(observer3.getCount() == 30);
	}
}
