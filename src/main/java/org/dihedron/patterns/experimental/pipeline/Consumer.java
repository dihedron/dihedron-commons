/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.experimental.pipeline;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public interface Consumer<S, T> {
	
	/**
	 * Consumes the given piece of information, possibly transforming it into a 
	 * different type of object.
	 * 
	 * @param data
	 *   the data to be processed/consumed.
	 * @return
	 *   the result of the processing.
	 */
	T consume(S data) throws PipelineException;	
}
