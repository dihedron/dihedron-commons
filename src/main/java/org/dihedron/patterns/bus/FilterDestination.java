/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;


/**
 * An example of filter destination; sub-classes can build logic around the 
 * invocation of the original destination.  
 * 
 * @author Andrea Funto'
 */
public abstract class FilterDestination<M> extends AbstractDestination<M> {

	/**
	 * The original filtered destination.
	 */
	protected Destination<M> destination;
	
	/**
	 * Constructor.
	 *
	 * @param destination
	 *   the original filtered destination.
	 */
	protected FilterDestination(Destination<M> destination) {
		this.destination = destination;
	}
}
