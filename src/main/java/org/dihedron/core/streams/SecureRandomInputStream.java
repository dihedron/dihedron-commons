/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.streams;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Random;

import org.dihedron.core.License;

/**
 * An input stream returning a random sequence of non-negative integer values; 
 * randomness is delegated to security providers and is guaranteed to have more
 * enthropy than simple #{@link java.util.Random}-based streams.
 * 
 * @author Andrea Funto'
 */
@License
public class SecureRandomInputStream extends InputStream {
	
	/**
	 * A constant representing the lack of bounds to the generated random values.
	 */
	public static final int NO_LIMIT = -1;

	/**
	 * The internal secure random number generator.
	 */
	private Random random = null; 
	
	/**
	 * The upper bound to generated random values (by default, no limit).
	 */
	private int limit = NO_LIMIT; 
	
	/**
	 * Default constructor.
	 */
	public SecureRandomInputStream() {
		random = new SecureRandom();
	}	

	/**
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		return limit == NO_LIMIT ? random.nextInt() : random.nextInt(limit);
	}
}
