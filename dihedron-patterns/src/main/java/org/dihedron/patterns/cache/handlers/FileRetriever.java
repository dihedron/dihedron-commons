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

package org.dihedron.patterns.cache.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.dihedron.patterns.cache.CacheException;
import org.dihedron.patterns.cache.CacheMissHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class FileRetriever implements CacheMissHandler {
	
	/** 
	 * The logger. 
	 */
	private static Logger logger = LoggerFactory.getLogger(FileRetriever.class);
	
	/** 
	 * The file. 
	 */
	private File file;
	
	/**
	 * Constructor.
	 * 
	 * @param file
	 *   the path to the file to be retrieved.
	 */
	public FileRetriever(File file) {
		this.file = file;
		logger.debug("path to the file: '{}'", file.getAbsolutePath());
	}
	
	/**
	 * Constructor.
	 * 
	 * @param path
	 *   the path to the file to be retrieved.
	 */
	public FileRetriever(String path) {	
		this.file = new File(path);
		logger.debug("path to the file: '{}'", file.getAbsolutePath());
	}

	/**
	 * Returns the given file as a stream.
	 */
	public InputStream getAsStream() throws CacheException {
		try {
			if(file.exists() && file.isFile()) {
				logger.debug("retrieving file as a stream");
				return new FileInputStream(file);			
			}
		} catch(IOException e) {
			logger.error("file '{}' does not exist or is not a file", file.getAbsolutePath());
			throw new CacheException("File " + file.getAbsolutePath() + " does not exist or is not a regular file", e);
		}		
		return null;
	}
}
