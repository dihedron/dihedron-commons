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
package org.dihedron.core.os.files;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

/**
 * @author Andrea Funto'
 */
public class FileFinderTest {

	@Test
	public void test() {
		//String [] roots = { "/lib/i386-linux-gnu/", "/lib/x86_64-linux-gnu/", "/usr/local/lib/" };		
		String [] roots = { "src/" };
		List<File> files = FileFinder.findFile("libpcsclite.*", true, roots);
		assertTrue(files.size() == 2);
	}
}
