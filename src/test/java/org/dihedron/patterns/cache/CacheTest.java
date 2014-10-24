/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.cache;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.dihedron.core.License;
import org.dihedron.patterns.cache.handlers.FileRetriever;
import org.dihedron.patterns.cache.storage.MemoryStorage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class CacheTest {

	private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);
	
	@Test
	public void test() throws Exception {
		
		
		Cache cache = new Cache(new MemoryStorage());
		assertTrue(cache.isEmpty());
		
		
		CacheMissHandler handler = new FileRetriever(new File("src/test/resources/test.pdf"));
		cache.get("google_1", handler);
		assertTrue(cache.size() == 1);
		
		
		
		cache.copyAs("google_1", "google_2");
		cache.copyAs("google_1", "google_3");
		cache.copyAs("google_1", "google_4");
		assertTrue(cache.size() == 4);
		
		
		for(String resource : cache) {
			logger.info("resource: " + resource);
		}
	}
}
