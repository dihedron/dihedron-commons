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

package org.dihedron.commons.cache.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.dihedron.commons.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class JavaLibPathResourceRetriever implements CacheMissHandler {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(JavaLibPathResourceRetriever.class);
	
	/**
	 * The name of the file to be located on the classpath.
	 */
	private String filename;
	
	/**
	 * Constructor.
	 * 
	 * @param filename
	 *   the name of the file to be located.
	 */
	public JavaLibPathResourceRetriever(String filename) {
		this.filename = filename;
	}

	/**
	 * Returns the file as a stream.
	 */
	public InputStream getAsStream() throws CacheException {
		File file = null;
		try {
			File directory = new File(System.getProperty("java.library.path"));
			file = new File(directory, filename);
			if(file.exists() && file.isFile()) {
				return new FileInputStream(file);
			}
		} catch(IOException e) {
			logger.error("file '{}' does not exist on classpath or is not a file", filename);
			throw new CacheException("File " + filename + " does not exist on classpath or is not a regular file", e);			
		}
		return null;
	}
}
