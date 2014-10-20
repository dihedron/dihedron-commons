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
package org.dihedron.activities.impl.logic;

import org.dihedron.activities.ActivityContext;
import org.dihedron.activities.base.Transformation;
import org.dihedron.activities.exceptions.ActivityException;
import org.dihedron.activities.exceptions.InvalidArgumentException;
import org.dihedron.activities.types.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class Not extends Transformation {
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Not.class);

	/**
	 * @see org.dihedron.activities.base.AbstractTransformation#transform(org.dihedron.activities.ActivityContext, org.dihedron.activities.types.Scalar)
	 */
	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		if(!(scalar.get() instanceof Boolean)) {
			// TODO: implement the type mapper!!!!
			logger.error("arguments to logic operators must be boolean, while an instance of {} was received", scalar.get().getClass().getName());
			throw new InvalidArgumentException("Arguments to boolean operators must be boolean"); 
		}
		return new Scalar(!((Boolean)scalar.get()));
	}
}
