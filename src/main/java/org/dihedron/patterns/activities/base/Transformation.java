/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.base;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.TypedVector;
import org.dihedron.patterns.activities.engine.ActivityEngine;
import org.dihedron.patterns.activities.engine.ActivityEngineFactory;
import org.dihedron.patterns.activities.engine.ActivityInfo;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Subclasses of this class are able to operate on a vector of elements or (as a 
 * side case for size of vector == 1) on a single element; the cardinality of the
 * input and output vectors should be the same, so any subclass should act as
 * a transformation of the input set into another set with the same size.
 *
 * @author Andrea Funto'
 */
@License
public abstract class Transformation extends AbstractTransformation {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(Transformation.class);

	/**
	 * The engine that will take care of parallel or sequential execution of the
	 * various activities.
	 */
	protected ActivityEngine engine = null;
	
	/**
	 * Sets a reference to the execution engine that will apply the transformation.
	 * 
	 * @param engine
	 *   the engine that will perform the tasks.
	 * @return
	 *   the object itself, to enable method chaining.
	 */
	public Transformation setEngine(ActivityEngine engine) {
		this.engine = engine;
		return this;
	}
	
	/**
	 * @see org.dihedron.patterns.activities.Activity#execute(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Vector)
	 */
	@Override
	protected Vector transform(ActivityContext context, Vector vector) throws ActivityException {

		if (engine == null) {
			engine = ActivityEngineFactory.getDefaultEngine();
		}
		
		Vector results = null;
		
		logger.trace("performing activity '{}' on a vector of {} elements...", this.getId(), vector.size());
		
		if(vector.size() > 1 || ! (this instanceof AbstractActivity)) {
			// prepare the information about the activities to run
			TypedVector<ActivityInfo> infos = new TypedVector<ActivityInfo>();
			for(Object element : vector) {
				logger.trace("preparing info for activity '{}' on element '{}'", this.getId(), element);
				ActivityInfo info = new ActivityInfo();
				info.setActivity(this);
				info.setData(element instanceof Scalar ? (Scalar)element : new Scalar(element));
				info.setContext(context);
				infos.add(info);
			}
			results = (Vector)engine.execute(infos);
		} else {
			results = new Vector();
			if(!vector.isEmpty()) {
				logger.trace("running on a single element in the main thread");
				Scalar scalar = vector.get(0) instanceof Scalar ? (Scalar)vector.get(0) : new Scalar(vector.get(0));
				results.add(transform(context, scalar));
			} else {
				logger.warn("running on an empty vector");
			}
		}
		return results;
	}
}
