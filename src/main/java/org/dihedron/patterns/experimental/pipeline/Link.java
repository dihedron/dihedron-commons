/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.experimental.pipeline;

import org.dihedron.core.License;


/**
 * An element representing a link between producer and consumer in a chain. 
 * The producer's output data is fed into the consumer, which may return a 
 * different data type. 
 * 
 * @author Andrea Funto'
 */
@License
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
