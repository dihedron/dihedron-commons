/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Activities library ("Activities").
 *
 * Activities is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Activities is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Activities. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.activities.base;

import org.dihedron.activities.ActivityContext;
import org.dihedron.activities.engine.ActivityEngine;
import org.dihedron.activities.engine.ActivityEngineFactory;
import org.dihedron.activities.engine.ActivityInfo;
import org.dihedron.activities.exceptions.ActivityException;
import org.dihedron.activities.types.Scalar;
import org.dihedron.activities.types.Vector;
import org.dihedron.commons.TypedVector;
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
	 * @see org.dihedron.activities.Activity#execute(org.dihedron.activities.ActivityContext, org.dihedron.activities.types.Vector)
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
			if(vector.size() > 0) {
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
