/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.experimental.pipeline;

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
