/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.core.formatters;


/**
 * @author Andrea Funto'
 */
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
