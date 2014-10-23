/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.impl.strings;

import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.AbstractAggregator;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;

/**
 * @author Andrea Funto'
 */
public class Concatenate extends AbstractAggregator {

	/**
	 * @see org.dihedron.patterns.activities.Activity#execute(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Vector)
	 */
	@Override
	public Scalar aggregate(ActivityContext context, Vector elements) throws ActivityException {
		StringBuilder buffer = new StringBuilder();
		for(Object element : elements) {
			if(buffer.length() > 0) {
				buffer.append(", ");
			}
			buffer.append(element != null ? element.toString() : "<null>");
		}
		return new Scalar(buffer.toString());
	}
}
