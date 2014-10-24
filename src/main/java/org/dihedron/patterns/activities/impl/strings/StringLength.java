/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.impl.strings;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.AbstractTransformation;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;

/**
 * @author Andrea Funto'
 */
@License
public class StringLength extends AbstractTransformation {

	/**
	 * @see org.dihedron.patterns.activities.Activity#execute(org.dihedron.patterns.activities.ActivityContext, org.dihedron.patterns.activities.types.Vector)
	 */
	@Override
	public Vector transform(ActivityContext context, Vector vector) throws ActivityException {
		Vector results = new Vector();
		results.setSize(vector.size());
		int i = 0;
		for(Object element : vector) {
			results.set(i++, transform(context, new Scalar(element)));
		}
		return results;
	}

	/**
	 * @see org.dihedron.patterns.activities.Activity#execute(org.dihedron.patterns.activities.ActivityContext, java.lang.Object)
	 */
	@Override
	protected Scalar transform(ActivityContext context, Scalar element) throws ActivityException {
		if(element.get() instanceof String && element.get() != null) {
			return new Scalar( ((String)element.get()).length());
		}
		return new Scalar(-1);
	}
}
