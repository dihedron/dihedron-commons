/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.Permission;

import org.dihedron.core.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class HttpResponse {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(HttpResponse.class);
	
	/**
	 * The URL connection returned by the client upon an HTTP request. 
	 */
	private URLConnection connection;
	
	/**
	 * Constructor.
	 *
	 * @param connection
	 *   the URL connection returned by the client upon an HTTP request.
	 */
	HttpResponse(URLConnection connection) {
		this.connection = connection;
	}
	
	public String getContentEncoding() {
		return connection.getContentEncoding();
	}
		
	public int getContentLength() {
		return connection.getContentLength();
	}
	
	public String getContentType() {
		return connection.getContentType();
	}
	
	public long getDate() {
		return connection.getDate();
	}
	
	public long getExpiration() {
		return connection.getExpiration();
	}
	
	public long getLastModified() {
		return connection.getLastModified();
	}
	
	public Permission getPermission() throws IOException {
		return connection.getPermission();
	}
	
	public Object getContent() throws IOException {
		return connection.getContent();
	}
	
	public InputStream getInputStream() throws IOException {
		return connection.getInputStream();
	}
	
	/**
	 * Dumps the response data as a string.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try(InputStream input = connection.getInputStream(); ByteArrayOutputStream output = new ByteArrayOutputStream()) { 
			Streams.copy(input, output);
			String data = new String(output.toByteArray());
			logger.trace("response data:\n{}", data);
			return data;
		} catch (IOException e) {
			logger.error("error reading response data from stream", e);
		}
		return null;
	}	
}
