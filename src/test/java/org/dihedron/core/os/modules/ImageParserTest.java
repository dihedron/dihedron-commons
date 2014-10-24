/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
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
