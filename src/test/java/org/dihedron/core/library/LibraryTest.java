/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.library;

import static org.junit.Assert.*;

import org.dihedron.core.library.Traits;
import org.dihedron.core.library.Library;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class LibraryTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(LibraryTest.class);

	private static class MyLibrary extends Library {
		
		private MyLibrary() {
			super("commons");
		}
	}
	
	/**
	 * Test method for {@link org.dihedron.core.library.Library#get(org.dihedron.core.library.Traits)}.
	 */
	@Test
	public void testGet() {
		Library library = new MyLibrary();
		for(Traits trait : Traits.values()) {
			logger.trace("{}: '{}'", trait.name(), library.get(trait));
			assertTrue(library.get(trait) != null);
		}
	}
}
