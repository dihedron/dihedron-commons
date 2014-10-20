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

import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.AbstractTransformation;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;

/**
 * @author Andrea Funto'
 */
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
