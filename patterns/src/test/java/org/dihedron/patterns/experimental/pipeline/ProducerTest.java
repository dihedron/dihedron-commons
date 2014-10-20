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
package org.dihedron.patterns.experimental.pipeline;

import static org.junit.Assert.fail;

import org.dihedron.patterns.experimental.pipeline.Filter;
import org.dihedron.patterns.experimental.pipeline.Link;
import org.dihedron.patterns.experimental.pipeline.Producer;
import org.dihedron.patterns.experimental.pipeline.impl.ArraySource;
import org.dihedron.patterns.experimental.pipeline.impl.Log;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class ProducerTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ProducerTest.class);

	/**
	 * Test method for {@link org.dihedron.patterns.experimental.pipeline.Producer#produce()}.
	 */
	@Test
	public void testProduce() {
		
		String[] array = {"hallo", "world"};
		
		Producer<String> producer = new ArraySource<String>(array);
		Filter<String> consumer = new Log<String>();
		
		new Link<String, String>().from(producer).to(consumer);
		
		
//		fail("Not yet implemented");
		
	}
}
