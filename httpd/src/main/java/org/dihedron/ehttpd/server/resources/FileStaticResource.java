/**
 * Copyright (c) 2011, 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron embeddable web container ("e-httpd").
 *
 * "e-httpd" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "e-httpd" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "e-httpd". If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.ehttpd.server.resources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.dihedron.core.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Provides MIME-type information and raw data for a static resource on the file 
 * system.
 * 
 * @author Andrea Funto'
 */
public class FileStaticResource implements StaticResource {
	
	/**
	 * The logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(FileStaticResource.class);
	
	/**
	 * The content type.
	 */
	private String mimeType;
	
	/**
	 * The path to the file system resource.
	 */
	private String resourcePath;
	
	/**
	 * Constructor.
	 *
	 * @param mimeType
	 *   the data content type.
	 * @param resourcePath
	 *   the path to the file system resource.
	 */
	public FileStaticResource(String mimeType, String resourcePath) {
		this.mimeType = mimeType;
		this.resourcePath = resourcePath;
		logger.debug("creating file resource of type '{}' on file '{}'", mimeType, resourcePath);
	}

	/**
	 * Retrieves the data MIME type.
	 * 
	 * @see org.dihedron.ehttpd.resources.StaticResource#getContentType()
	 */
	public String getContentType() {
		return mimeType;
	}

	/**
	 * Reads the file from the file system and returns it.
	 * 
	 * @see org.dihedron.ehttpd.resources.StaticResource#getData()
	 */
	public byte[] getData() {
		try(FileInputStream input = new FileInputStream(new File(resourcePath)); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			long copied = Streams.copy(input, output);
			logger.debug("resource '{}' exists on the filesystem, size is {} bytes", resourcePath, copied);
			return output.toByteArray(); 
		} catch(IOException e) {
			logger.error("I/O exception accessing resource '" + resourcePath + "'", e);
		}
		return null;
	}
}
