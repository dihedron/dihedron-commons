/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.base;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.engine.ActivityEngineFactory;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class ParallelActivity extends CompoundActivity {
	
//@License
//private class DefaultAggregator extends AbstractAggregator {
//
//		private ActivityData data;
//		
//		public void setOriginalData(ActivityData data) {
//			this.data = data;
//		}
//		
//		/**
//		 * @see org.dihedron.patterns.activities.base.AbstractAggregator#aggregate(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Vector)
//		 */
//		@Override
//		protected Scalar aggregate(ActivityContext context, Vector vector) throws ActivityException {
//			if(data instanceof Scalar) {
//				return (Scalar)data;
//			}
//			return new Scalar(data);
//		}
//		
//	}
	
	/**
	 * The logger.
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(ParallelActivity.class);

	/**
	 * The object that will merge the results.
	 */
	@SuppressWarnings("unused")
	private AbstractAggregator aggregator;
		
	/**
	 * Constructor; initialises the activity with the default engine.
	 */
	public ParallelActivity() {
		setEngine(ActivityEngineFactory.getDefaultEngine());
	}
	
	/**
	 * Sets the reference to the aggregator activity, which will merge the results 
	 * of the parallel processing on the same entity into the resulting, single 
	 * object. 
	 * 
	 * @param aggregator
	 *   the aggregator activity.
	 */
	public void setAggregator(AbstractAggregator aggregator) {
		this.aggregator = aggregator;
	}
		
//	/**
//	 * Starts its set of sub-activities asynchronously, returning the result
//	 * 
//	 * @return
//	 *   the corresponding set of futures.
//	 * @see 
//	 *   org.dihedron.patterns.activities.Activity#execute(org.dihedron.patterns.activities.ActivityContext)
//	 */
//	@Override
//	public ActivityData perform(ActivityContext context, ActivityData data) throws ActivityException {
//		
//		if(executor == null) {
//			executor = ActivityExecutorFactory.makeDefaultExecutor();
//		}
//		
//		Vector results = null;
//		try {
//			if(activities.size() == 0) {
//				logger.error("there must be at least one activity!");
//				throw new ActivityException("Empty activity set: there must be at least one activity to perform");
//			} else if(activities.size() > 1) {
//				int i = 0;
//				logger.trace("executing {} activities in parallel on the same chunk of data", activities.size());
//				// prepare the input elements
//				TypedVector<ActivityInfo> infos = new TypedVector<ActivityInfo>();
//				infos.setSize(activities.size());
//				if(data != null && data instanceof Copyable) {
//					ActivityInfo info = new ActivityInfo();
//					info.setActivity(activities.get(0));
//					info.setContext(context);
//					info.setData(data);
//					infos.set(0, info);
//					logger.trace("cloning the original single instance of element");
//					for(i = 1; i < activities.size(); ++i) {
//						info = new ActivityInfo();
//						info.setActivity(activities.get(i));
//						info.setContext(context);
//						info.setData((ActivityData)((Copyable)data).clone());
//						infos.set(i, info);
//					}
//				} else {
//					logger.warn("running a set of activities in parallel against a single, shared instance of element");
//					for(i = 0; i < activities.size(); ++i) {
//						ActivityInfo info = new ActivityInfo();
//						info.setContext(context);
//						info.setData(data);
//						infos.set(i, info);
//					}
//				}
//		
//				// now fill the info about the activity and submit them all
//				i = 0;
//				for(Activity activity : activities) {
//					infos.get(i).setActivity(activity);
//				}
//				
//				TypedVector<Future<Object>> futures = executor.submit(infos);			
//				
//				switch(waitMode) {
//				case WAIT_FOR_ALL:
//					results = executor.waitForAll(futures, waitTimeout, waitTimeUnit);
//					break;
//				case WAIT_FOR_ANY:
//					results = executor.waitForAny(futures, waitTimeout, waitTimeUnit);
//					break;
//				}
//				// aggregate the result of the N parallel executions on the same data
//				// into a new scalar entity to be returned	
//				if(aggregator == null) {
//					logger.warn("no aggregator provided, using default one");
//					DefaultAggregator a = new DefaultAggregator();
//					a.setOriginalData(data);
//					return a.perform(context, results);
//				}
//				return aggregator.perform(context, results);
//			} else {
//				logger.trace("running the one parallel activity in the main thread");
//				return activities.get(0).perform(context, data);
//			}
//		} catch (CloneNotSupportedException e) {
//			logger.error("input data does not support cloning", e);
//			throw new ActivityException("Input data does not support cloning");
//		}
//	}

	/**
	 * @see org.dihedron.patterns.activities.base.AbstractTransformation#transform(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Scalar)
	 */
	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		throw new ActivityException("Not implemented");
	}	
}
