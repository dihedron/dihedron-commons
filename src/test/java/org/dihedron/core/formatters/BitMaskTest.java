/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.formatters;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class BitMaskTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(BitMaskTest.class);
	 
	private final static int LOOPS = 16;

	@Test
	public void testByte() {
		for(byte i = 0; i < LOOPS; ++i) {
			logger.trace("byte {}: '{}'", i, BitMask.toBitMask(i));
		}
	}
	
	@Test	
	public void testShort() {
		for(short i = 0; i < LOOPS; ++i) {
			logger.trace("short {}: '{}'", i, BitMask.toBitMask(i));
		}
	}

	@Test
	public void testInt() {
		for(int i = 0; i < LOOPS; ++i) {
			logger.trace("int {}: '{}'", i, BitMask.toBitMask(i));
		}
	}

	@Test
	public void testLong() {
		for(long i = 0; i < LOOPS; ++i) {
			logger.trace("long {}: '{}'", i, BitMask.toBitMask(i));
		}
	}
}
