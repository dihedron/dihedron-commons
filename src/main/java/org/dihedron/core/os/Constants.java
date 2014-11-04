/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public final class Constants {
	
	/**
	 * The size of a byte.
	 */
	public static final int SIZE_OF_BYTE = 1;
	
	/**
	 * The size of a word (2 bytes).
	 */
	public static final int SIZE_OF_WORD = 2;
	
	/**
	 * The size of a double word (4 bytes)
	 */
	public static final int SIZE_OF_DWORD = 4;
	
	/**
	 * The size of a quad-word (8 bytes).
	 */
	public static final int SIZE_OF_QWORD = 8;
	
	/**
	 * The number of bytes in a kilobyte.
	 */
	public static final long KILOBYTE = 1024;
	
	/**
	 * The number of bytes in a megabyte.
	 */
	public static final long MEGABYTE = 1024 * KILOBYTE;
	
	/**
	 * The number of bytes in a gigabyte.
	 */
	public static final long GIGABYTE = 1024 * MEGABYTE;
	
	/**
	 * The number of bytes in a terabyte.
	 */
	public static final long TERABYTE = 1024 * GIGABYTE;
	
	/**
	 * The number of bytes in a petabyte.
	 */
	public static final long PETABYTE = 1024 * TERABYTE;
	
	/**
	 * The number of bytes in a hexabyte.
	 */
	public static final long HEXABYTE = 1024 * PETABYTE;		

	/**
	 * Private constructor, prevent construction of library.
	 */
	private Constants() {
	}
}
