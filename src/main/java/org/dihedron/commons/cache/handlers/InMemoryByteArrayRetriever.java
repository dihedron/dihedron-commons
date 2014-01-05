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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.dihedron.commons.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copies data from memory into a virtual streamed resource. It is used to copy
 * data from memory into the cache.
 * 
 * @author Andrea Funto'
 */
public class InMemoryByteArrayRetriever implements CacheMissHandler {

	/** 
	 * The logger. 
	 */
	private static Logger logger = LoggerFactory.getLogger(InMemoryByteArrayRetriever.class);
	
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
