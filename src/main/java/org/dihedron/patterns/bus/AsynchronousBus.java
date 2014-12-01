/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class AsynchronousBus<M> extends Bus<M> implements AutoCloseable {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(AsynchronousBus.class);

	/**
	 * The underlying thread pool.
	 */
	private ExecutorService executor;
		
	/**
	 * Constructor; the underlying executor service is by default a cached thread
	 * pool for it seems to provide the best results with small asynchronous tasks.
	 */
	public AsynchronousBus() {
		this(Executors.newCachedThreadPool());		
	}
	
	/**
	 * Constructor.
	 *
	 * @param executor
	 *   a user-provided executor service.
	 */
	public AsynchronousBus(ExecutorService executor) {
		this.executor = executor;		
	}
	
	/**
	 * @see org.dihedron.patterns.bus.Bus#send(java.lang.Object)
	 */
	@Override
	public Bus<M> send(M message) {
		try {
			final CompletionService<Void> completion = new ExecutorCompletionService<Void>(executor);			
			int submitted = 0;
			for(Destination<M> destination : destinations) {
				++submitted; 
				logger.trace("sending message {} to destination {} in thread {}", message, destination, Thread.currentThread().getId());
				completion.submit(new Task<M>(destination, message));
			}
			int completed = 0;
			boolean errors = false;
			while(completed < submitted && !errors) {
				Future<Void> result = completion.take(); //blocks if none available
			      try {
			    	  result.get();
			    	  ++completed;
			    	  logger.trace("one task completed, {} out of {} so far", completed, submitted);
			      } catch(ExecutionException e) {
			    	  logger.error("one task failed", e);
			    	  errors = true;
			    }
			}
		} catch(InterruptedException e) {
			logger.error("interrupted while waiting for destinations to handle their messages", e);
		}
		return this;
	}

	/**
	 * @see org.dihedron.patterns.bus.Bus#post(java.lang.Object)
	 */
	@Override
	public Bus<M> post(M message) {
		final CompletionService<Void> completion = new ExecutorCompletionService<Void>(executor);
		for(Destination<M> destination : destinations) { 
			logger.trace("posting message {} to destination {} in thread {}", message, destination, Thread.currentThread().getId());
			completion.submit(new Task<M>(destination, message));
		}
		return this;
	}

	/**
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() {
		this.executor.shutdown();
	}
}
