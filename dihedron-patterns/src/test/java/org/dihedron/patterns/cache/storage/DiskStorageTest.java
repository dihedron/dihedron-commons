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
package org.dihedron.patterns.cache.storage;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import org.dihedron.core.regex.Regex;
import org.dihedron.patterns.cache.storage.DiskStorage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiskStorageTest {
	
	private static final Logger logger = LoggerFactory.getLogger(DiskStorageTest.class);

	
	private void cleanup(File directory) {
		if(directory != null && directory.exists() && directory.isDirectory()){
			for (File file : directory.listFiles()) {
				file.delete();
			} 
			directory.delete();
		}		
	}
	
	@Test
	public void test() throws Exception {		
		
		File directory = null;
		try {
			// clean up
			directory = new File("testDir");
			cleanup(directory);
			
			DiskStorage storage = new DiskStorage(directory);
			storage.store("file1.pdf", new FileInputStream(new File("src/test/resources/test.pdf")));
			storage.store("file2.pdf", new FileInputStream(new File("src/test/resources/test.pdf")));
			storage.store("file3.pdf", new FileInputStream(new File("src/test/resources/test.pdf")));
			storage.store("file4.pdf", new FileInputStream(new File("src/test/resources/test.pdf")));
			
			for (String string : storage.list(new Regex(".*2\\.pdf"))) {
				logger.debug("resource in storage: '{}'", string);
			}
			
			storage.delete(new Regex("file\\d\\.pdf", true));
					
			logger.debug("storage is empty? {} [expected: true]", storage.isEmpty());
					
			storage.store("pluto.pdf", new FileInputStream(new File("src/test/resources/test.pdf")));
			logger.debug("storage is empty? {} [expected: false]", storage.isEmpty());
			storage.clear();
			logger.debug("storage is empty? {} [expected: true]", storage.isEmpty());
			storage.store("pluto2.pdf", new FileInputStream(new File("src/test/resources/test.pdf")));
			logger.debug("storage is empty? {} [expected: false]", storage.isEmpty());
			storage.delete(new Regex("pi.*\\.pdf", true));
			logger.debug("storage is empty? {} [expected: false]", storage.isEmpty());
			storage.delete(new Regex("p.*\\.pdf", true));
			logger.debug("storage is empty? {} [expected: true]", storage.isEmpty());
			assertTrue(storage.isEmpty());
		} finally {
			cleanup(directory);
		}
	}
}
