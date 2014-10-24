/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.TypedVector;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.exceptions.TimedOutException;
import org.dihedron.patterns.activities.types.ActivityData;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code ActivityExecutor}s are able to execute {@code Activity}es asynchronously 
 * by spawning new background threads, or leveraging other asynchronous code 
 * execution mechanisms. Concrete instances act e.g. as adapters for JavaSE 
 * {@code ExecutorService}s or JavaEE asynchronous EJB 3.1 methods.
 *   
 * @author Andrea Funto'
 */
@License
public abstract class ParallelEngine implements ActivityEngine {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ParallelEngine.class);
	
	/**
	 * The queue used to synchronise task execution.
	 */
	private BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
	
	/**
	 * How the engine should wait for activities to complete.
	 */
	private WaitMode mode = WaitMode.WAIT_FOR_ALL;
	
	/**
	 * How many units of time the engine should wait for the background activities 
	 * to complete before it times out. 
	 */
	private long timeout = Long.MAX_VALUE;
	
	/**
	 * The unit to use to evaluate the timeout.
	 */
	private TimeUnit unit = TimeUnit.DAYS;
	
	/**
	 * Sets the wait mode, the number of units and their scale before the wait
	 * for the background activities times out.
	 * 
	 * @param mode
	 *   the wait mode: whether it should wait for all background activities to 
	 *   complete or it will exit as soon as one of them is complete.
	 * @param timeout
	 *   the number of time units (milliseconds, seconds...) to wait before timing 
	 *   out.
	 * @param unit
	 *   the time unit used to express the timeout preiod (seconds, minutes...).
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public ParallelEngine setWaitMode(WaitMode mode, long timeout, TimeUnit unit) {
		this.mode = mode;
		this.timeout = timeout;
		this.unit = unit;
		return this;
	}

	/**
	 * Submits a set of activities to the underlying asynchornous execution
	 * mechanism.
	 *  
	 * @param infos
	 *   the activities, along with the elements of data they will operate upon
	 *   and their context.
	 * @return
	 *   a set of {@code Future}s representing the execution results.
	 */
	@Override
	public Vector execute(TypedVector<ActivityInfo> infos) throws ActivityException {

		if(infos != null && infos.size() > 0) {
			logger.trace("executing {} activities...", infos.size());
			int i = 0;
			TypedVector<Future<ActivityData>> futures = new TypedVector<Future<ActivityData>>();
			for(ActivityInfo info : infos) {
				ActivityCallable callable = new ActivityCallable(i++, queue, info);
				futures.add(submit(callable));
			}
			return wait(futures);
		} else {
			logger.warn("no activities to execute");
		}
		return null;
	}
	
	/**
	 * Waits for all activities to complete before returning.
	 * 
	 * @param futures
	 *   the list of futures to wait for.
	 * @return
	 *   the list of results, once all or one of the input activities are done,
	 *   depending on the {@code WaitMode}.
	 * @throws InterruptedException
	 * @throws TimedOutException 
	 */
	public Vector wait(TypedVector<Future<ActivityData>> futures) throws ActivityException {
		Vector results = new Vector();
		results.setSize(futures.size());
		
		try {
			int count = futures.size();
			logger.trace("waiting for {} activities to complete...", count);
			long newTimeout = timeout;
			while(count-- > 0 && newTimeout > 0) {
				long start = System.currentTimeMillis();
				Integer id = queue.poll(newTimeout, unit);
				newTimeout = newTimeout - unit.convert((System.currentTimeMillis() - start), TimeUnit.MILLISECONDS);
				if(id == null || newTimeout < 0) {
					logger.trace("no activity completed within timeout, exiting");
					throw new TimedOutException("Timeout expired before all activities completed");
				}
				logger.debug("activity '{}' complete (count: {}, queue: {})", id, count, queue.size());
				ActivityData result = futures.get(id).get();
				if(result instanceof Scalar) {
					results.set(id, ((Scalar)result).get());
				} else {
					results.set(id, result);
				}
				
				if(this.mode == WaitMode.WAIT_FOR_ANY) {
					return results;
				}
			}			
			logger.debug("all activities complete");
			return results;
		} catch (ExecutionException e) {
			logger.error("error executing asynchronous activity", e);
			throw new ActivityException("Error executing asynchronous activity", e);
		} catch (InterruptedException e) {
			logger.error("operation interrupted", e);
			throw new ActivityException("Operation interrupted");
		}			
	}

	/**
	 * Assigns the given activity to a background thread of execution and returns 
	 * the corresponding {@code Future} object, holding the result value.
	 * 
	 * @param callable
	 *   the callable object wrapping the {@code Activity} to be executed.
	 * @return
	 *   the activity's {@code Future} object, from which the result value can 
	 *   be retrieved.
	 */
	protected abstract Future<ActivityData> submit(ActivityCallable callable);
}
