/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.streams;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dihedron.core.License;

/**
 * An output stream class writing to multiple output streams in
 * parallel.
 * 
 * @author Andrea Funto'
 */
@License
public class TeeOutputStream extends OutputStream implements DataOutput,AutoCloseable {
	
	/**
	 * The output streams to which each bite will be routed.
	 */
	private final Map<OutputStream, Boolean> streams = new LinkedHashMap<>();

	/**
	 * Constructor.
	 * 
	 * @param streams
	 *   a collection of streams to which each byte will be routed.
	 */
	public TeeOutputStream(Collection<OutputStream> streams) {
		if(streams != null) {
			for(OutputStream stream : streams) {
				this.streams.put(stream, true);
			}
		}
	}
	
	/**
	 * Constructor.
	 * 
	 * @param streams
	 *   a collection of streams to which each byte will be routed.
	 */
	public TeeOutputStream(OutputStream ... streams) {
		if(streams != null) {
			for(OutputStream stream : streams) {
				this.streams.put(stream, true);
			}
		}
	}	

	/**
	 * Adds an output stream; by default the stream will be auto-closed.
	 * 
	 * @param stream
	 *   the stream to add to the T.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public TeeOutputStream with(OutputStream stream) {
		return with(stream, false);
	}
	
	/**
	 * Adds an output stream; this method allows to specify whether the 
	 * stream should be closed when the T stream is closed. If you want to 
	 * use standard output as one of the streams in the T, this is the method
	 * to use, passing in {@code false} for parameter {@code autoclose}.
	 * 
	 * @param stream
	 *   the stream to add to the T.
	 * @param autoclose
	 *   whether the stream should be closed automatically when the T is closed.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public TeeOutputStream with(OutputStream stream, boolean autoclose) {
		if(stream != null) {
			this.streams.put(stream,  autoclose);
		}
		return this;
	}
	
	/**
	 * Returns the stream at the given index.
	 * 
	 * @param index
	 *   the index of the stream to return.
	 * @return
	 *   an OutputStream if available at the given index, null 
	 *   otherwise.
	 */
	public OutputStream getStreamAt(int index) {
		if(index < streams.size()) {
			int i = 0;
			for(Entry<OutputStream, Boolean> entry : streams.entrySet()) {
				if(i++ == index) {
					return entry.getKey();
				}
			}
		}
		return null;
	}

	/**
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		for(OutputStream stream : streams.keySet()) {
			stream.write(b);
		}
	}
	
	/**
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte [] buffer, int offset, int length) throws IOException {
		for(OutputStream stream : streams.keySet()) {
			stream.write(buffer, offset, length);
		}
	}

	/**
	 * @see java.io.DataOutput#writeBoolean(boolean)
	 */
	@Override
	public void writeBoolean(boolean value) throws IOException {
		for(OutputStream stream : streams.keySet()) {
			stream.write(value ? 1 : 0);
		}
	}

	/**
	 * @see java.io.DataOutput#writeByte(int)
	 */
	@Override
	public void writeByte(int value) throws IOException {
		for(OutputStream stream : streams.keySet()) {
			stream.write(value);
		}
	}

	/**
	 * @see java.io.DataOutput#writeShort(int)
	 */
	@Override
	public void writeShort(int value) throws IOException {
		byte [] buffer = { 
				(byte)((value >>> 8) & 0xFF), 
				(byte)((value >>> 0) & 0xFF) 
		};
		write(buffer, 0, buffer.length);
	}

	/**
	 * @see java.io.DataOutput#writeChar(int)
	 */
	@Override
	public void writeChar(int value) throws IOException {
		byte [] buffer = {
				(byte)((value >>> 8) & 0xFF),
				(byte)((value >>> 0) & 0xFF)
		};
		write(buffer, 0, buffer.length);
	}

	/**
	 * @see java.io.DataOutput#writeInt(int)
	 */
	@Override
	public void writeInt(int value) throws IOException {
		byte [] buffer = {
				(byte)((value >>> 24) & 0xFF),
				(byte)((value >>> 16) & 0xFF),
				(byte)((value >>>  8) & 0xFF),
				(byte)((value >>>  0) & 0xFF)
		};
		write(buffer, 0, buffer.length);		
	}

