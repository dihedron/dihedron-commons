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
package org.dihedron.core.os;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public abstract class ImageFileParser {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ImageFileParser.class);
	
	/**
	 * Factory method: returns the appropriate file parser, trying to auto-detect it 
	 * based on the current system architecture.
	 * 
	 * @return
	 *   an {@code ImageFileParser}.
	 */
	public static ImageFileParser makeParser() {
		switch(Platform.getCurrent()) {
		case WINDOWS_32:
		case WINDOWS_64:
			// windows platforms use the "portable executable" (PE) file format
			logger.trace("Windows platform detected, returning a PE parser");
			return new PEParser();
		
		case LINUX_32:
		case LINUX_64:
		case MACOSX_32:
		case MACOSX_64:
		case UNIX_32:
		case UNIX_64:
			// unix and variants use the "extensible linker format" (ELF) file format
			logger.trace("UNIX variant detected, returning an ELF parser");
		}
		logger.error("unsupported platform: {}", Platform.getCurrent());
		return null;
	}
	
	/**
	 * Factory method: returns the requested image file parser.
	 * 
	 * @param type
	 *   the type of image file to parse (must be known in advance).
	 * @return
	 *   an {@code ImageFileParser}.
	 */
	public static ImageFileParser makeParser(ImageFile.Format type) {
		switch(type) {
		case ELF:
			return new ELFParser();
		case PE:
			return new PEParser();
		}
		return null;
	}
		
	/**
	 * Reads bytes from the input stream and parses it according to its 
	 * known format and rules; if the file does not comply with the
	 * known format, an exception is thrown.
	 * 
	 * @param path
	 *   the path to the image file to parse.
	 * @throws IOException
	 *   an exception thrown when the image file cannot be opened (e.g.
	 *   due to access restriction or read). 
	 * @throws ImageParseException
	 *   if the file does not comply with the format rules.
	 */
	public ImageFile parse(String path) throws IOException, ImageParseException {
		return parse(new File(path));
	}
	
	/**
	 * Reads bytes from the input stream and parses it according to its 
	 * known format and rules; if the file does not comply with the
	 * known format, an exception is thrown.
	 * 
	 * @param file
	 *   the image file to parse.
	 * @throws IOException
	 *   an exception thrown when the image file cannot be opened (e.g.
	 *   due to access restriction or read). 
	 * @throws ImageParseException
	 *   if the file does not comply with the format rules.
	 */
	public abstract ImageFile parse(File file) throws IOException, ImageParseException;
}
