/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.streams;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class MirrorInputStreamTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(MirrorInputStreamTest.class);

	/**
	 * @throws IOException 
	 */
	@Test
	public void testString() throws IOException {
		String string = "this is a test string";
		try(InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			MirrorInputStream mirror = new MirrorInputStream(input, output)) {
			
			Streams.copy(mirror, new NullOutputStream());
			String copy = new String(output.toByteArray());
			logger.trace("copy: '{}'", copy);
			assertTrue(copy.equals(string));
		}
	}
	
	/**
	 * @throws IOException 
	 */
	@Test
	public void testBytes() throws IOException {
		byte[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		
		try(InputStream input = new ByteArrayInputStream(array);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			MirrorInputStream mirror = new MirrorInputStream(input, output)) {
			Streams.copy(mirror, new NullOutputStream());
			byte [] copy = output.toByteArray();
			logger.trace("copy: '{}'", copy);
			for(int i = 0; i < array.length; ++i) {
				assertTrue(copy[i] == i);
			}
		}
	}
	
}
