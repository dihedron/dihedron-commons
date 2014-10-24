/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.experimental.pipeline.impl;

import org.dihedron.core.License;
import org.dihedron.patterns.experimental.pipeline.PipelineException;
import org.dihedron.patterns.experimental.pipeline.ResettableProducer;


/**
 * @author Andrea Funto'
 */
@License
public class ArraySource<T> implements ResettableProducer<T>  {

	/**
	 * The array of elements on which the producer iterates.
	 */
	private T[] array;
	
	/**
	 * The current index into the backing array. 
	 */
	private int index = 0;

	/**
	 * Constructor.
	 *
	 * @param array
	 *   the array used to produce new elements.
	 */
	public ArraySource(T[] array) {
		this.array = array;
	}
	
	/**
	 * @see org.dihedron.patterns.experimental.pipeline.Producer#hasMore()
	 */
	@Override
	public boolean hasMore() {
		return array != null && index < array.length;
	}	
	
	/**
	 * @see org.dihedron.patterns.streams.Source#start()
	 */
	@Override
	public T produce() throws PipelineException {
		if(hasMore()) { 
			return array[index++];
		} else {
			throw new PipelineException("No more data in input array");
		}
	}

	/**
	 * @see org.dihedron.patterns.experimental.pipeline.ResettableProducer#reset()
	 */
	@Override
	public void reset() {
		index = 0;
	}
}
