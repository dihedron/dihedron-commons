/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * An input stream returning a constant integer value.
 * 
 * @author Andrea Funto'
 */
public class ConstantInputStream extends InputStream {

	/**
	 * The constant value to be returned.
	 */
	private int constant = 0;
	
	/**
	 * Default constructor; initialises the constant stream to the value 0.
	 */
	public ConstantInputStream() {
		this(0);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param constant
	 *   the constant value to be returned.
	 */
	public ConstantInputStream(int constant) {
	}

	/**
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		return constant;
	}
}
