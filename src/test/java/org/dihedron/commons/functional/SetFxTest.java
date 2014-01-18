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
package org.dihedron.commons.functional;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class SetFxTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(SetFxTest.class);

	private static final int MAX_TEST_INTEGER = 1000;
	
	@Test
	public void test() {
		FunctionalSet<Integer> set = new FunctionalSet<Integer>(new HashSet<Integer>());
		for(int i = 1; i <= MAX_TEST_INTEGER; ++i) {
			set.add(i);
		}
		
		int sum = set.forEach(new $<Integer, Integer>() {
			public Integer _(Integer element, Integer sum) {
				if(sum == null) sum = new Integer(0);
				sum = sum + element;
				return sum;
			}			
		});		
		int result = ((MAX_TEST_INTEGER + 1) * MAX_TEST_INTEGER) / 2;
		
		logger.trace("returned: {} (expected {})", sum, result);
		assertTrue(sum == result);

	}
}
