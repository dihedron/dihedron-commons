/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.bus.simplistic;

import org.dihedron.core.License;

/**
 * A bus observer that provides a default implementation for the callback method
 * with a reference to the sender object.
 * 
 * @author Andrea Funto'
 */
@License
public abstract class DefaultBusObserver<M> implements BusObserver<M> {

	/**
	 * @see org.dihedron.patterns.bus.simplistic.BusObserver#onMessage(java.lang.Object, java.lang.Object, java.lang.Object[])
	 */
	public void onMessage(Object sender, M message, Object ... args) {
		onMessage(message, args);
	}
}	
