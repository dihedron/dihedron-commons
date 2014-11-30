/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import java.util.Comparator;

/**
 * @author Andrea Funto'
 */
class PriorityComparator<M> implements Comparator<PrioritisedTask<M>> {

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(PrioritisedTask<M> o1, PrioritisedTask<M> o2) {
		return o1.compareTo(o2);
	}
}	