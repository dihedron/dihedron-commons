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

package org.dihedron.commons.cache.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dihedron.commons.cache.CacheException;
import org.dihedron.commons.cache.Storage;
import org.dihedron.commons.regex.Regex;
import org.dihedron.commons.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A class proving in-memory storage.
 * 
 * @author Andrea Funto'
 */
public class MemoryStorage implements Storage {
	
	/** 
	 * The logger. 
	 */
	private static Logger logger = LoggerFactory.getLogger(MemoryStorage.class);
	
	/** 
	 * A map containing all cache resources. 
	 */
	private Map<String, byte[]> contents = Collections.synchronizedMap(new HashMap<String, byte[]>());
	
	/**
	 * Constructor.
	 */
	public MemoryStorage() {
		logger.debug("creating memory storage");
	}
	
	/**
	 * @see org.dihedron.commons.cache.Storage#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return contents.isEmpty();
	}
	
	/**
	 * @see org.dihedron.commons.cache.Storage#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String resource) {
		boolean result = contents.containsKey(resource);
		logger.debug("storage {} resource '{}'", (result ? "contains" : "doesn't contain"), resource);
		return result;
	}

	/**
	 * @see org.dihedron.commons.cache.Storage#list(org.dihedron.commons.regex.Regex)
	 */
	@Override
	public String[] list(Regex regex) {
		if(regex != null) {
			List<String> matched = new ArrayList<String>();
			Set<String> resources = contents.keySet();
			for (String resource : resources) {
				if(regex.matches(resource)) {
					logger.trace("regular expression matches input string '{}'", resource);
					matched.add(resource);
				}
			}
			if(matched.size() > 0){
				String [] result = new String[matched.size()];
				return matched.toArray(result);			
			}
		}
		return new String[0];
	}	

	/**
	 * @see org.dihedron.commons.cache.Storage#store(java.lang.String, java.io.InputStream)
	 */
	@Override
	public void store(String resource, InputStream stream) throws CacheException {
		try {
			if(resource != null && stream != null) {			
				logger.debug("storing resource {} available bytes: {}", resource, stream.available());
				ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
				Streams.copy(stream, baos);
				store(resource, baos.toByteArray());
				baos.close();
			}
		} catch(IOException e) {
			logger.error("error storing data into memory");
			throw new CacheException("Error storing data into memory", e);
		}
	}

	/**
	 * @see org.dihedron.commons.cache.Storage#store(java.lang.String, byte[])
	 */
	@Override
	public void store(String resource, byte[] data) throws CacheException {
		if(resource != null && data != null){
			contents.put(resource, data);
		}
	}
	
	/**
	 * @see org.dihedron.commons.cache.Storage#retrieveAsStream(java.lang.String)
	 */
	@Override
	public InputStream retrieveAsStream(String resource) {
		return byteArrayToStream(contents.get(resource));
	}	
	
	/**
	 * @see org.dihedron.commons.cache.Storage#retrieveAsByteArray(java.lang.String)
	 */
	@Override
	public byte[] retrieveAsByteArray(String resource) {
		return contents.get(resource);
	}
	
	/**
	 * @see org.dihedron.commons.cache.Storage#delete(org.dihedron.commons.regex.Regex)
	 */
	@Override
	public void delete(Regex regex) {
		Set<String> resources = new HashSet<String>(contents.keySet());
		for (String resource : resources) {

			if(regex.matches(resource)){
				logger.debug("removing resource '{}'", resource);
				contents.remove(resource);
			}
		}
	}
		
	/**
	 * @see org.dihedron.commons.cache.Storage#delete(java.lang.String, boolean)
	 */
	@Override
	public void delete(String resource, boolean caseSensitive) {
		Set<String> resources = new HashSet<String>(contents.keySet());
		for (String string : resources) {
			if(caseSensitive && string.equals(resource)) {
				logger.debug("removing resource '{}'", resource);
				contents.remove(resource);
			} else if(!caseSensitive && string.equalsIgnoreCase(resource)){
				logger.debug("removing resource '{}'", resource);
				contents.remove(resource);				
			} else {
				logger.trace("keeping resource '{}'", resource);
			}
		}
	}
	
	/**
	 * @see org.dihedron.commons.cache.Storage#clear()
	 */
	@Override
	public void clear() {
		logger.debug("clearing storage");
		contents.clear();
	}	

	/**
	 * Converts a byte array into a stream.
	 *  
	 * @param data
	 *   the byte array.
	 * @return
	 *   the InputStream.
	 */
	private static InputStream byteArrayToStream(byte[] data) {
		if(data != null) {
			return new ByteArrayInputStream(data);
		}
		return null;
	}	
}
