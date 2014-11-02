/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.cache.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dihedron.core.License;
import org.dihedron.core.regex.Regex;
import org.dihedron.core.streams.Streams;
import org.dihedron.core.strings.Strings;
import org.dihedron.patterns.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A class proving in-memory storage.
 * 
 * @author Andrea Funto'
 */
@License
public class MemoryStorage extends AbstractStorage {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(MemoryStorage.class);
	
	/** 
	 * A map containing all cache resources. 
	 */
	private Map<String, ByteArrayOutputStream> contents = Collections.synchronizedMap(new HashMap<String, ByteArrayOutputStream>());
	
	/**
	 * Constructor.
	 */
	public MemoryStorage() {
		logger.debug("creating memory storage");
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return contents.isEmpty();
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String resource) {
		boolean result = contents.containsKey(resource);
		logger.debug("storage {} resource '{}'", (result ? "contains" : "doesn't contain"), resource);
		return result;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#list(org.dihedron.core.regex.Regex)
	 */
	@Override
	public String[] list(Regex regex) {
		List<String> matched = new ArrayList<String>();
		if(regex != null) {
			for (String resource : contents.keySet()) {
				if(regex.matches(resource)) {
					logger.trace("regular expression matches input string '{}'", resource);
					matched.add(resource);
				}
			}
		} else {
			matched.addAll(contents.keySet());
		}
		
//		if(matched.size() > 0){
//			String [] result = new String[matched.size()];
//			return matched.toArray(result);			
//		} else {
//			return new String[0];
//		}
		return matched.toArray(new String[0]);
	}	

	/**
	 * @see org.dihedron.patterns.cache.Storage#store(java.lang.String, java.io.InputStream)
	 */
	@Override
	public OutputStream store(String resource) throws CacheException {
		if(Strings.isValid(resource)) {
			Streams.safelyClose(contents.get(resource));
			logger.debug("storing resource '{}'", resource);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			contents.put(resource, stream);
			return stream; 
		}
		return null;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#retrieveAsStream(java.lang.String)
	 */
	@Override
	public InputStream retrieve(String resource) {
		if(Strings.isValid(resource)) {
			ByteArrayOutputStream stream = contents.get(resource);
			if(stream != null) {
				return new ByteArrayInputStream(stream.toByteArray());
			}
		}
		return null;
	}	
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(org.dihedron.core.regex.Regex)
	 */
	@Override
	public void delete(Regex regex) {
		Set<String> resources = new HashSet<String>(contents.keySet());
		for (String resource : resources) {
			if(regex.matches(resource)){
				logger.debug("removing resource '{}'", resource);
				Streams.safelyClose(contents.get(resource));
				contents.remove(resource);
			}
		}
	}
		
	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(java.lang.String, boolean)
	 */
	@Override
	public void delete(String resource, boolean caseSensitive) {
		Set<String> resources = new HashSet<String>(contents.keySet());
		for (String string : resources) {
			if(caseSensitive && string.equals(resource)) {
				logger.debug("removing resource '{}'", resource);
				Streams.safelyClose(contents.get(resource));
				contents.remove(resource);
			} else if(!caseSensitive && string.equalsIgnoreCase(resource)){
				logger.debug("removing resource '{}'", resource);
				Streams.safelyClose(contents.get(resource));
				contents.remove(resource);				
			} else {
				logger.trace("keeping resource '{}'", resource);
			}
		}
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#clear()
	 */
	@Override
	public void clear() {
		logger.debug("clearing storage");
		for(String resource : contents.keySet()) {
			Streams.safelyClose(contents.get(resource));
		}
		contents.clear();
	}	
}
