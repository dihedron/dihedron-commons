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

	@Test
	public void testByte() {
		for(byte i = 0; i < Byte.MAX_VALUE; ++i) {
			logger.trace("byte {}: '{}'", i, BitMask.toBitMask(i));
		}
	}
	
	@Test	
	public void testShort() {
		for(short i = 0; i < Byte.MAX_VALUE; ++i) {
			logger.trace("short {}: '{}'", i, BitMask.toBitMask(i));
		}
	}

	@Test
	public void testInt() {
		for(int i = 0; i < Byte.MAX_VALUE; ++i) {
			logger.trace("int {}: '{}'", i, BitMask.toBitMask(i));
		}
	}

	@Test
	public void testLong() {
		for(long i = 0; i < Byte.MAX_VALUE; ++i) {
			logger.trace("long {}: '{}'", i, BitMask.toBitMask(i));
		}
	}
	
}
