/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.patterns.experimental.pipeline.impl;

import org.dihedron.patterns.experimental.pipeline.PipelineException;
import org.dihedron.patterns.experimental.pipeline.ResettableProducer;


/**
 * @author Andrea Funto'
 */
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
