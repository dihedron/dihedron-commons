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
import org.dihedron.activities.exceptions.ActivityException;
import org.dihedron.activities.types.ActivityData;
import org.dihedron.activities.types.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public abstract class AbstractSplitter extends AbstractActivity {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbstractSplitter.class);

	/**
	 * @see org.dihedron.activities.Activity#execute(org.dihedron.activities.ActivityContext, org.dihedron.activities.types.Vector)
	 */
	@Override
	public ActivityData perform(ActivityContext context, ActivityData data) throws ActivityException {
		if(data instanceof Scalar) {
			return split(context, (Scalar)data);
		}
		logger.error("cardinality mismatch: a splitter should only be invoked on a scalar object");
		throw new ActivityException("Cardinality mismatch: a splitter should only be invoked on a scalar object");
	}
}
