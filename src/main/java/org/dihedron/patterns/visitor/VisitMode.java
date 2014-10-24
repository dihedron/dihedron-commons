/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor;

import org.dihedron.core.License;

/**
 * An enumeration representing whether the object properties must be accessed
 * in read only mode or in read/write mode.
 *  
 * @author Andrea Funto'
 */
@License
public enum VisitMode {
	
	/**
	 * The sub-nodes will be accessed in read-only mode.
	 */
	READ_ONLY,
	
	/**
	 * The sub-nodes will be accessed in read and write mode.
	 */
	READ_WRITE
}