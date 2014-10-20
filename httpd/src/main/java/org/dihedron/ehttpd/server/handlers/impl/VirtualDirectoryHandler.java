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
package org.dihedron.ehttpd.server.handlers.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.dihedron.core.streams.Streams;
import org.dihedron.ehttpd.exceptions.ApplicationException;
import org.dihedron.ehttpd.exceptions.ResourceNotFoundException;
import org.dihedron.ehttpd.exceptions.ServerException;
import org.dihedron.ehttpd.server.Request;
import org.dihedron.ehttpd.server.Response;
import org.dihedron.ehttpd.server.handlers.Handler;
import org.dihedron.ehttpd.server.resources.MimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class supports serving of static resources (like CSS style sheets, static 
 * HTML pages etc.) from a root directory; all resources are located on the class
 * path or in the directory specified.
 * 
 * @author Andrea Funto'
 */
public class VirtualDirectoryHandler implements Handler {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(VirtualDirectoryHandler.class);
	
	/**
	 * Static resource provider.
	 */
	private File directory;
	
	/**
	 * Constructor.
	 * 
	 * @param resource
	 *   a static resource provider, which is in charge of
	 *   providing a MIME-type description of the resource
	 *   and the resource data, as a raw byte array.
	 */
	public VirtualDirectoryHandler(File directory) {
		this.directory = directory;
	}

	/**
	 * Serves a static resources to the client from the given directory.
	 */
	@Override
	public void handle(Request request, Response response) throws ApplicationException, ServerException {
		String resource = request.getHttpQuery().getResource();
		MimeType mime = MimeType.getMimeType(resource);
		
		try(FileInputStream input = new FileInputStream(new File(directory, resource)); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			Streams.copy(input, output);	
			byte [] data = output.toByteArray();
			if (data != null) {
				response.setHeader("Content-Type", mime.getContentType());
				response.addContent(data);
			} else {
				logger.error("resource not found, throwing exception");
				throw new ResourceNotFoundException();
			}
		} catch (IOException e) {
			logger.error("error reading rsource from disk", e);
			throw new ResourceNotFoundException("error reading resource from disk");
		}
				
	}
}
