/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import java.util.concurrent.Executors;

import org.dihedron.core.License;

/**
 * @author Andrea Funto'
 */
@License
public class SynchronousBus2<M> extends AsynchronousBus<M> {
	
	/**
	 * Constructor; the underlying executor service is by default a single thread
	 * executor in order to guarantee the execution (and the restart in case of 
	 * failure) of a single destination at a time.
	 */
	public SynchronousBus2() {
		super(Executors.newSingleThreadExecutor());		
	}		
}
