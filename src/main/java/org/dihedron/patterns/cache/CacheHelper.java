/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.dihedron.core.License;
import org.dihedron.core.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public final class CacheHelper {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(CacheHelper.class);
	
	/**
	 * Retrieves the given resource from the cache and translate it to a byte
	 * array; if missing tries to retrieve it using the (optional) provided set 
	 * of handlers.
	 * 
	 * @param cache
	 *   the cache that stores the resource. 
	 * @param resource
	 *   the name of the resource to be retrieved.
	 * @param handlers
	 *   the (optional) set of handlers that will attempt to retrieve the resource
	 *   if missing from the cache. 
	 * @return
	 *   the resource as an array of bytes, or {@code null} if it cannot be 
	 *   retrieved.
	 * @throws CacheException
	 */
	public static byte[] getIntoByteArray(Cache cache, String resource, CacheMissHandler ... handlers) throws CacheException {
		if(cache == null) {
			logger.error("cache reference must not be null");
			throw new CacheException("invalid cache");
		}
		InputStream input = null;
		ByteArrayOutputStream output = null; 
		try {
			input = cache.get(resource, handlers);
			if(input != null) {
				output = new ByteArrayOutputStream();
				long copied = Streams.copy(input, output);
				logger.trace("copied {} bytes from cache", copied);
				return output.toByteArray();
			}			
		} catch (IOException e) {
			logger.error("error copying data from cache to byte array", e);
			throw new CacheException("error copying data from cache to byte array", e);
		} finally {
			Streams.safelyClose(input);
			Streams.safelyClose(output);
		}
		return null;
	}
	
	/**
	 * Stores the given array of bytes under the given resource name.
	 * 
	 * @param cache
	 *   the cache that stores the resource.
	 * @param resource
	 *   the name of the resource to be stored.
	 * @param data
	 *   the array of bytes containing the resource. 
	 * @return
	 *   the number of bytes copied to the cache.
	 * @throws CacheException
	 */
	public static long putFromByteArray(Cache cache, String resource, byte [] data) throws CacheException {
		if(cache == null) {
			logger.error("cache reference must not be null");
			throw new CacheException("invalid cache");
		}
		ByteArrayInputStream input = null;
		OutputStream output = null;
		try { 
			output = cache.put(resource);
			if(output != null) {
				input = new ByteArrayInputStream(data);
				long copied = Streams.copy(input, output);
				logger.trace("copied {} bytes into cache", copied);
				return copied;
			}			
		} catch (IOException e) {
			logger.error("error copying data from byte array to cache", e);
			throw new CacheException("error copying data from byte array to cache", e);
		} finally {
			Streams.safelyClose(input);
			Streams.safelyClose(output);
		}
		return -1;
	}
	
	/**
	 * Private constructor to prevent instantiation.
	 */
	private CacheHelper() {		
	}
}
