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
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.lf5.util.StreamUtils;
import org.dihedron.commons.cache.CacheException;
import org.dihedron.commons.regex.Regex;
import org.dihedron.commons.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class implements the mechanics to persist cache resources onto disk.
 * 
 * @author Andrea Funto'
 */
public class DiskStorage implements Storage {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(DiskStorage.class);

	/** 
	 * The default path to the cache. 
	 */
	public static final File DEFAULT_CACHE_LOCATION = new File(".cache");
	
	/** 
	 * The directory where the cache is kept. 
	 */
	private File directory;

	/** 
	 * Whether the file names on disk are treated respecting the case. 
	 */
	private boolean caseSensitive = false;
	
	/**
	 * This class provides a way of filtering/selecting items given
	 * their name or a regular expression.
	 */
	protected static class Filter implements FilenameFilter, FileFilter {
		
		/** 
		 * The logger. 
		 */
		private static Logger logger = LoggerFactory.getLogger(Filter.class);		
		
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
		 * Checks whether a resource complies with the filter
		 * criteria.
		 */		
		public boolean accept(File dir, String name) {
			boolean result = regex.matches(name);
			logger.debug("checked resource '{}', result is {}", name, result);
			return result;
		}

		/**
		 * Checks whether a resource complies with the filter
		 * criteria.
		 */
		public boolean accept(File pathname) {
			return accept(null, pathname.getName());
		}		
	}	

	/**
	 * Constructor; creates the cache storage in the default directory.
	 * 
	 * @throws Exception 
	 * @see DiskStorage#DEFAULT_CACHE_LOCATION
	 */
	public DiskStorage() throws CacheException {
		this(DEFAULT_CACHE_LOCATION, true);
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
	public DiskStorage(String path) throws CacheException {
		this(new File(path), true);
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
	public DiskStorage(File path) throws CacheException {
		this(path, true);
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
	public DiskStorage(File path, boolean createIfMissing) throws CacheException {
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
	 * @throws Exception
	 *   if the input values are invalid or if the combination of 
	 *   parameters is not compatible with the creation of the cache.
	 */
	public DiskStorage(String path, boolean createIfMissing) throws Exception{
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
	 *   <code>true</code> if case sensitive, <code>false</code>
	 *   otherwise.
	 */
	public boolean isCaseSensitive(){
		return caseSensitive;
	}	
	
	/**
	 * @see org.dihedron.commons.cache.storage.Storage#isEmpty()
	 */
	public boolean isEmpty() {
		String [] files = directory.list();
		return files == null || files.length == 0;
	}	

	/**
	 * @see org.dihedron.commons.cache.storage.Storage#contains(java.lang.String)
	 */
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
	 * @see org.dihedron.commons.cache.storage.Storage#list(org.dihedron.commons.regex.Regex)
	 */
	public String[] list(Regex regex) {
		if(regex == null) { 
			logger.debug("returning full list of storage contents");
			return directory.list();
		}
		logger.debug("returning list of resources matching /{}/", regex);
		return directory.list(new Filter(regex));
	}	
	
	/**
	 * @see org.dihedron.commons.cache.storage.Storage#store(java.lang.String, java.io.InputStream)
	 */	
	public void store(String resource, InputStream stream) throws CacheException {		
		delete(resource, caseSensitive);
		logger.debug("storing '{}' into cache", resource);
		FileOutputStream fos = null;
		try {
			try {
				fos = new FileOutputStream(new File(directory, resource));
				Streams.copy(stream, fos);
			} finally {
				if(fos != null) {
					fos.close();
				}
			}
		} catch(IOException e) {
			logger.error("error storing data to disk", e);
			throw new CacheException("Error storing data to disk", e);
		}
	}

	/**
	 * @see org.dihedron.commons.cache.storage.Storage#store(java.lang.String, byte[])
	 */
	public void store(String resource, byte[] data) throws CacheException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		store(resource, bais);
	}
	
	/**
	 * @see org.dihedron.commons.cache.storage.Storage#retrieveAsStream(java.lang.String)
	 */
	public InputStream retrieveAsStream(String resource) {
		try {
			return new FileInputStream(new File(directory, resource));
		} catch (FileNotFoundException e) {
			logger.error("resource '{}' does not exist", resource);
		}
		return null;
	}

	/**
	 * @see org.dihedron.commons.cache.storage.Storage#retrieveAsByteArray(java.lang.String)
	 */
	public byte[] retrieveAsByteArray(String resource) {
		InputStream input = retrieveAsStream(resource);
		if(input != null){
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				StreamUtils.copy(input, output);
				input.close();
				logger.debug("returning resource as byte array");
				return output.toByteArray();
			} catch (FileNotFoundException e) {
				logger.error("error retrieving resource '" + resource  + "'", e);
			} catch (IOException e) {				
				logger.error("error retrieving resource '" + resource + "'", e);
			}
		}			
		return null;
	}

	/**
	 * @see org.dihedron.commons.cache.storage.Storage#delete(org.dihedron.commons.regex.Regex)
	 */
	public void delete(Regex regex) {
		logger.debug("deleting files that match /" + regex + "/ from cache");
		File [] files = directory.listFiles((FileFilter)new Filter(regex));
		for (File file : files) {
			logger.debug("removing '{}' from cache", file.getName());
			boolean result = file.delete();
			logger.debug("file {}removed from cache", (result ? "" : "not "));
		}	
	}

	/**
	 * @see org.dihedron.commons.cache.storage.Storage#delete(java.lang.String, boolean)
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
	 * @see org.dihedron.commons.cache.storage.Storage#clear()
	 */
	public void clear() {
		logger.debug("clearing cache");
		File[] files = directory.listFiles();
		for (File file : files) {
			logger.debug("removing '{}' from cache", file.getName());
			file.delete();
		}		
	}
}
