/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.dihedron.patterns.bus.AsynchronousBus;
import org.dihedron.patterns.bus.Bus;
import org.dihedron.patterns.bus.Destination;
import org.dihedron.patterns.bus.Prioritised;
import org.dihedron.patterns.bus.PriorityComparator;
import org.dihedron.patterns.bus.SynchronousBus2;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class BusTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(BusTest.class);

	
	
	public enum Message implements Prioritised {
		HALLO(5),
		MESSAGE1(1),
		MESSAGE2(2),
		MESSAGE3(3),
		MESSAGE4(4),
		MESSAGE5(5),
		GOODBYE(0);
		
		private Message(int priority) {
			this.priority = priority;
		}
		
		private int priority = Prioritised.NO_PRIORITY;

		/**
		 * @see org.dihedron.patterns.bus.BusTest.Prioritised#getPriority()
		 */
		@Override
		public int getPriority() {
			return priority;
		}
	}
	
	private class TestDestination implements Destination<Message> {

		/**
		 * The logger.
		 */
		private final Logger logger = LoggerFactory.getLogger(TestDestination.class);
		
		/**
		 * The destination id.
		 */
		private int id; 
		
		private AtomicInteger counter;
		
		/**
		 * Constructor.
		 *
		 * @param id
		 *   the destination id.
		 * @param counter
		 *   a shared message counter.
		 */
		public TestDestination(int id, AtomicInteger counter) {
			this.id = id;
			this.counter = counter;
		}
		
		@Override
		public String getId() {
			return toString();
		}
		
		@Override
		public String toString() {
			return "DESTINATION[" + id +"]";
		}
		
		/**
		 * @see org.dihedron.patterns.bus.Destination#onMessage(java.lang.Object)
		 */
		@Override
		public void onMessage(Message message) {
			int value = counter.incrementAndGet();
			logger.debug("{}: handling message {} in thread {} ({} so far}", this, message.name(), Thread.currentThread().getId(), value);
			
		}		
	}
	
	/**
	 * Test method for {@link org.dihedron.patterns.bus.Bus#send(java.lang.Object)}.
	 */
	@Test
	public void testSendOnSynchronousBus() {
		logger.trace("------------------ SEND ON SYNCHRONOUS BUS ------------------");
		AtomicInteger counter = new AtomicInteger(0);
		try(Bus<Message> bus = new SynchronousBus2<>()) {
			bus.addDestination(new TestDestination(0, counter));
			bus.addDestination(new TestDestination(1, counter));
			bus.addDestination(new TestDestination(2, counter));
			bus.addDestination(new TestDestination(3, counter));
			bus.addDestination(new TestDestination(4, counter));
			
			bus
				.send(Message.HALLO)
				.send(Message.MESSAGE1)
				.send(Message.MESSAGE2)
				.send(Message.MESSAGE3)
				.send(Message.MESSAGE4)
				.send(Message.MESSAGE5)
				.send(Message.GOODBYE);			
			assertTrue(counter.get() == 35);
		}
	}
	
	/**
	 * Test method for {@link org.dihedron.patterns.bus.Bus#send(java.lang.Object)}.
	 */
	@Test
	public void testSendOnAsynchronousBus() {
		
		logger.trace("------------------ SEND ON ASYNCHRONOUS BUS ------------------");
		
		AtomicInteger counter = new AtomicInteger(0);
		try(Bus<Message> bus = new AsynchronousBus<>()) {
			bus.addDestination(new TestDestination(0, counter));
			bus.addDestination(new TestDestination(1, counter));
			bus.addDestination(new TestDestination(2, counter));
			bus.addDestination(new TestDestination(3, counter));
			bus.addDestination(new TestDestination(4, counter));
			
			bus
				.send(Message.HALLO)
				.send(Message.MESSAGE1)
				.send(Message.MESSAGE2)
				.send(Message.MESSAGE3)
				.send(Message.MESSAGE4)
				.send(Message.MESSAGE5)
				.send(Message.GOODBYE);
			assertTrue(counter.get() == 35);
		}
	}
	
	/**
	 * Test method for {@link org.dihedron.patterns.bus.Bus#send(java.lang.Object)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testPostOnSynchronousBus() throws InterruptedException {
		logger.trace("------------------ POST ON SYNCHRONOUS BUS ------------------");
		AtomicInteger counter = new AtomicInteger(0);
		try(Bus<Message> bus = new SynchronousBus2<>()) {
			bus.addDestination(new TestDestination(0, counter));
			bus.addDestination(new TestDestination(1, counter));
			bus.addDestination(new TestDestination(2, counter));
			bus.addDestination(new TestDestination(3, counter));
			bus.addDestination(new TestDestination(4, counter));
			
			bus
				.post(Message.HALLO)
				.post(Message.MESSAGE1)
				.post(Message.MESSAGE2)
				.post(Message.MESSAGE3)
				.post(Message.MESSAGE4)
				.post(Message.MESSAGE5)
				.post(Message.GOODBYE);	
			
			Thread.sleep(1000);
			assertTrue(counter.get() == 35);
		}
	}
	
	/**
	 * Test method for {@link org.dihedron.patterns.bus.Bus#send(java.lang.Object)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testPostOnAsynchronousBus() throws InterruptedException {
		
		logger.trace("------------------ POST ON ASYNCHRONOUS BUS ------------------");
		
		AtomicInteger counter = new AtomicInteger(0);
		try(Bus<Message> bus = new AsynchronousBus<>()) {
			bus.addDestination(new TestDestination(0, counter));
			bus.addDestination(new TestDestination(1, counter));
			bus.addDestination(new TestDestination(2, counter));
			bus.addDestination(new TestDestination(3, counter));
			bus.addDestination(new TestDestination(4, counter));
			
			bus
				.post(Message.HALLO)
				.post(Message.MESSAGE1)
				.post(Message.MESSAGE2)
				.post(Message.MESSAGE3)
				.post(Message.MESSAGE4)
				.post(Message.MESSAGE5)
				.post(Message.GOODBYE);
			Thread.sleep(1000);
			assertTrue(counter.get() == 35);
		}
	}
	
	
	/**
	 * Test method for {@link org.dihedron.patterns.bus.Bus#send(java.lang.Object)}.
	 */
	@Test
//	@Ignore
//	http://stackoverflow.com/questions/3545623/how-to-implement-priorityblockingqueue-with-threadpoolexecutor-and-custom-tasks
	public void testSendOnAsynchronousBusWithPriority() {
		
		logger.trace("------------------ SEND ON ASYNCHRONOUS BUS WITH PRIORITY ------------------");
		
		BlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>(20, new PriorityComparator());

	    ExecutorService service = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, queue);
	    
		AtomicInteger counter = new AtomicInteger(0);
		try(Bus<Message> bus = new PrioritisedAsynchronousBus<>(service)) {
			bus.addDestination(new TestDestination(0, counter));
			bus.addDestination(new TestDestination(1, counter));
			bus.addDestination(new TestDestination(2, counter));
			bus.addDestination(new TestDestination(3, counter));
			bus.addDestination(new TestDestination(4, counter));
			
			bus
				.send(Message.HALLO)
				.send(Message.MESSAGE1)
				.send(Message.MESSAGE2)
				.send(Message.MESSAGE3)
				.send(Message.MESSAGE4)
				.send(Message.MESSAGE5)
				.send(Message.GOODBYE);
			assertTrue(counter.get() == 35);
		}
	}

	/**
	 * Test method for {@link org.dihedron.patterns.bus.Bus#post(java.lang.Object)}.
	 */
	@Test
	@Ignore
	public void testPost() {
		fail("Not yet implemented");
	}
}
