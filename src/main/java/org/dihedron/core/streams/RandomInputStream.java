/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */  
package org.dihedron.core.streams;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.dihedron.core.License;

/**
 * An input stream returning a random sequence of non-negative integer values.
 * 
 * @author Andrea Funto'
 */
@License
public class RandomInputStream extends InputStream {
	
	/**
	 * A constant representing the lack of bounds to the generated random values.
	 */
	public static final int NO_LIMIT = -1;

	/**
	 * The internal random number generator.
	 */
	private Random random = null; 
	
	/**
	 * The upper bound to generated random values (by default, no limit).
	 */
	private int limit = NO_LIMIT; 
	
	/**
	 * Default constructor; initialises the internal random number generator using 
	 * the current time as reported by #{@link java.lang.System#nanoTime()}.
	 */
	public RandomInputStream() {
		this(System.nanoTime());
	}
	
	/**
	 * Constructor.
	 * 
	 * @param seed
	 *   the seed used to initialise the internal random number generator.
	 */
	public RandomInputStream(long seed) {
		this(seed, NO_LIMIT);		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param seed
	 *   the seed used to initialise the internal random number generator.
	 * @param limit
	 *   the upper bound (exclusive) for the generated integer value: if 50 is
	 *   given here, the stream will generate numbers between 0 and 49 (included).
	 */
	public RandomInputStream(long seed, int limit) {
		random = new Random(seed);
		this.limit = limit;
	}

	/**
	 * Constructor; initialises the internal random number generator using the 
	 * provided generator.
	 * 
	 * @param random
	 *   the random number generator.
	 */
	public RandomInputStream(Random random) {
		this(random, NO_LIMIT);
	}
	
	/**
	 * Constructor; initialises the internal random number generator using the 
	 * provided generator.
	 * 
	 * @param random
	 *   the random number generator.
	 * @param limit
	 *   the upper limit to the generated values.
	 */
	public RandomInputStream(Random random, int limit) {
		this.random = random;
		this.limit = limit;
	}	
	
	/**
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		return limit == NO_LIMIT ? random.nextInt() : random.nextInt(limit);
	}
}
