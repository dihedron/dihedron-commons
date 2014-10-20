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
package org.dihedron.patterns.activities.impl.logic;

import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.AbstractAggregator;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.exceptions.InvalidArgumentException;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class Or extends AbstractAggregator {
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Or.class);

	/**
	 * @see org.dihedron.patterns.activities.base.AbstractAggregator#aggregate(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Vector)
	 */
	@Override
	protected Scalar aggregate(ActivityContext context, Vector vector) throws ActivityException {
		if(vector == null || vector.size() == 0) {
			return new Scalar(false);
		}
		boolean or = true;
		for(Object object : vector) {
			if(object instanceof Boolean) {
				Boolean bool = (Boolean)object;
				or = or && bool;
			} else {
				// TODO: implement the type mapper!!!!
				logger.error("arguments to logic operators must be boolean, while an instance of {} was received", object.getClass().getName());
				throw new InvalidArgumentException("Arguments to boolean operators must be boolean");
			}
			if(or) break;
		}
		return new Scalar(or);
	}

}
