/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.experimental.pipeline;


/**
 * @author Andrea Funto'
 */
public interface Producer<T> {
	
	/**
	 * Returns whether there is more data available in the source.
	 * 
	 * @return
	 *   whether there is more data available in the source.
	 * @throws PipelineException
	 */
	boolean hasMore() throws PipelineException;
	
	/**
	 * Starts producing elements for consumption by the following consumers.
	 * 
	 * @return
	 *   the result of the processing.
	 */
	T produce() throws PipelineException;	
}
