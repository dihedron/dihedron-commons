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
import org.dihedron.commons.regex.Regex;
import org.dihedron.commons.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Andrea Funto'
 *
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
	 * @see org.dihedron.commons.cache.storage.Storage#isEmpty()
	 */

	public boolean isEmpty() {
		return contents.isEmpty();
	}
	
	/**
	 * @see org.dihedron.commons.cache.storage.Storage#contains(java.lang.String)
	 */

	public boolean contains(String resource) {
		boolean result = contents.containsKey(resource);
		logger.debug("storage " + (result ? "contains " : "doesn't contain ") + resource);
		return result;
	}

	public String[] list(Regex regex) {
		if(regex == null) {
			regex = new Regex();
		}
		List<String> matched = new ArrayList<String>();
		Set<String> resources = contents.keySet();
		for (String resource : resources) {
			if(regex.matches(resource)) {
				matched.add(resource);
			}
		}
		if(matched.size() > 0){
			String []result = new String[matched.size()];
			return matched.toArray(result);			
		}
		return null;
	}	

	/**
	 * @see org.dihedron.commons.cache.storage.Storage#store(java.lang.String, java.io.InputStream)
	 */

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
	 * @see org.dihedron.commons.cache.storage.Storage#store(java.lang.String, byte[])
	 */
	public void store(String resource, byte[] data) throws CacheException {
		if(resource != null && data != null){
			contents.put(resource, data);
		}
	}
	
	/**
	 * @see org.dihedron.commons.cache.storage.Storage#retrieveAsStream(java.lang.String)
	 */
	public InputStream retrieveAsStream(String resource) {
		return byteArrayToStream(contents.get(resource));
	}	
	
	/**
	 * @see org.dihedron.commons.cache.storage.Storage#retrieveAsByteArray(java.lang.String)
	 */
	public byte[] retrieveAsByteArray(String resource) {
		return contents.get(resource);
	}
	
	/**
	 * @see org.dihedron.commons.cache.storage.Storage#delete(org.dihedron.commons.regex.Regex)
	 */
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
	 * @see org.dihedron.commons.cache.storage.Storage#delete(java.lang.String, boolean)
	 */
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
	 * @see org.dihedron.commons.cache.storage.Storage#clear()
	 */
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
	
	/*
	public static void main(String [] args) throws Exception {
		logger = Logger.initialiseWithDefaults(Level.DEBUG, MemoryStorage.class);
		
		MemoryStorage storage = new MemoryStorage();
		File file = new File("d:\\temp2\\pippo.pdf");
		long size = file.length();
		storage.store("file1.pdf", new FileInputStream(file)); 
		storage.store("file2.pdf", new FileInputStream(file)); 
		storage.store("file3.pdf", new FileInputStream(file));
		storage.store("file4.pdf", new FileInputStream(file));
		logger.debug("storage contains file1.pdf? " + storage.contains("file1.pdf") + " [expected: true]");
		logger.debug("storage contains file2.pdf? " + storage.contains("file1.pdf") + " [expected: true]");
		logger.debug("storage contains file3.pdf? " + storage.contains("file1.pdf") + " [expected: true]");
		logger.debug("storage contains file4.pdf? " + storage.contains("file1.pdf") + " [expected: true]");
		logger.debug("storage contains file5.pdf? " + storage.contains("file1.pdf") + " [expected: false]");
		
		byte [] data = storage.retrieveAsByteArray("file1.pdf");
		logger.debug("size of file1.pdf on disk and in storage match? " + (size == data.length) + " [expected: true]");
		data = storage.retrieveAsByteArray("file2.pdf");
		logger.debug("size of file2.pdf on disk and in storage match? " + (size == data.length) + " [expected: true]");
		data = storage.retrieveAsByteArray("file3.pdf");
		logger.debug("size of file3.pdf on disk and in storage match? " + (size == data.length) + " [expected: true]");
		data = storage.retrieveAsByteArray("file4.pdf");
		logger.debug("size of file4.pdf on disk and in storage match? " + (size == data.length) + " [expected: true]");

		storage.delete(new Regex(".*1\\.pdf", true));
		storage.contains("file1.pdf");
	
//		storage.delete("file\\d\\.pdf", true);
//		logger.debug("storage is empty? " + storage.isEmpty() + " [expected: true]");
//		storage.store("pluto.pdf", new FileInputStream(new File("d:\\temp2\\pippo.pdf")));
//		logger.debug("storage is empty? " + storage.isEmpty() + " [expected: false]");
//		storage.clear();
//		logger.debug("storage is empty? " + storage.isEmpty() + " [expected: true]");
//		storage.store("pluto2.pdf", new FileInputStream(new File("d:\\temp2\\pippo.pdf")));
//		logger.debug("storage is empty? " + storage.isEmpty() + " [expected: false]");
//		storage.delete("pi.*\\.pdf", true);
//		logger.debug("storage is empty? " + storage.isEmpty() + " [expected: false]");
//		storage.delete("p.*\\.pdf", true);
//		logger.debug("storage is empty? " + storage.isEmpty() + " [expected: true]");
//		assert(storage.isEmpty());	
		
	}

*/


}
