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
 * A class representing the response to an HTTP request.
 * 
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
	
	/**
	 * Returns the response content encoding.
	 * 
	 * @return
	 *   the response content encoding.
	 */
	public String getContentEncoding() {
		return connection.getContentEncoding();
	}
		
	/**
	 * Returns the response content length.
	 * 
	 * @return
	 *   the response content length.
	 */
	public int getContentLength() {
		return connection.getContentLength();
	}
	
	/**
	 * Returns the response content MIME type.
	 * 
	 * @return
	 *   the response content MIME type.
	 */
	public String getContentType() {
		return connection.getContentType();
	}
	
	/**
	 * Returns the date of the response.
	 * 
	 * @return
	 *   the date of the response.
	 */
	public long getDate() {
		return connection.getDate();
	}
	
	/**
	 * Returns the expiration date of the response data.
	 * 
	 * @return
	 *   the expiration date of the response data.
	 */
	public long getExpiration() {
		return connection.getExpiration();
	}
	
	/**
	 * Returns information about the time stamp of the last content modification.
	 * 
	 * @return
	 *   information about the time stamp of the last content modification.
	 */
	public long getLastModified() {
		return connection.getLastModified();
	}
	
	/**
	 * Returns a {@code Permission} object representing the permissions needed 
	 * to access the content.
	 *  
	 * @return
	 *   a {@code Permission} object representing the permissions needed to access 
	 *   the content.
	 * @throws IOException
	 */
	public Permission getPermission() throws IOException {
		return connection.getPermission();
	}
	
	/**
	 * Returns the content as an object.
	 * 
	 * @return
	 *   the content as an object.
	 * @throws IOException
	 */
	public Object getContent() throws IOException {
		return connection.getContent();
	}
	
	/**
	 * Returns the input stream underlying the response.
	 * 
	 * @return
	 *   the input stream underlying the response.
	 * @throws IOException
	 */
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
//			logger.trace("response data:\n{}", data);
			return data;
		} catch (IOException e) {
			logger.error("error reading response data from stream", e);
		}
		return null;
	}	
}
