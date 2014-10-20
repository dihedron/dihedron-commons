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
import java.io.IOException;
import java.io.InputStream;

import org.dihedron.core.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class retrieves a resource from the JAR, using the
 * class loader methods, and returns it as a stream.
 * 
 * @author Andrea Funto'
 */
public class JarStaticResource implements StaticResource {
	
	/** 
	 * The logger. 
	 */
	private static Logger logger = LoggerFactory.getLogger(JarStaticResource.class);
		
	/** 
	 * The fully qualified path of the resource. 
	 */
	private String resource = null;
	
	/** 
	 * The MIME type of the resource. 
	 */
	private String contentType = null;
	
	/**
	 * Constructor.
	 * 
	 * @param resource
	 *   the name of the resource.
	 * @param contentType
	 *   the MIME type of the resource (e.g. "text/html").  
	 */
	public JarStaticResource(String resource, String contentType) {
		this.resource = resource;
		this.contentType = contentType;
		logger.debug("retrieving resource '{}' with fully qualified name", resource);
	}

	//@Override
	public byte[] getData() {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(resource); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			long copied = Streams.copy(input, output);
			logger.trace("copied {} bytes for resource '{}'", copied, resource);
			return output.toByteArray();
		} catch(IOException e) {
			logger.error("error reading resource '" + resource + "' data", e);
		}
		return null;
	}

	//@Override
	public String getContentType() {
		return contentType;
	}
}
