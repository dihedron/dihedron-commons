/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.filters.objects;

import org.dihedron.core.License;
import org.dihedron.core.filters.Filter;

/**
 * @author Andrea Funto'
 */
@License
public class Null<T> extends Filter<T> {

	/**
	 * @see org.dihedron.core.filters.Filter#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(T object) {
		return object == null;
	}
}
