/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;


/**
 * @author Andrea Funto'
 */
public abstract class TypedMessage<T extends Enum<?>> {
	
	/**
	 * The type of message.
	 */
	private T type;
	
	/**
	 * Constructor.
	 * 
	 * @param type
	 *   the type of message.
	 */
	protected TypedMessage(T type) {
		this.type = type;
	}

	/**
	 * Returns the message type.
	 * 
	 * @return
	 *   the message type, as a convenient value to switch upon.
	 */
	public T getType() {
		return type;
	}
}
