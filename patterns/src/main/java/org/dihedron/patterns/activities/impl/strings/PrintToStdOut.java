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
package org.dihedron.patterns.activities.impl.strings;

import java.util.Random;

import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.AbstractTransformation;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class PrintToStdOut extends AbstractTransformation {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(PrintToStdOut.class);

	/**
	 * @see org.dihedron.patterns.activities.Activity#execute(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Vector)
	 */
	@Override
	public Vector transform(ActivityContext context, Vector vector) throws ActivityException {
		StringBuilder buffer = new StringBuilder();
		for(Object element : vector) {
			if(buffer.length() > 0) {
				buffer.append(", ");
			}
			buffer.append(element != null ? element.toString() : "<null>");
		}
		transform(context, new Scalar(buffer.toString()));
		return vector;
	}

	/**
	 * @see org.dihedron.patterns.activities.Activity#execute(org.dihedron.patterns.activities.ActivityContext, java.lang.Object)
	 */
	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
		try {
			Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
			System.out.println((scalar != null && scalar.get() != null) ? scalar.get().toString() : "<null>");
			return scalar;
		} catch (InterruptedException e) {
			logger.error("thread interrupted while sleeping", e);
			throw new ActivityException("Thread interrupted while sleeping");
		}
	}
}
