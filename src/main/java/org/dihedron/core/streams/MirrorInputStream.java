/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Andrea Funto'
 */
public class MirrorInputStream extends FilterInputStream {
	
	/**
	 * The mirroring output stream.
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
	 * @param in
	 *   the filtered input stream
	 * @param mirror
	 *   the mirror output stream: whatever is read from the input stream is 
	 *   piped into the mirror stream.  
	 */
	public MirrorInputStream(InputStream in, OutputStream mirror) {
		super(in);
		this.mirror = mirror;
	}
	
	/**
	 * Constructor.
	 *
	 * @param in
	 *   the filtered input stream
	 * @param mirror
	 *   the mirror output stream: whatever is read from the input stream is echoed
	 *   into this output stream.
	 * @param autoclose
	 *   whether the mirror stream should be auto-closed upon closure.  
	 */
	public MirrorInputStream(InputStream in, OutputStream mirror, boolean autoclose) {
		super(in);
		this.mirror = mirror;
		this.autoclose = autoclose;
	}
	
	/**   
	 * @see java.io.FilterInputStream#read()
	 */
	@Override
	public int read() throws IOException {
		int result = super.read();
		mirror.write(result);
		return result;
	}

	/**
	 * @see java.io.FilterInputStream#read(byte[])
	 */
	@Override
	public int read(byte b[]) throws IOException {
		int result = read(b, 0, b.length);
		mirror.write(b);
		return result;
	}

	/**
	 * @see java.io.FilterInputStream#read(byte[], int, int)
	 */
	@Override
    public int read(byte b[], int off, int len) throws IOException {
    	int result = in.read(b, off, len);
    	mirror.write(b, off, len);
    	return result;
    }
	
	/**
	 * @see java.io.FilterInputStream#close()
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
