/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
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
