/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.experimental.pipeline;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public interface ResettableProducer<T> extends Producer<T> {
	
	/**
	 * Resets the producer, starting the production all over again.
	 * 
	 * @throws PipelineException
	 */
	void reset() throws PipelineException;
}
