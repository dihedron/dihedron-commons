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

package org.dihedron.commons.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dihedron.commons.visitor.Visitor.VisitMode;
import org.dihedron.commons.visitor.factories.UnmodifiableNodeFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class VisitorTest {
	
	@SuppressWarnings("unused")
	private class MyInnerBean {
		
		private int number;
		
		protected String p1 = "a nice string";
		
		@Visitable private List<String> list1 = new ArrayList<String>();
		@Visitable private ArrayList<String> list2 = new ArrayList<String>();
		
		@Visitable private Set<String> set = new HashSet<String>();
		
		public MyInnerBean(int number) {
			
			this.number = number;
			
			list1.add("s0");
			list1.add("s1");
			list1.add("s2");
			list1.add("s3");
			list1.add("s4");
			list1.add("s5");

			list2.add("al0");
			list2.add("al1");
			list2.add("al2");
			list2.add("al3");
			list2.add("al4");
			list2.add("al5");
			
			set.add("s0");
			set.add("s1");
			set.add("s2");
			set.add("s3");
			set.add("s4");
			set.add("s5");
		}
	}
	
	@SuppressWarnings("unused")
	private class MyOuterBean {
		
		protected String p1 = "ciao";
		private int p2 = 10;
		private long p3 = 20;
		@Visitable private int[] iarray = { 1, 2, 3, 4, 5 };
		@Visitable private String[] sarray = { "1", "2", "3", "4", "5" };
		@Visitable protected MyInnerBean inner = new MyInnerBean(-1);
		
		@Visitable(true) Map<String, MyInnerBean> beanMap = new HashMap<String, MyInnerBean>();
		
		@Visitable(true) protected List<MyInnerBean> beanList = new ArrayList<MyInnerBean>();
		
		@Visitable(true) private MyInnerBean[] oarray = {  new MyInnerBean(3), new MyInnerBean(4), new MyInnerBean(5) };
		
		public MyOuterBean() {
			beanList.add(new MyInnerBean(0));
			beanList.add(new MyInnerBean(1));
			beanList.add(new MyInnerBean(2));
			
			beanMap.put("key_10", new MyInnerBean(10));
			beanMap.put("key_11", new MyInnerBean(11));
			beanMap.put("key_12", new MyInnerBean(12));
			beanMap.put("key_13", new MyInnerBean(13));
			beanMap.put("key_14", new MyInnerBean(14));
			beanMap.put("key_15", new MyInnerBean(15));
		}
	}
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(VisitorTest.class);

	/**
	 * Test method for {@link org.dihedron.visitor.Visitor#iterator()}.
	 * @throws VisitorException 
	 */
	@Test
	public void testIterator() throws VisitorException {
		
		MyOuterBean bean = new MyOuterBean();
		Visitor visitor = new Visitor(bean, new UnmodifiableNodeFactory());
		for(Node property : visitor) {
			String name = property.getName();
			Object value = property.getValue();
			String type = value != null ? value.getClass().getSimpleName() : "<null>";			
			logger.trace("property: '{}' --> '{}' ({})", name, value, type);
		}
	}
}
