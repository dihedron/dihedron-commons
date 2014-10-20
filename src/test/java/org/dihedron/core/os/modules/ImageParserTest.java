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
package org.dihedron.core.os.modules;

import java.io.File;
import java.io.IOException;

import org.dihedron.core.os.modules.ImageFile;
import org.dihedron.core.os.modules.ImageFileParser;
import org.dihedron.core.os.modules.ImageParseException;
import org.dihedron.core.os.modules.ImageFile.Format;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class ImageParserTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ImageParserTest.class);

	/**
	 * Test method for {@link org.dihedron.core.os.SharedObject#isLibrary()}.
	 * 
	 * @throws IOException 
	 * @throws ImageParseException 
	 */
	@Test
	@Ignore
	public void parseELF() throws IOException, ImageParseException {
		String [] linux = {
				"src/test/resources/x86/libpcsclite.so.1", 
				"src/test/resources/x86-64/libpcsclite.so.1" 
		};
		for(String path : linux) {
			logger.trace("testing '{}' as {}", path, new File(path).getAbsolutePath());
			ImageFileParser parser = ImageFileParser.makeParser(Format.ELF);
			ImageFile image = parser.parse(path);
			logger.trace("module: \n{}", image.toJSON());
		}
	}
	
	@Test
	public void parsePE() throws IOException, ImageParseException {
		String [] windows = {
				"src/test/resources/x86/iexplore.exe", 
				"src/test/resources/x86/iedvtool.dll",
				"src/test/resources/x86-64/iexplore.exe" ,
				"src/test/resources/x86-64/iedvtool.dll"
		};
		for(String path : windows) {
			logger.trace("testing '{}' as {}", path, new File(path).getAbsolutePath());
			ImageFileParser parser = ImageFileParser.makeParser(Format.PE);
			ImageFile image = parser.parse(path);
			logger.trace("module: \n{}", image.toJSON());
		}
	}
}
