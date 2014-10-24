/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.cache.storage;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;

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
public class DiskStorage extends AbstractStorage {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(DiskStorage.class);

	/** 
	 * The default path to the cache. 
	 */
	public static final File DEFAULT_CACHE_LOCATION = new File(".cache");

	/** 
	 * Whether by default the storage engine should create the directory tree
	 * if this is not ready yet.
	 */
	public static final boolean DEFAULT_CREATE_IF_MISSING = true;	
			
	/** 
	 * The directory where the cache is kept. 
	 */
	private File directory;

	/** 
	 * Whether the file names on disk are treated respecting the case. 
	 */
	private boolean caseSensitive = false;
		
	/**
	 * Constructor; creates the cache storage in the default directory.
	 * 
	 * @throws CacheException 
	 * @see DiskStorage#DEFAULT_CACHE_LOCATION
	 */
	public DiskStorage() throws CacheException {
		this(DEFAULT_CACHE_LOCATION, DEFAULT_CREATE_IF_MISSING);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param path
	 *   the directory where the cache storage will be created/installed.
	 * @throws CacheException
	 *   if the input values are invalid or if the combination of 
	 *   parameters is not compatible with the creation of the cache.
	 */
	public DiskStorage(String path) throws CacheException {
		this(new File(path), DEFAULT_CREATE_IF_MISSING);
	}	
	
	/**
	 * Constructor.
	 * 
	 * @param path
	 *   the directory where the cache will be created/installed.
	 * @throws CacheException
	 *   if the input values are invalid or if the combination of 
	 *   parameters is not compatible with the creation of the cache.
	 */
	public DiskStorage(File path) throws CacheException {
		this(path, DEFAULT_CREATE_IF_MISSING);
	}	

	/**
	 * Constructor.
	 * 
	 * @param path
	 *   the directory where the cache storage will be created/installed.
	 * @param createIfMissing
	 *   if {@code true}, the directory will be created if not existing on disk.
	 * @throws Exception
	 *   if the input values are invalid or if the combination of 
	 *   parameters is not compatible with the creation of the cache.
	 */
	public DiskStorage(String path, boolean createIfMissing) throws CacheException {
		this(new File(path), createIfMissing);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param path
	 *   the directory where the cache storage will be created/installed.
	 * @param createIfMissing
	 *   if {@code true}, the directory will be created if not existing on disk.
	 * @throws Exception
	 *   if the input values are invalid or if the combination of 
	 *   parameters is not compatible with the creation of the cache.
	 */
	public DiskStorage(File path, boolean createIfMissing) throws CacheException {
		if(path == null) {
			throw new CacheException("null file specified for the cache storage");
		}		
		if(path.exists()) {
			if(!path.isDirectory()) {	
				logger.error("'{}' is not a directory", path.getAbsolutePath());
				throw new CacheException("'" + path.getAbsolutePath() + "' is not a directory");
			} else {
				logger.debug("installing cache in directory '{}'", path.getAbsolutePath());
				directory = path;
			}
		} else {
			if(createIfMissing) {
				if(path.mkdirs()) {
					logger.info("created cache in directory '{}'", path.getAbsolutePath());
					directory = path;
				} else {
					logger.error("error creating new directory in '{}'", path.getAbsolutePath());
					throw new CacheException("error creating new directory in '" + path.getAbsolutePath() + "'");
				}				
			} else {
				logger.error("directory '{}' does not exist", path.getAbsolutePath());
				throw new CacheException("directory '" + path.getAbsolutePath() + "' does not exist");				
			}
		}
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
	 * Sets the behaviour of the cache storage with respect to
	 * case sensitivity; on some systems (e.g. Windows), the file
	 * system is case insensitive; on others (Unix, Linux, MacOS) 
	 * it treats file names with differing cases differently.
	 * 
	 * @param caseSensitive
	 *   whether the cache storage should treat resource names
	 *   differing only bay the case differently.
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;		
	}
	
	/**
	 * Returns whether the cache storage is case sensitive or not
	 * with respect to resource names.
	 * 
	 * @return
	 *   {@code true} if case sensitive, {@code false} otherwise.
	 */
	public boolean isCaseSensitive(){
		return caseSensitive;
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		String [] files = directory.list();
		for(String file : files) logger.debug("file: '{}'", file);
		return files == null || files.length == 0;
	}	

	/**
	 * @see org.dihedron.patterns.cache.Storage#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String resource) {
		if(resource == null || resource.length() == 0) {
			logger.error("invalid resource name provided");
			return false;
		}
		logger.debug("checking if '{}' is in cache", resource);
		File file = new File(directory, resource);
		return file.exists() && file.isFile();		
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#list(org.dihedron.core.regex.Regex)
	 */
	@Override
	public String[] list(Regex regex) {
		if(regex == null) { 
			logger.debug("returning full list of storage contents");
			return directory.list();
		}
		logger.debug("returning list of resources matching /{}/", regex);
		return directory.list(this.new Filter(regex));
	}	
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#store(java.lang.String)
	 */	
	@Override
	public OutputStream store(String resource) throws CacheException {
		File file = new File(directory, resource);
		try {
			delete(resource, caseSensitive);			
			logger.debug("storing '{}' into cache as '{}'", resource, file.getAbsolutePath());		
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			logger.error("error opening output stream to '" + file.getAbsolutePath() + "'", e);
			throw new CacheException("error opening output stream to '" + file.getAbsolutePath() + "'", e);
		}
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#retrieveAsStream(java.lang.String)
	 */
	@Override
	public InputStream retrieve(String resource) {
		try {
			return new FileInputStream(new File(directory, resource));
		} catch (FileNotFoundException e) {
			logger.error("resource '{}' does not exist", resource);
		}
		return null;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(org.dihedron.core.regex.Regex)
	 */
	public void delete(Regex regex) {
		logger.debug("deleting files that match /" + regex + "/ from cache");
		File [] files = directory.listFiles((FileFilter)new Filter(regex));
		for (File file : files) {
			logger.debug("removing '{}' from cache", file.getName());
			if(file.delete()) {
				logger.debug("file removed from cache");
			} else {
				logger.warn("file not removed from cache");
			}
		}	
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(java.lang.String, boolean)
	 */
	public void delete(String resource, boolean caseSensitive){
		logger.debug("deleting resource '{}' from cache", resource);
		if(!caseSensitive) {
			File file = new File(getLocation() + "\\" + resource);
			if(file.exists()) {
				logger.debug("file found: deleting '{}'", resource);
				file.delete();
			}
		} else {
			String [] names = directory.list();
			for (String name : names) {
				logger.debug("checking {}...", name);
				if(name.equalsIgnoreCase(resource)) {					
					delete(name, false);
					return;
				}
			}
		}
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#clear()
	 */
	public void clear() {
		logger.debug("clearing cache");
		File[] files = directory.listFiles();
		for (File file : files) {
			logger.debug("removing '{}' from cache", file.getName());
			file.delete();
		}		
	}
	
	/**
	 * This class provides a way of filtering/selecting items given their name 
	 * or a regular expression.
	 * 
	 * @author Andrea Funto'
	 */
	protected class Filter implements FilenameFilter, FileFilter {
		
		/** 
		 * The logger. 
		 */
		private final Logger logger = LoggerFactory.getLogger(Filter.class);		
		
		/** 
		 * The regular expression against which the filtering is performed. 
		 */
		private Regex regex;
		
		/**
		 * Constructor.
		 * 
		 * @param regex
		 *   a regular expression.
		 */
		public Filter(Regex regex) {
			this.regex = regex;
			logger.debug("checking regex /{}/, case {}", regex, (regex.isCaseSensitive() ? "sensitive" : "insensitive"));			 
		}

		/**
		 * Checks whether a resource complies with the filter criteria.
		 */		
		public boolean accept(File dir, String name) {
			boolean result = regex.matches(name);
			logger.debug("checked resource '{}', result is {}", name, result);
			return result;
		}

		/**
		 * Checks whether a resource complies with the filter criteria.
		 */
		public boolean accept(File pathname) {
			return accept(null, pathname.getName());
		}		
	}	
}
