/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.streams;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

/**
 * @author Andrea Funto'
 */
public class MirrorOutputStreamTest extends AbstractStreamsTest {

	@Test
	public void testString() throws UnsupportedEncodingException, IOException {
		String string = makeString(1000);
		
		try(InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			MirrorOutputStream mirror = new MirrorOutputStream(output, buffer)) {
			
			Streams.copy(input, mirror);
			String original = new String(output.toByteArray());
			String copy = new String(buffer.toByteArray());
			assertTrue(original.equals(copy));
		}
	}
	
	@Test
	public void testByteArray() throws UnsupportedEncodingException, IOException {
		byte [] array = makeByteArray(100000);
		
		try(InputStream input = new ByteArrayInputStream(array);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			MirrorOutputStream mirror = new MirrorOutputStream(output, buffer)) {
			
			Streams.copy(input, mirror);
			byte [] original = output.toByteArray();
			byte [] copy = buffer.toByteArray();
			for(int i = 0; i < array.length; ++i) {
				assertTrue(array[i] == original[i]);
				assertTrue(array[i] == copy[i]);
			}
		}
	}
	
}