	/**
	 * @see java.io.DataOutput#writeLong(long)
	 */
	@Override
	public void writeLong(long value) throws IOException {
		byte buffer[] = {
				(byte)(value >>> 56),
				(byte)(value >>> 48),
				(byte)(value >>> 40),
				(byte)(value >>> 32),
				(byte)(value >>> 24),
				(byte)(value >>> 16),
				(byte)(value >>>  8),
				(byte)(value >>>  0)
		};
        write(buffer, 0, buffer.length);
	}

	/**
	 * @see java.io.DataOutput#writeFloat(float)
	 */
	@Override
	public void writeFloat(float value) throws IOException {
		writeInt(Float.floatToIntBits(value));
	}

	/**
	 * @see java.io.DataOutput#writeDouble(double)
	 */
	@Override
	public void writeDouble(double value) throws IOException {
		writeLong(Double.doubleToLongBits(value));
	}

	/**
	 * @see java.io.DataOutput#writeBytes(java.lang.String)
	 */
	@Override
	public void writeBytes(String string) throws IOException {
		byte[] buffer = new byte[string.length()];
		for (int i = 0 ; i < buffer.length ; i++) {
        	buffer[i] = (byte)string.charAt(i);
        }
		write(buffer, 0, buffer.length);
	}

	/**
	 * @see java.io.DataOutput#writeChars(java.lang.String)
	 */
	@Override
	public void writeChars(String string) throws IOException {
		int length = string.length();
		byte [] buffer = new byte[length * 2];
		
        for (int i = 0 ; i < length ; i+=2) {
            int v = string.charAt(i);
            buffer[i] = (byte)((v >>> 8) & 0xFF);
            buffer[i+1] = (byte)((v >>> 0) & 0xFF);
        }
		write(buffer, 0, buffer.length);		
	}

	/**
	 * @see java.io.DataOutput#writeUTF(java.lang.String)
	 */
	@Override
	public void writeUTF(String string) throws IOException {
		int strlen = string.length();        

        // loop over the characters in the input string and, based on their code 
        // point, decide how many bytes will be needed to encode each of them
		int utflen = 0;
        for (int i = 0; i < strlen; i++) {
            int c = string.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                utflen++;
            } else if (c > 0x07FF) {
                utflen += 3;
            } else {
                utflen += 2;
            }
        }

        if (utflen > 65535) {
            throw new UTFDataFormatException("encoded string too long: " + utflen + " bytes");
        }

        // we need two additional bytes for the initial BOM 
        byte[] buffer = new byte[utflen + 2];
        buffer[0] = (byte) ((utflen >>> 8) & 0xFF);
        buffer[1] = (byte) ((utflen >>> 0) & 0xFF);
        
        // now encode the string, one character at a time, into the byte array
        for (int i = 0, count = 2; i < strlen; i++){
            int c = string.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                buffer[count++] = (byte) c;
            } else if (c > 0x07FF) {
                buffer[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                buffer[count++] = (byte) (0x80 | ((c >>  6) & 0x3F));
                buffer[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            } else {
                buffer[count++] = (byte) (0xC0 | ((c >>  6) & 0x1F));
                buffer[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            }
        }
        // now write out to streams
        write(buffer, 0, buffer.length);	
	}
	
	/**
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		IOException exception = null;
		for(OutputStream stream : streams.keySet()) {
			try {
				stream.flush();
			} catch (IOException e) {
				// do nothing, just remember there has been an error
				// and throw an exception at the end.
				exception = e;
			}
		}
		if(exception != null) {
			throw new IOException("Error closing at least one of the wrapped output streams.");
		}
	}
	
	/**
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		IOException exception = null;
		for(Entry<OutputStream, Boolean> entry : streams.entrySet()) {
			if(entry.getValue()) { 
				// stream must be closed upon exit
				try {
					entry.getKey().close();
				} catch (IOException e) {
					// do nothing, just remember there has been an error
					// and throw an exception at the end.
					exception = e;
				}
			}
		}
		if(exception != null) {
			throw new IOException("Error closing at least one of the wrapped output streams.");
		}
	}
}
