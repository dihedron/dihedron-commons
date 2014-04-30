/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Commons library ("Commons").
 *
 * Commons is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Commons is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Commons. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.core.library;

import java.io.IOException;
import java.io.InputStream;

import org.dihedron.core.properties.Properties;
import org.dihedron.core.properties.PropertiesException;
import org.dihedron.core.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public abstract class Library {
	
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(Library.class);

	/**
	 * The name of the file from which the library properties will be loaded.
	 */
	private static final String PROPERTIES_FILE = "classpath:${library}.properties";
			
	/**
	 * The name of the library.
	 */
	private String name; 
	
	/**
	 * The overall library properties.
	 */
	private final Properties properties = new Properties();

	/**
	 * Constructor.
	 *
	 * @param name
	 *   the name of the library; the "&lt;library&gt;.properties" (where <&lt;library&gt;
	 *   is the name of the library) will be loaded from the root of the classpath.
	 */
	protected Library(String name) {
		this.name = name;
		InputStream stream = null;
		try {
			String path = PROPERTIES_FILE.replaceAll("\\$\\{library\\}", name);
			logger.trace("loading from '{}'", path);
			stream = Streams.fromURL(path);
			properties.load(stream);
		} catch (IOException e) {
			logger.error("error loading library properties from input stream", e);
		} catch (PropertiesException e) {
			logger.error("error loading library properties from input stream", e);
		} finally {
			if(stream != null) {
				try {
					stream.close();
					stream = null;
				} catch(IOException e) {
					logger.error("error closing library properties stream", e);
				}
			}
		}		
	}
	
	/**
	 * Returns the given value from the properties file.
	 * 
	 * @param key
	 *   the name of the property to be retrieved.
	 * @return
	 *   the value of the property.
	 */
	public String get(Traits key) {
		return properties.get(key.toString(name));
	}
}
