/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.streams;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private final List<OutputStream> streams = new ArrayList<>();

	/**
	 * Constructor.
	 * 
	 * @param streams
	 *   a collection of streams to which each byte will be routed.
	 */
	public TeeOutputStream(Collection<OutputStream> streams) {
		this.streams.addAll(streams);
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
				this.streams.add(stream);
			}
		}
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
			return streams.get(index);
		}
		return null;
	}

	/**
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		for(OutputStream stream : streams) {
			stream.write(b);
		}
	}
	
	/**
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		IOException exception = null;
		for(OutputStream stream : streams) {
			try {
				stream.close();
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
	 * @see java.io.DataOutput#writeBoolean(boolean)
	 */
	@Override
	public void writeBoolean(boolean v) throws IOException {
		for(OutputStream stream : streams) {
			stream.write(v ? 1 : 0);
		}
	}

	/**
	 * @see java.io.DataOutput#writeByte(int)
	 */
	@Override
	public void writeByte(int v) throws IOException {
		for(OutputStream stream : streams) {
			stream.write(v);
		}
	}

	/**
	 * @see java.io.DataOutput#writeShort(int)
	 */
	@Override
	public void writeShort(int v) throws IOException {
		for(OutputStream stream : streams) {
	        stream.write((v >>> 8) & 0xFF);
	        stream.write((v >>> 0) & 0xFF);
		}
	}

	/**
	 * @see java.io.DataOutput#writeChar(int)
	 */
	@Override
	public void writeChar(int v) throws IOException {
		for(OutputStream stream : streams) {
	        stream.write((v >>> 8) & 0xFF);
	        stream.write((v >>> 0) & 0xFF);
		}
	}

	/**
	 * @see java.io.DataOutput#writeInt(int)
	 */
	@Override
	public void writeInt(int v) throws IOException {
		for(OutputStream stream : streams) {
	        stream.write((v >>> 24) & 0xFF);
	        stream.write((v >>> 16) & 0xFF);
	        stream.write((v >>>  8) & 0xFF);
	        stream.write((v >>>  0) & 0xFF);
		}		
	}

	/**
	 * @see java.io.DataOutput#writeLong(long)
	 */
	@Override
	public void writeLong(long v) throws IOException {
		byte buffer[] = new byte[8];	
        buffer[0] = (byte)(v >>> 56);
        buffer[1] = (byte)(v >>> 48);
        buffer[2] = (byte)(v >>> 40);
        buffer[3] = (byte)(v >>> 32);
        buffer[4] = (byte)(v >>> 24);
        buffer[5] = (byte)(v >>> 16);
        buffer[6] = (byte)(v >>>  8);
        buffer[7] = (byte)(v >>>  0);		
		for(OutputStream stream : streams) {
			stream.write(buffer, 0, buffer.length);
		}
	}

	/**
	 * @see java.io.DataOutput#writeFloat(float)
	 */
	@Override
	public void writeFloat(float v) throws IOException {
		writeInt(Float.floatToIntBits(v));
	}

	/**
	 * @see java.io.DataOutput#writeDouble(double)
	 */
	@Override
	public void writeDouble(double v) throws IOException {
		writeLong(Double.doubleToLongBits(v));
	}

	/**
	 * @see java.io.DataOutput#writeBytes(java.lang.String)
	 */
	@Override
	public void writeBytes(String s) throws IOException {
		byte[] buffer = new byte[s.length()];
		for (int i = 0 ; i < buffer.length ; i++) {
        	buffer[i] = (byte)s.charAt(i);
        }
		for(OutputStream stream : streams) {
			stream.write(buffer, 0, buffer.length);
		}
	}

	/**
	 * @see java.io.DataOutput#writeChars(java.lang.String)
	 */
	@Override
	public void writeChars(String s) throws IOException {
		int length = s.length();
		byte [] buffer = new byte[length * 2];
		
        
        for (int i = 0 ; i < length ; i+=2) {
            int v = s.charAt(i);
            buffer[i] = (byte)((v >>> 8) & 0xFF);
            buffer[i+1] = (byte)((v >>> 0) & 0xFF);
        }
		for(OutputStream stream : streams) {
			stream.write(buffer, 0, buffer.length);
		}		
	}

	/**
	 * @see java.io.DataOutput#writeUTF(java.lang.String)
	 */
	@Override
	public void writeUTF(String str) throws IOException {
		int strlen = str.length();
        int utflen = 0;
        int c, count = 0;

        /* use charAt instead of copying String to char array */
        for (int i = 0; i < strlen; i++) {
            c = str.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                utflen++;
            } else if (c > 0x07FF) {
                utflen += 3;
            } else {
                utflen += 2;
            }
        }

        if (utflen > 65535)
            throw new UTFDataFormatException(
                "encoded string too long: " + utflen + " bytes");

        byte[] bytearr = null;
        if (out instanceof DataOutputStream) {
            DataOutputStream dos = (DataOutputStream)out;
            if(dos.bytearr == null || (dos.bytearr.length < (utflen+2)))
                dos.bytearr = new byte[(utflen*2) + 2];
            bytearr = dos.bytearr;
        } else {
            bytearr = new byte[utflen+2];
        }

        bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
        bytearr[count++] = (byte) ((utflen >>> 0) & 0xFF);

        int i=0;
        for (i=0; i<strlen; i++) {
           c = str.charAt(i);
           if (!((c >= 0x0001) && (c <= 0x007F))) break;
           bytearr[count++] = (byte) c;
        }

        for (;i < strlen; i++){
            c = str.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                bytearr[count++] = (byte) c;

            } else if (c > 0x07FF) {
                bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                bytearr[count++] = (byte) (0x80 | ((c >>  6) & 0x3F));
                bytearr[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            } else {
                bytearr[count++] = (byte) (0xC0 | ((c >>  6) & 0x1F));
                bytearr[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            }
        }
        for(OutputStream stream : streams) {
        	stream.write(bytearr, 0, utflen+2);
		}	
	}
}
