/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.filters.logic;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
@License
public class False<T> extends Filter<T> {

	/**
	 * @see org.dihedron.core.filters.Filter#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(T object) {
		return false;
	}
}
