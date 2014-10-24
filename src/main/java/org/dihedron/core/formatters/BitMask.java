/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.formatters;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public final class BitMask {

	/**
	 * Provides a bitmask (1's and 0's) representation of the given byte.
	 * 
	 * @param bitmask
	 *   the byte whose bitmask is to be printed out.
	 * @return
	 *   a bitmask (1's and 0's) representation of the given byte.
	 */
	public static String toBitMask(byte bitmask) {
		return toBitMask(bitmask, 8);
	}	
	
	/**
	 * Provides a bitmask (1's and 0's) representation of the given short integer.
	 * 
	 * @param bitmask
	 *   the short integer whose bitmask is to be printed out.
	 * @return
	 *   a bitmask (1's and 0's) representation of the given short integer.
	 */
	public static String toBitMask(short bitmask) {
		return toBitMask(bitmask, 16);
	}	
	
	/**
	 * Provides a bitmask (1's and 0's) representation of the given integer.
	 * 
	 * @param bitmask
	 *   the integer whose bitmask is to be printed out.
	 * @return
	 *   a bitmask (1's and 0's) representation of the given integer.
	 */
	public static String toBitMask(int bitmask) {
		return toBitMask(bitmask, 32);
	}	
	
	/**
	 * Provides a bitmask (1's and 0's) representation of the given long integer.
	 * 
	 * @param bitmask
	 *   the long integer whose bitmask is to be printed out.
	 * @return
	 *   a bitmask (1's and 0's) representation of the given long integer.
	 */
	public static String toBitMask(long bitmask) {
		return toBitMask(bitmask, 64);
	}
	
	/**
	 * Parametric version of the method, which dumps any number as bitmask.
	 *  
	 * @param bitmask
	 *   the number to be formatted as a bitmask.
	 * @param length
	 *   the length (in bits) of the type (e.g. int's are 32 bits).
	 * @return
	 *   a bitmask (1's and 0's) representation of the given Number.
	 */
	private static <T extends Number> String toBitMask(T bitmask, int length) {
		StringBuilder buffer = new StringBuilder();
		long bit = 1;
		for(int i = 0; i < length; ++i) {
			if(i != 0 && i % 8 == 0) {
				buffer.append(" ");
			} else if(i != 0 && i % 4 == 0) {
				buffer.append(":");
			}
			if((bit & bitmask.longValue()) == bit) {
				buffer.append("1");
			} else {
				buffer.append("0");
			}
			bit = bit << 1;				
		}
		return buffer.reverse().toString();
	}
	
	/**
	 * Private constructor, to prevent construction.
	 */
	private BitMask() {
	}
}
