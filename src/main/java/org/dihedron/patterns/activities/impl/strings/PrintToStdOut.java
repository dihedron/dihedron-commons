/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.impl.strings;

import java.util.Random;

import org.dihedron.core.License;
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
@License
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
