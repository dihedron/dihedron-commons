/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.cache;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An output stream wrapper that keeps track of how many bytes were written to 
 * the wrapped stream; this is useful because it provides a way to know how many
 * data are in the storage in an implementation agnostic way.
 * 
 * @author Andrea Funto'
 */
public class CacheOutputStream<T extends OutputStream> extends FilterOutputStream {

	/**
	 * A counter keeping track of the size of the data written to the wrapped
	 * output stream.
	 */
	private long size = 0;
	
	/**
	 * Constructor.
	 *
	 * @param out
	 *   the wrapped output stream.
	 */
	public CacheOutputStream(OutputStream out) {
		super(out);
	}
	
	/**
	 * Returns a reference to the underlying output stream.
	 *  
	 * @return
	 *   a reference to the underlying output stream.
	 */
	@SuppressWarnings("unchecked")
	public T getWrappedStream() {
		return (T)out;
	}

    /**
     * Returns the amount of bytes written to this output stream.
     * 
     * @return
     *   the amount of bytes written to this output stream.
     */
    public long getSize() {
    	return size;
    }
	
    /**
     * @throws IOException 
     * @see java.io.FilterOutputStream#write(byte[])
     */
    @Override
    public void write(byte[] b) throws IOException {
    	out.write(b);
    	size += b.length;
    }
	
    /**
	 * @see java.io.FilterOutputStream#write(int)
	 */
    @Override
    public void write(int b) throws IOException {
    	out.write(b);
    	++size;
    }

    /**
     * @throws IOException 
     * @see java.io.FilterOutputStream#write(byte[], int, int)
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
    	out.write(b, off, len);
    	size += len;
    }  
}
