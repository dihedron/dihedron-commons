/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.properties;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dihedron.core.License;
import org.dihedron.core.strings.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements an alternative to the standard Properties in that you 
 * can specify a different key/value separator character sequence and you can 
 * read it from any source, including an input stream. Moreover, if the file
 * containes one or more property keyed with the special character "@", the value
 * is taken to be the name of a file, and it is loaded at the point where it is 
 * encountered, possibly overriding the value of any variable defined so far
 * by the including file.
 * 
 * @author Andrea Funto'
 */
@License
public class Properties extends LinkedHashMap<String, String>{
		
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = -1286708636833164372L;

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Properties.class);

	/**
	 * The default character using for telling key and value apart.
	 */	
	public static final String DEFAULT_SEPARATOR = "=";
		
	/**
	 * The default comment start character sequence.
	 */
	public final static String LINE_COMMENT_START = "#";
	
	/**
	 * The character(s) indicating that a property is stored on 
	 * multiple lines, and that it continues on the following line.
	 */
	public final static String CONTINUE_ON_NEW_LINE = "\\"; 	
	/**
	 * A constant indicating whether the properties can be modified.
	 */
	private boolean locked = false;
	
//	/**
//	 * The map that actually implements the properties dictionary.
//	 */
//	private Map<String, String> map;
	
	/**
	 * Constructor.
	 */
	public Properties() {
		super();
	}
	
	/**
	 * Constructor.
	 *
	 * @param values
	 *   a map of initial values.
	 */
	public Properties(Map<String, String> values) {
		super(values);
	}
	
	/**
	 * Sets the properties map in read-only mode.
	 */
	public void lock() {
		this.locked = true;
	}
	
	/**
	 * Sets the properties map in modifiable mode.
	 */
	public void unlock() {
		this.locked = false;
	}
	
	/**
	 * Returns whether the object contents is read only or it can be modified 
	 * (e.g. a property can be added or removed).
	 * 
	 * @return
	 *   whether the object contents are not modifiable (e.g. properies can be 
	 *   added or removed).
	 */
	public boolean isLocked() {
		return locked;
	}
	
	/**
	 * Returns whether the object contents can be modified (e.g. a property can be added or removed).
	 * 
	 * @return
	 *   whether the object contents can be modified (e.g. a property 
	 *   can be added or removed).
	 */
	public boolean isModifiable() {
		return !locked;
	}
		
	/**
	 * Loads the properties from an input file.
	 * 
	 * @param filename
	 *   the name of the file.
	 * @throws IOException
	 * @throws PropertiesException
	 *   if the mapis locked. 
	 */
	public void load(String filename) throws IOException, PropertiesException {
		load(new File(filename), DEFAULT_SEPARATOR);
	}

	/**
	 * Loads the properties from an input file.
	 * 
	 * @param filename
	 *   the name of the file.
	 * @param separator
	 *   the separator character.
	 * @throws IOException
	 * @throws PropertiesException 
	 */
	public void load(String filename, String separator) throws IOException, PropertiesException {
		load(new File(filename), separator);
	}
	
	/**
	 * Loads the properties from an input file.
	 * 
	 * @param file
	 *   the input file.
	 * @throws IOException
	 * @throws PropertiesException 
	 */
	public void load(File file) throws IOException, PropertiesException {
		load(file, DEFAULT_SEPARATOR);
	}

	/**
	 * Loads the properties from an input file.
	 * 
	 * @param file
	 *   the input file.
	 * @param separator
	 *   the separator character.
	 * @throws IOException
	 * @throws PropertiesException 
	 */
	public void load(File file, String separator) throws IOException, PropertiesException {
		try (InputStream stream = new FileInputStream(file)){
			load(stream, separator);
		}
	}

	/**
	 * Loads the properties from an input stream.
	 * 
	 * @param stream
	 *   an input stream.
	 * @throws IOException
	 * @throws PropertiesException 
	 */
	public void load(InputStream stream) throws IOException, PropertiesException {
		load(stream, DEFAULT_SEPARATOR);
	}
		
	/**
	 * Loads the properties from an input stream.
	 * 
	 * @param stream
	 *   an input stream.
	 * @param separator
	 *   the separator character.
	 * @throws IOException
	 * @throws PropertiesException 
	 */
	public void load(InputStream stream, String separator) throws IOException, PropertiesException {
		 
		try(DataInputStream data = new DataInputStream(stream); Reader reader = new InputStreamReader(data); BufferedReader br = new BufferedReader(reader)) {
			String line;
			StringBuilder buffer = new StringBuilder();
			while ((line = br.readLine()) != null)   {
				if(Strings.trimRight(line).startsWith(LINE_COMMENT_START)) {
					// comment line, skip!
					continue;
				} else {					
					// now check if multi-line property
					if(line.endsWith(CONTINUE_ON_NEW_LINE)) {
						// middle line in multi-line, add and skip parsing
						buffer.append(line.replaceFirst("\\\\$", ""));
						continue;
					} else {
						// ordinary line, or end of multi-line: add and parse
						buffer.append(line);
					}
				}
				line = buffer.toString().trim();
				buffer.setLength(0);
				int index = -1;
				if(line.length() > 0 && (index = line.lastIndexOf(separator)) != -1) {
					String key = line.substring(0, index).trim();
					String value = line.substring(index + separator.length()).trim();
					if("@".equals(key)) {
						logger.trace("including and overriding values defined so far with file '{}'", value);
						load(value, separator);
					} else {
						logger.trace("adding '{}' => '{}'", key, value);
						this.put(key.trim(), value.trim());
					}
				}
			}
			if(buffer.length() > 0) {
				logger.warn("multi-line property '{}' is not properly terminated", buffer);
			}
		}
	}	
	
	/**
	 * Merges the contents of another property set into this; if this object and 
	 * the other one both contain the same keys, those is the other object will
	 * override this object's.
	 *  
	 * @param properties
	 *   the other property set to merge into this (possibly overriding).
	 * @throws PropertiesException 
	 */
	public void merge(Properties properties) throws PropertiesException {
		if(isLocked()) {
			throw new PropertiesException("properties map is locked, its contents cannot be altered.");
		}				
		assert properties != null;
		for(Entry<String, String> entry : properties.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Returns whether the properties map contains the given key.
	 * 
	 * @param key
	 *   the key of the property whose existence is being tested.
	 * @return
	 *   whether the properties map contains the given key.
	 */
	public boolean hasKey(String key) {
		return containsKey(key);
	}

	/**
	 * Puts a new value into the properties map.
	 * 
	 * @param key
	 *   the key of the property to set.
	 * @param value
	 *   the value of the property to set.
	 * @return
	 *   the previous value associated with the key, or null if no such value
	 *   was present in the map.
	 * @throws PropertiesException
	 *   if the map is in read-only mode (note: it's a runtime exception).
	 */
	@Override
	public String put(String key, String value) throws PropertiesException {
		if(isLocked()) {
			throw new PropertiesException("properties map is locked, its contents cannot be altered.");
		}		
		return super.put(key, value);
	}
	
	/**
	 * Returns the keys in the configuration file.
	 * 
	 * @return
	 *   the keys in the configuration file.
	 */
	public Set<String> getKeys() {
		return keySet();
	}
}
