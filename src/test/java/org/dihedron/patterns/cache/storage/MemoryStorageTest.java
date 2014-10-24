/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.cache.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.dihedron.core.License;
import org.dihedron.core.regex.Regex;
import org.dihedron.core.streams.Streams;
import org.dihedron.patterns.cache.CacheException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrea Funto'
 */
@License
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
	public void test() throws CacheException, IOException {
		
		MemoryStorage storage = new MemoryStorage();		
		assertTrue(storage.isEmpty());
		String [] list = storage.list();
		assertTrue(list != null);
		assertTrue(list.length == 0);
				
		
		File file = new File("src/test/resources/test.pdf");
		long size = file.length();		
		assertTrue(file.exists());
		
		
		
		Streams.copy(new FileInputStream(file), storage.store("file1.pdf"));
//		storage.store("file1.pdf", new FileInputStream(file));		
		assertFalse(storage.isEmpty());
		assertTrue(storage.size() == 1);
		
		
		
		Streams.copy(new FileInputStream(file), storage.store("file2.pdf"));
		Streams.copy(new FileInputStream(file), storage.store("file3.pdf"));
		Streams.copy(new FileInputStream(file), storage.store("file4.pdf"));
//		storage.store("file2.pdf", new FileInputStream(file)); 
//		storage.store("file3.pdf", new FileInputStream(file));
//		storage.store("file4.pdf", new FileInputStream(file));
		assertTrue(storage.contains("file1.pdf"));
		assertTrue(storage.contains("file2.pdf"));
		assertTrue(storage.contains("file3.pdf"));
		assertTrue(storage.contains("file4.pdf"));
		assertFalse(storage.contains("file5.pdf"));
		assertTrue(storage.size() == 4);
		assertTrue(storage.list(new Regex("^file\\d{1}\\.pdf$")).length == 4);

		
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Streams.copy(storage.retrieve("file1.pdf"), output);
		assertTrue(size == output.toByteArray().length);

		
		
		output = new ByteArrayOutputStream();
		Streams.copy(storage.retrieve("file2.pdf"), output);
		assertTrue(size == output.toByteArray().length);

		
		
		output = new ByteArrayOutputStream();
		Streams.copy(storage.retrieve("file3.pdf"), output);
		assertTrue(size == output.toByteArray().length);
		
		
		
		output = new ByteArrayOutputStream();
		Streams.copy(storage.retrieve("file4.pdf"), output);
		assertTrue(size == output.toByteArray().length);
		
		
		
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
