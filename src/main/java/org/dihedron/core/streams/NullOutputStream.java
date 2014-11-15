/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */
package org.dihedron.core.streams;

import java.io.IOException;
import java.io.OutputStream;

import org.dihedron.core.License;

/**
 * An output stream that writes to nowhere.
 * 
 * @author Andrea Funto'
 */
@License
public class NullOutputStream extends OutputStream {

	/**
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
	}
}
