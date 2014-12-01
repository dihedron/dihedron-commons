/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class LegacySynchronousBus<M> extends Bus<M> implements AutoCloseable {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(LegacySynchronousBus.class);
	
	/**
	 * The internal queue used to communicate with the dispatcher tread.
	 */
	private final BlockingQueue<M> queue;
	
	/**
	 * The dispatcher worker thread.
	 */
	private Dispatcher dispatcher;
	
	/**
	 * Default constructor; initialises the internal queue with a simple
	 * linked list (non-prioritising) blocking queue; thus, messages will be 
	 * dispatched in the exact same order in which they are submitted.
	 */
	public LegacySynchronousBus() {
		this.queue = new PriorityBlockingQueue<>();
		this.dispatcher = new Dispatcher(queue, destinations);
		new Thread(dispatcher).start();
	}
	
	/**
	 * Constructor.
	 *
	 * @param queue
	 *   an implementation of a blocking queue; by supplying a custom implementation 
	 *   of the blocking queue, the order in which messages are dispatched may 
	 *   be altered at will, e.g. to provide a priority-based order.
	 */
	public LegacySynchronousBus(BlockingQueue<M> queue) {
		this.queue = queue;
	}
	
	/**
	 * Sends a message synchronously to all registered destinations; message
	 * handling code in the destinations will run in the same thread as the sender 
	 * object's.
	 * 
	 * @see org.dihedron.patterns.bus.Bus#send(java.lang.Object)
	 */
	@Override
	public Bus<M> send(M message) {
		logger.trace("starting dispatching message '{}' to destinations", message);
		for(Destination<M> destination : destinations) {
			logger.trace("dispatching to destination {}", destination);
			destination.onMessage(message);
		}
		logger.trace("done dispatching message '{}' to destinations", message);
		return this;
	}

	/**
	 * Posts a message asynchronously to all registered destinations; message
	 * handling code in the destinations will run in a new thread; destinations
	 * will share the same thread, which is different from the sender's.
	 * 
	 * @see org.dihedron.patterns.bus.Bus#post(java.lang.Object)
	 */
	@Override
	public Bus<M> post(M message) {
		queue.offer(message);
		return this;
	}
	
	/**
	 * Posts a message to all registered destinations, waiting up to the specified
	 * number of time units if the internal queue is full.
	 * 
	 * @param message
	 *   the message to be posted.
	 * @param timeout
	 *   a number of time units to wait if the message cannot be queued immediately
	 *   for dispatching.
	 * @param unit
	 *   the time units in which the timeout is expressed.
	 * @return
	 *   the objects itself, for method chaining.
	 * @throws InterruptedException
	 */
	public Bus<M> post(M message, long timeout, TimeUnit unit) throws InterruptedException {
		queue.offer(message, timeout, unit);
		return this;
	}
	
	/**
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() {
		logger.trace("synchronous bus shutting down");
		dispatcher.close();
	}		
	
	/**
	 * The internal class used to spawn new threads that provide asynchronicity 
	 * to the dispatching process when needed.
	 * 
	 * @author Andrea Funto'
	 */
	@License
	private class Dispatcher implements Runnable, AutoCloseable {

		/**
		 * The queue on 
		 */
		private final BlockingQueue<M> queue;
		
		/**
		 * The set of destinations to which the message will be dispatched.
		 */
		private Collection<Destination<M>> destinations;
		
		/**
		 * A boolean tested every few seconds to see if the bus is shutting down.
		 */
		private AtomicBoolean closing = new AtomicBoolean(false);
						
		/**
		 * Constructor.
		 *
		 * @param destinations
		 *   the destinations to which the message will be dispatched in a round
		 *   robin fashion; this is not a private copy: it is a reference to the
		 *   original set of destinations in the bus, so the addition of a new
		 *   destination to the bus is immediately available to the dispatcher.
		 * @param queue
		 *   the queue on which messages will arrive to the dispatcher.
		 * @param sender
		 *   the reference to the sender option, or {@code null}.
		 */
		private Dispatcher(BlockingQueue<M> queue, Collection<Destination<M>> destinations) {
			this.queue = queue;
			this.destinations = destinations;
		}
		
		/**
		 * Dispatched the message to all the registered destinations in a round
		 * robin fashion, that is sequentially and waiting for each destination
		 * to complete its processing. 
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				logger.trace("dispatcher starting in thread {}...", Thread.currentThread().getId());
				do {
					M message = queue.poll(1, TimeUnit.SECONDS);
					if(message != null && !closing.get()) {
						logger.trace("starting dispatching message '{}' to destinations", message);
						for(Destination<M> destination : destinations) {
							logger.trace("dispatching to destination {}", destination);
							destination.onMessage(message);
						}
						logger.trace("done dispatching message '{}' to destinations", message);
					}
				} while(true);
			} catch(InterruptedException e) {
				logger.warn("dispatcher tread was interrupted");
			}
		}

		/**
		 * Sets an internal flag that causes the dispatcher thread to exit as 
		 * soon as it senses the flag change, without dispatching any message
		 * still in the queue.
		 *  
		 * @see java.lang.AutoCloseable#close()
		 */
		@Override
		public void close() {
			logger.trace("synchronous bus internal dispatcher shutting down");
			this.closing.set(true);
		}		
	}
}
