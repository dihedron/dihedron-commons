/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.os;

import org.dihedron.core.License;


/**
 * The "endianness" of the architecture.
 * 
 * @author Andrea Funto'
 */
@License
public enum Endianness {
	
	/**
	 * The architecture is little-endian.
	 */
	LITTLE_ENDIAN("little endian"),
	
	/**
	 * the architecture is big-endian.
	 */
	BIG_ENDIAN("big endian");
	
	/**
	 * Constructor.
	 *
	 * @param label
	 *   a user-friendly label for the endianness.
	 */
	private Endianness(String label) {
		this.label = label;
	}
	
	/**
	 * Returns a user-friendly representation of the endianness.
	 * 
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return label;
	}
	
	/**
	 * A user-friendly label for the endianness.
	 */
	private String label;		
}
