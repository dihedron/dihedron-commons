/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.cache.handlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.dihedron.core.License;
import org.dihedron.patterns.cache.CacheException;
import org.dihedron.patterns.cache.CacheMissHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copies data from memory into a virtual streamed resource. It is used to copy
 * data from memory into the cache.
 * 
 * @author Andrea Funto'
 */
@License
public class InMemoryByteArrayRetriever implements CacheMissHandler {

	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(InMemoryByteArrayRetriever.class);
	
	/** 
	 * The input stream mapping the buffered data. 
	 */
	private ByteArrayInputStream stream;
	
	/**
	 * Constructor.
	 * 
	 * @param data
	 *   the data to be stored in the cache.
	 */
	public InMemoryByteArrayRetriever(byte [] data) {
		logger.debug("storing {} bytes as stream", (data != null ? data.length : 0));
		// store into internal buffer to avoid security issues
		byte [] buffer = new byte[data.length]; 
		System.arraycopy(data, 0, buffer, 0, buffer.length);
		stream = new ByteArrayInputStream(buffer);
	}

	/**
	 * Returns the stream, or <code>null</code> if it cannot be found.
	 * 
	 * @return 
	 *   the stream, or <code>null</code> if it cannot be found.
	 */
	public InputStream getAsStream() throws CacheException {
		logger.debug("returning the data as an InputStream");
		return stream;
	}
}
