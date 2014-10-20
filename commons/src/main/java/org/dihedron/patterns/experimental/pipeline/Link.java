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
package org.dihedron.patterns.experimental.pipeline;


/**
 * An element representing a link between producer and consumer in a chain. 
 * The producer's output data is fed into the consumer, which may return a 
 * different data type. 
 * 
 * @author Andrea Funto'
 */
public class Link<S, T> {
	
	/**
	 * The producer (the preceding element in the chain).
	 */
	protected Producer<S> producer;
	
	/**
	 * The consumer (the following element in the chain).
	 */
	protected Consumer<S, T> consumer;
	
	/**
	 * Constructor.
	 */
	public Link() {
	}
	
	/**
	 * Constructor.
	 *
	 * @param producer
	 *   the producer, that is the element before this link in the chain.
	 * @param consumer
	 *   the consumer, that is the element after this link in the chain.
	 */
	public Link(Producer<S> producer, Consumer<S, T> consumer) {
		this.producer = producer;
		this.consumer = consumer;
	}
	
	/**
	 * Sets the reference to the previous element in the chain (the producer).
	 * 
	 * @param producer
	 *   the previous element in the chain (the producer).
	 * @return
	 *   the object itself, for method chaining.
	 */
	public Link<S, T> from(Producer<S> producer) {
		this.producer = producer;
		return this;
	}
	
	/**
	 * Sets the reference to the following element in the chain (the consumer).
	 * 
	 * @param consumer
	 *   the following element in the chain (the consumer).
	 * @return
	 *   the object itself, for method chaining.
	 */
	public Link<S, T> to(Consumer<S, T> consumer) {
		this.consumer = consumer;
		return this;
	}
}
