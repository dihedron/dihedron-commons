/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.os;

import org.dihedron.core.License;

/**
 * The number of bits used for internal memory addressing by the processor.
 * 
 * @author Andrea Funto'
 */
@License
public enum Addressing {

	/**
	 * Memory addresses are 32-bits wide.
	 */
	SIZE_32("32 bits"),

	/**
	 * Memory addresses are 64-bits wide.
	 */
	SIZE_64("64 bits");

	/**
	 * Constructor.
	 * 
	 * @param label
	 *   a user-friendly label for the number of bits.
	 */
	private Addressing(String label) {
		this.label = label;
	}

	/**
	 * Returns a user-friendly representation of the number of bits.
	 * 
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return label;
	}

	/**
	 * A user-friendly label for the number of bits.
	 */
	private String label;
}
