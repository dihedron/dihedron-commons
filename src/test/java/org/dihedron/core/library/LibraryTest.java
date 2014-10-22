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
