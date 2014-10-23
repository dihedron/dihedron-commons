/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine;

/**
 * An enumeration representing how the activity should behave with respect 
 * to its sub-activities completion.
 * 
 * @author Andrea Funto'
 */
public enum WaitMode {
	/**
	 * Wait for all sub-activities to complete before returning.
	 */
	WAIT_FOR_ALL,
	
	/**
	 * Return as soon as any of the sub-activities has completed.
	 */
	WAIT_FOR_ANY
}	