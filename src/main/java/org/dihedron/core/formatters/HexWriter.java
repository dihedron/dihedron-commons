/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.formatters;

import org.dihedron.core.License;

/**
 * Utility for printing a number as an hexadecimal string.
 * 
 * @author Andrea Funto'
 */
@License
public final class HexWriter {
	
	/**
	 * The hexadecimal digits.
	 */
    private static final String DIGITS = "0123456789ABCDEF";
    
	/** 
	 * How many bytes should be written per line in the printout. 
	 */
	private static final int DEFAULT_BYTES_PER_LINE = 16;
	
	/** 
	 * The character that will be used to separate bytes in the printout. 
	 */
	private static final String DEFAULT_BYTES_SEPARATOR = " ";	

    /**
     * Return length many bytes of the passed in byte array as a hex string.
     *
     * @param data the bytes to be converted.
     * @param length the number of bytes in the data block to be converted.
     * @return a hex representation of length bytes of data.
     */
    public static String toHex(byte[] data, int length) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i != length; i++) {
            int v = data[i] & 0xff;

            buffer.append(DIGITS.charAt(v >> 4));
            buffer.append(DIGITS.charAt(v & 0xf));
        }

        return buffer.toString();
    }

    /**
     * Return the passed in byte array as a hex string.
     *
     * @param data the bytes to be converted.
     * @return a hex representation of data.
     */
    public static String toHex(byte[] data) {
        return toHex(data, data.length);
    }
        
	/**
	 * Formats a byte[] as an hexadecimal String, interleaving bytes with a
	 * separator string; DEFAULT_BYTES_PER_LINE bytes will be written per
	 * line, separated by DEFAULT_BYTES_SEPARATOR.
	 * 
	 * @param data
	 *   the byte[] to format.
	 * @param wrapAfter
	 *   the number of bytes to be written per line.
	 * @return 
	 *   the formatted string.
	 */
    public static String toMultiLineHex(byte[] data) {
    	return toMultiLineHex(data, DEFAULT_BYTES_SEPARATOR, DEFAULT_BYTES_PER_LINE);
    }
    
	/**
	 * Formats a byte[] as an hexadecimal String, interleaving bytes with a
	 * separator string; DEFAULT_BYTES_PER_LINE bytes will be written per
	 * line.
	 * 
	 * @param data
	 *   the byte[] to format.
	 * @param byteSeparator
	 *   the string to be used to separate bytes.
	 * @return 
	 *   the formatted string.
	 */
    public static String toMultiLineHex(byte[] data, String byteSeparator) {
    	return toMultiLineHex(data, byteSeparator, DEFAULT_BYTES_PER_LINE);
    }

    /**
	 * Formats a byte[] as an hexadecimal String, interleaving bytes with a
	 * separator string.
	 * 
	 * @param data
	 *   the byte[] to format.
	 * @param byteSeparator
	 *   the string to be used to separate bytes.
	 * @param wrapAfter
	 *   the number of bytes to be written per line.
	 * @return 
	 *   the formatted string.
	 */
	public static String toMultiLineHex(byte[] data, String byteSeparator, int wrapAfter) {
		int n, x;
		String w = null;
		String s = null;

		String separator = null;

		for (n = 0; n < data.length; n++) {
			x = (int) (0x000000FF & data[n]);
			w = Integer.toHexString(x).toUpperCase();
			
			if (w.length() == 1) {
				w = "0" + w;
			}
			
			if ((n % wrapAfter) == (wrapAfter - 1)){
				separator = "\n";
			} else {
				separator = byteSeparator;
			}
			s = (s != null ? s : "") + w + ((n + 1 == data.length) ? "" : separator);
		}
		return s;
	} 
	
	/**
	 * Private constructor to prevent construction.
	 */
	private HexWriter() {		
	}
}

