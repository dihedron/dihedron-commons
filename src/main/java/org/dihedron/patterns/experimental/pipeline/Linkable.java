/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.experimental.pipeline;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public abstract class Linkable<T> {
	
	/**
	 * The next stage in the pipeline.
	 */
	protected T next;
		
	/**
	 * Attaches the given stage to the pipeline.
	 * 
	 * @see org.dihedron.patterns.experimental.pipeline.Linkable#attach(org.dihedron.patterns.experimental.pipeline.Consumer)
	 */
	public T attach(T next) {
		this.next = next;
		return next;
	}
}
