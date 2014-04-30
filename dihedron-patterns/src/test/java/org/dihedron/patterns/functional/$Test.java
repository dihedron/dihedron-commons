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
package org.dihedron.patterns.functional;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dihedron.patterns.functional.$;
import org.dihedron.patterns.functional.Fx;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class $Test {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger($Test.class);

	private final static int TEST_INT = 100;
	
	private final static int EVEN_TEST_INT = 2 * TEST_INT;
	
	private class Counters {
		
		private int even = 0;
		private int odd = 0;
		
		public Counters() {
			even = 0;
			odd = 0;
		}
		
		public void addToEven() {
			even = even + 1;
		}
		
		public void addToOdd() {
			odd = odd + 1;
		}
		
		public int getEven() {
			return even;
		}
		
		public int getOdd() {
			return odd;
		}
	}
	
	/**
	 * Test method for {@link org.dihedron.patterns.functional.$#forEach(java.lang.Object, org.dihedron.patterns.functional.Fx)}.
	 */
	@Test
	public void testForEachOnList() {
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 1; i <= EVEN_TEST_INT; ++i) {
			list.add(i);
		}
		int sum = new $<Integer>(list).forEach(new Integer(0), new Fx<Integer, Integer>() {
			@Override
			public Integer apply(Integer state, Integer element) {
				return state + element;
			}			
		});
		
		logger.trace("sum is {}", sum);
		assertTrue(sum == (EVEN_TEST_INT * (EVEN_TEST_INT + 1) / 2));
		
		List<Integer> even = new $<List<Integer>>(list).forEach(new ArrayList<Integer>(), new Fx<List<Integer>, Integer>() {
			@Override
			public List<Integer> apply(List<Integer> state, Integer element) {
				if((element & 1) == 0 ) {
					state.add(element);
				}
				return state;
			}			
		});
		
		logger.trace("list has {} even elements", even.size());
		assertTrue(even.size() == (EVEN_TEST_INT / 2));
		
		for(Integer i : even) {
			assertTrue(i % 2 == 0);
		}
	}
	
	/**
	 * Test method for {@link org.dihedron.patterns.functional.$#forEach(java.lang.Object, org.dihedron.patterns.functional.Fx)}.
	 */
	@Test
	public void testForEachOnSet() {
		Set<Integer> set = new HashSet<Integer>();
		for(int i = 1; i <= EVEN_TEST_INT; ++i) {
			set.add(i);
		}
		int sum = new $<Integer>(set).forEach(new Integer(0), new Fx<Integer, Integer>() {
			@Override
			public Integer apply(Integer state, Integer element) {
				return state + element;
			}			
		});
		
		logger.trace("sum is {}", sum);
		assertTrue(sum == (EVEN_TEST_INT * (EVEN_TEST_INT + 1) / 2));
		
		List<Integer> odd = new $<List<Integer>>(set).forEach(new ArrayList<Integer>(), new Fx<List<Integer>, Integer>() {
			@Override
			public List<Integer> apply(List<Integer> state, Integer element) {
				if((element & 1) != 0 ) {
					state.add(element);
				}
				return state;
			}			
		});
		
		logger.trace("set has {} odd elements", odd.size());
		assertTrue(odd.size() == (EVEN_TEST_INT / 2));
		for(Integer i : odd) {
			assertTrue(i % 2 == 1);
		}
	}

	/**
	 * Test method for {@link org.dihedron.patterns.functional.$#forEach(java.lang.Object, org.dihedron.patterns.functional.Fx)}.
	 */
	@Test
	public void testForEachOnMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for(int i = 1; i <= EVEN_TEST_INT; ++i) {
			if((i & 1) == 0) {
				map.put(i, "even");
			} else {
				map.put(i, "odd");
			}
		}
		int sum = new $<Integer>(map).forEach(new Integer(0), new Fx<Integer, Entry<Integer, String>>() {
			@Override
			public Integer apply(Integer state, Entry<Integer, String> element) {
				return state + element.getKey();
			}			
		});
		
		logger.trace("sum is {}", sum);
		assertTrue(sum == (EVEN_TEST_INT * (EVEN_TEST_INT + 1) / 2));
		
		Counters counters = new $<Counters>(map).forEach(new Counters(), new Fx<Counters, Entry<Integer, String>>() {
			@Override
			public Counters apply(Counters state, Entry<Integer, String> element) {
				if(element.getValue().equals("even")) {
					state.addToEven();
				} else {
					state.addToOdd();
				}
				return state;
			}			
		});
		
		logger.trace("map has {} odd elements and {} even elements", counters.getOdd(), counters.getEven());
		assertTrue(counters.getEven() == (EVEN_TEST_INT / 2));
		assertTrue(counters.getOdd() == (EVEN_TEST_INT / 2));
	}
	
}
