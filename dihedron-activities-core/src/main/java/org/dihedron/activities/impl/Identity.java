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
package org.dihedron.activities.impl;

import org.dihedron.activities.ActivityContext;
import org.dihedron.activities.base.AbstractTransformation;
import org.dihedron.activities.exceptions.ActivityException;
import org.dihedron.activities.types.Scalar;
import org.dihedron.activities.types.Vector;

/**
 * @author Andrea Funto'
 */
public class Identity extends AbstractTransformation {

	/**
	 * @see org.dihedron.activities.base.AbstractTransformation#transform(org.dihedron.activities.ActivityContext, org.dihedron.activities.types.Scalar)
	 */
	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		return scalar;
	}

	/**
	 * @see org.dihedron.activities.base.AbstractTransformation#transform(org.dihedron.activities.ActivityContext, org.dihedron.activities.types.Vector)
	 */
	@Override
	protected Vector transform(ActivityContext context, Vector vector) throws ActivityException {		
		return vector;
	}
}
