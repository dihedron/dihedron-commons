/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;


/**
 * @author Andrea Funto'
 */
public abstract class AbstractDestination<M> implements Destination<M> {

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		return other != null && other.getClass().equals(this.getClass()) && ((Destination<M>)other).getId().equals(this.getId());
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
}
