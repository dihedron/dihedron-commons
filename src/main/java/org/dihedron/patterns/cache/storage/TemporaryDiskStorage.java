/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.cache.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dihedron.core.License;
import org.dihedron.core.regex.Regex;
import org.dihedron.patterns.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class implements the mechanics to persist cache resources onto disk.
 * 
 * @author Andrea Funto'
 */
@License
public class TemporaryDiskStorage extends AbstractStorage {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(TemporaryDiskStorage.class);

	/** 
	 * The default path to the cache. 
	 */
	public static final File DEFAULT_CACHE_LOCATION = new File(".cache");
	
	/**
	 * Whether the directory tree should be created if missing from disk.
	 */
	public static final boolean DEFAULT_CREATE_IF_MISSING = true;
	
	/** 
	 * The directory where the cache is kept. 
	 */
	private File directory;
	
	/**
	 * A unique string that allows to distinguish among temporary files allocated 
	 * to concurrent instances of the cache library.
	 */
	private String instance;
	
	/**
	 * The index of the cache contents.
	 */
	private Map<String, File> index = new HashMap<String, File>();

	/**
	 * Constructor; creates the cache storage in the default directory.
	 * 
	 * @throws Exception 
	 * @see TemporaryDiskStorage#DEFAULT_CACHE_LOCATION
	 */
	public TemporaryDiskStorage() throws CacheException {
		this(DEFAULT_CACHE_LOCATION, DEFAULT_CREATE_IF_MISSING);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param path
	 *   the directory where the cache storage will be created/installed.
	 * @throws Exception
	 *   if the input values are invalid or if the combination of 
	 *   parameters is not compatible with the creation of the cache.
	 */
	public TemporaryDiskStorage(String path) throws CacheException {
		this(new File(path), DEFAULT_CREATE_IF_MISSING);
	}	
	
	/**
	 * Constructor.
	 * 
	 * @param path
	 *   the directory where the cache will be created/installed.
	 * @throws Exception
	 *   if the input values are invalid or if the combination of 
	 *   parameters is not compatible with the creation of the cache.
	 */
	public TemporaryDiskStorage(File path) throws CacheException {
		this(path, DEFAULT_CREATE_IF_MISSING);
	}	
	
	/**
	 * Constructor.
	 * 
	 * @param path
	 *   the directory where the cache storage will be created/installed.
	 * @param createIfMissing
	 *   if <code>true</code>, the directory will be created if
	 *   not existing on disk.
	 * @throws Exception
	 *   if the input values are invalid or if the combination of 
	 *   parameters is not compatible with the creation of the cache.
	 */
	public TemporaryDiskStorage(File path, boolean createIfMissing) throws CacheException {
		instance = ManagementFactory.getRuntimeMXBean().getName();
		if(path == null) {
			throw new CacheException("Null file specified for the cache storage");
		}		
		if(path.exists()) {
			if(!path.isDirectory()) {	
				logger.error("{} is not a directory", path.getAbsolutePath());
				throw new CacheException(path.getAbsolutePath() + " is not a directory");
			} else {
				logger.debug("installing cache in directory {}", path.getAbsolutePath());
				directory = path;
			}
		} else {
			if(createIfMissing) {
				if(path.mkdirs()) {
					logger.info("created cache in directory {}", path.getAbsolutePath());
					directory = path;
				} else {
					logger.error("error creating new directory in {}", path.getAbsolutePath());
					throw new CacheException("Error creating new directory in " + path.getAbsolutePath());
				}				
			} else {
				logger.error("directory {} does not exist", path.getAbsolutePath());
				throw new CacheException("Directory " + path.getAbsolutePath() + " does not exist");				
			}
		}
	}
		
	/**
	 * Constructor.
	 * 
	 * @param path
	 *   the directory where the cache storage will be created/installed.
	 * @param createIfMissing
	 *   if <code>true</code>, the directory will be created if
	 *   not existing on disk. 
	 * @throws CacheException
	 *   if the input values are invalid or if the combination of 
	 *   parameters is not compatible with the creation of the cache.
	 */
	public TemporaryDiskStorage(String path, boolean createIfMissing) throws CacheException{
		this(new File(path), createIfMissing);
	}
	
	/**
	 * Returns the physical cache location.
	 * 
	 * @return
	 *   the physical cache location.
	 */
	public File getLocation() {
		return directory;
	}
		
	/**
	 * @see org.dihedron.patterns.cache.Storage#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return index.isEmpty();
	}	

	/**
	 * @see org.dihedron.patterns.cache.Storage#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String resource) {
		return index.containsKey(resource);
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#list(org.dihedron.core.regex.Regex)
	 */
	@Override
	public String[] list(Regex regex) {
		if(regex == null) {
			logger.debug("returning full list of storage contents");
			return index.keySet().toArray(new String[0]);
		} else {		
			logger.debug("returning list of resources matching /{}/", regex);
			List<String> matched = new ArrayList<String>();
			for(String key : index.keySet()) {
				if(regex.matches(key)) {
					matched.add(key);
				}				
			}
			return matched.toArray(new String[0]);
		}
	}	
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#store(java.lang.String, java.io.InputStream)
	 */	
	@Override
	public OutputStream store(String resource) throws CacheException {
		File file = null;
		try {
			delete(resource, true);
			file = File.createTempFile(instance, null, directory);
			file.deleteOnExit();
			logger.debug("storing '{}' into cache as '{}'", resource, file.getAbsolutePath());
			index.put(resource, file);
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			logger.error("error opening output stream", e);
			throw new CacheException("error opening output stream to '" + file.getAbsolutePath() + "'", e);
		} catch (IOException e) {
			logger.error("error allocating temporary file", e);
			throw new CacheException("error allocating temporary file", e);
		}
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#retrieveAsStream(java.lang.String)
	 */
	@Override
	public InputStream retrieve(String resource) {
		try {
			if(index.containsKey(resource)) {
				return new FileInputStream(index.get(resource));
			}
		} catch (FileNotFoundException e) {
			logger.error("resource '{}' does not exist", resource);
		}
		return null;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(org.dihedron.core.regex.Regex)
	 */
	public void delete(Regex regex) {
		logger.debug("deleting files that match /{}/ from cache", regex);
		for(String resource : list(regex)) {
			logger.debug("removing '{}' from cache", resource);
			delete(resource, true);
		}
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(java.lang.String, boolean)
	 */
	public void delete(String resource, boolean caseSensitive){
		logger.debug("deleting resource '{}' from cache", resource);
		if(index.containsKey(resource)) {
			File file = index.get(resource);
			logger.debug("removing '{}' from cache", file.getName());
			boolean result = file.delete();
			logger.debug("file {}removed from cache", (result ? "" : "not "));
		}		
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#clear()
	 */
	public void clear() {
		logger.debug("clearing cache");				
		for(String resource : list()) {
			logger.debug("removing '{}' from cache", resource);
			delete(resource, true);
		}		
	}
}
