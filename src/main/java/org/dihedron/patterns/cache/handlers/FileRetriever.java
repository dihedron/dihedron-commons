/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.cache.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.dihedron.core.License;
import org.dihedron.patterns.cache.CacheException;
import org.dihedron.patterns.cache.CacheMissHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class FileRetriever implements CacheMissHandler {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileRetriever.class);
	
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
