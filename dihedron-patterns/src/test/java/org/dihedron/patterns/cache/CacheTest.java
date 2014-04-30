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
package org.dihedron.patterns.cache;

import java.io.File;

import org.dihedron.patterns.cache.Cache;
import org.dihedron.patterns.cache.CacheMissHandler;
import org.dihedron.patterns.cache.handlers.FileRetriever;
import org.dihedron.patterns.cache.storage.MemoryStorage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class CacheTest {

	private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);
	
	@Test
	public void test() throws Exception {
		
		
		Cache cache = new Cache(new MemoryStorage());
		
		CacheMissHandler handler = new FileRetriever(new File("src/test/resources/test.pdf"));
		cache.getAsByteArray("google_1", handler);
		cache.copyAs("google_1", "google_2");
		cache.copyAs("google_1", "google_3");
		cache.copyAs("google_1", "google_4");
		
		for(String resource : cache) {
			logger.info("resource: " + resource);
		}
	}
}
