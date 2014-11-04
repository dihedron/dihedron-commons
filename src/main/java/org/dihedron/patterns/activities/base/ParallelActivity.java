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
	
	/**
	 * @see org.dihedron.patterns.activities.base.AbstractTransformation#transform(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Scalar)
	 */
	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		throw new ActivityException("Not implemented");
	}	
}
