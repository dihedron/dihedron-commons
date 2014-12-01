/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import org.dihedron.core.License;


/**
 * The common base task for internal bus tasks.
 * 
 * @author Andrea Funto'
 */
@License
class PrioritisedTask<M> extends Task<M> implements Comparable<PrioritisedTask<M>> {

	/**
	 * Constructor.
	 *
	 * @param destination
	 *   the destination to which the message must be delivered.
	 * @param message
	 *   the message to dispatch.
	 */
	PrioritisedTask(Destination<M> destination, M message) {
		super(destination, message);
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PrioritisedTask<M> other) {
		if(message instanceof Prioritised && other.message instanceof Prioritised) {
			int p1 = ((Prioritised)message).getPriority();
			int p2 = ((Prioritised)other.message).getPriority();
			return p1 < p2 ? -1 : p1 == p2 ? 0 : -1;
		}
		return 0;
	}
}
