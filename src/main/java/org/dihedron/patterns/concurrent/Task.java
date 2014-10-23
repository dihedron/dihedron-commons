/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.concurrent;

/**
 * The interface all tasks must implement in order to be executed asynchronously.
 * 
 * @author Andrea Funto'
 */
public interface Task<T> {	
	
	/**
	 * The actual business logic method: the code in this method will be 
	 * executed asynchronously in a thread pool. This object will be available 
	 * to task observers.
	 * 
	 * @return
	 *   a return value.
	 * @throws Exception
	 */
	T execute() throws TaskException;
}
