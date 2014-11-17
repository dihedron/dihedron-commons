/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.streams;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An output stream that actually mirrors whatever is written to it to a secondary
 * "mirror" stream. This is in effect an equivalent of a "tee" output stream.
 * 
 * @author Andrea Funto'
 */
public class MirrorOutputStream extends FilterOutputStream {

	/**
	 * The mirroring stream.
	 */
	private OutputStream mirror;
	
	/**
	 * Whether the mirroring stream should be closed automatically upon main 
	 * stream closure.
	 */
	private boolean autoclose = true;
	
	/**
	 * Constructor.
	 *
	 * @param out
	 *   the filtered output stream
	 * @param mirror
	 *   the mirror output stream.  
	 */
	public MirrorOutputStream(OutputStream out, OutputStream mirror) {
		super(out);
		this.mirror = mirror;
	}
	
	/**
	 * Constructor.
	 *
	 * @param out
	 *   the filtered output stream
	 * @param mirror
	 *   the mirror output stream.
	 * @param autoclose
	 *   whether the mirror stream should be auto-closed upon closure.  
	 */
	public MirrorOutputStream(OutputStream out, OutputStream mirror, boolean autoclose) {
		super(out);
		this.mirror = mirror;
		this.autoclose = autoclose;
	}
		
	/**
	 * @see java.io.FilterOutputStream#write(int)
	 */
	@Override
    public void write(int b) throws IOException {
    	super.write(b);
    	mirror.write(b);
    }

	/**
	 * @see java.io.FilterOutputStream#write(byte[])
	 */
	@Override
    public void write(byte b[]) throws IOException {
    	super.write(b);
        mirror.write(b);
    }

	/**
	 * @see java.io.FilterOutputStream#write(byte[], int, int)
	 */
	@Override
    public void write(byte b[], int off, int len) throws IOException {
    	super.write(b, off, len);
    	mirror.write(b, off, len);
    }
	
	/**
	 * @see java.io.FilterOutputStream#flush()
	 */
	@Override
    public void flush() throws IOException {
    	super.flush();
    	mirror.flush();
    }

	/**
	 * @see java.io.FilterOutputStream#close()
	 */
	@Override
    public void close() throws IOException {
		super.close();
		if(autoclose) {
			try (OutputStream ostream = mirror) {
	            mirror.flush();
	        }
		}
    }	
}
