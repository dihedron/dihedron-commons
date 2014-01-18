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

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class MapFxTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(MapFxTest.class);

	/**
	 * Test method for {@link org.dihedron.commons.functional.FunctionalMap#forEach(org.dihedron.commons.functional.FunctionalMap.M$)}.
	 */
	@Test
	public void testForEach() {
		FunctionalMap<Integer, String> map = new FunctionalMap<Integer, String>(new HashMap<Integer, String>());
		
		map.put(0, "0");
		map.put(1, "1");
		map.put(2, "2");
		map.put(3, "3");
		map.put(4, "4");
		map.put(5, "5");
		
		StringBuilder buffer = map.forEach(new $<Entry<Integer, String>, StringBuilder>() {
			public StringBuilder _(Entry<Integer, String> entry, StringBuilder state) {
				if(state == null) state = new StringBuilder();
				state.append("map['").append(entry.getKey()).append("']='").append(entry.getValue()).append("'\n");
				return state;
			}			
		});
		
		logger.trace("returned:\n{}", buffer);

		int sum = map.forEach(new $<Entry<Integer, String>, Integer>() {
			public Integer _(Entry<Integer, String> entry, Integer sum) {
				if(sum == null) sum = new Integer(0);
				sum = sum + entry.getKey();
				return sum;
			}			
		});		
		logger.trace("returned: {}", sum);
		assertTrue(sum == 15);
	}
}
