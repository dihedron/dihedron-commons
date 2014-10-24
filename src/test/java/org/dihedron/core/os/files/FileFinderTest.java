/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.os.files;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.dihedron.core.License;
import org.junit.Test;

/**
 * @author Andrea Funto'
 */
@License
public class FileFinderTest {

	@Test
	public void test() {
		//String [] roots = { "/lib/i386-linux-gnu/", "/lib/x86_64-linux-gnu/", "/usr/local/lib/" };		
		String [] roots = { "src/" };
		List<File> files = FileFinder.findFile("libpcsclite.*", true, roots);
		assertTrue(files.size() == 2);
	}
}
