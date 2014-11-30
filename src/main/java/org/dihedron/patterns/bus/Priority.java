/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;


/**
 * @author Andrea Funto'
 */
public interface Priority {
	
	/**
	 * Compares this object's priority with the other's.
	 * 
	 * @param other
	 * @return
	 */
	int compareTo(Priority other);
}
