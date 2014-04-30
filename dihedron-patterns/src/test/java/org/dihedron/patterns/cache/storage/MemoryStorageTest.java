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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.dihedron.core.regex.Regex;
import org.dihedron.patterns.cache.CacheException;
import org.dihedron.patterns.cache.storage.MemoryStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrea Funto'
 */
public class MemoryStorageTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws FileNotFoundException, CacheException {
		
		MemoryStorage storage = new MemoryStorage();
		
		assertTrue(storage.isEmpty());
		
		File file = new File("src/test/resources/test.pdf");
		long size = file.length();
		
		assertTrue(file.exists());
		
		storage.store("file1.pdf", new FileInputStream(file));
		
		assertFalse(storage.isEmpty());
		
		storage.store("file2.pdf", new FileInputStream(file)); 
		storage.store("file3.pdf", new FileInputStream(file));
		storage.store("file4.pdf", new FileInputStream(file));
		assertTrue(storage.contains("file1.pdf"));
		assertTrue(storage.contains("file2.pdf"));
		assertTrue(storage.contains("file3.pdf"));
		assertTrue(storage.contains("file4.pdf"));
		assertFalse(storage.contains("file5.pdf"));
		
		assertTrue(storage.list(new Regex("^file\\d{1}\\.pdf$")).length == 4);
		
		byte [] data = storage.retrieveAsByteArray("file1.pdf");
		assertTrue(size == data.length);
		data = storage.retrieveAsByteArray("file2.pdf");
		assertTrue(size == data.length);
		data = storage.retrieveAsByteArray("file3.pdf");
		assertTrue(size == data.length);
		data = storage.retrieveAsByteArray("file4.pdf");
		assertTrue(size == data.length);

		storage.delete(new Regex(".*1\\.pdf", true));
		
		assertTrue(storage.list(new Regex("^file\\d{1}\\.pdf$")).length == 3);
		
		assertFalse(storage.contains("file1.pdf"));
		assertTrue(storage.contains("file2.pdf"));
		assertTrue(storage.contains("file3.pdf"));
		assertTrue(storage.contains("file4.pdf"));
		
		storage.delete(new Regex(".*\\.pdf", true));

		assertTrue(storage.isEmpty());
	}
}
