/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.dihedron.core.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class HttpResponse implements AutoCloseable {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(HttpResponse.class);
	
	/**
	 * The input stream returned by the client upon a request. 
	 */
	private InputStream stream;
	
	/**
	 * Constructor.
	 *
	 * @param stream
	 *   the input stream returned by the client upon an HTTP request.
	 */
	HttpResponse(InputStream stream) {
		this.stream = stream;
	}
	
	/**
	 * Dumps the response data as a string.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try(ByteArrayOutputStream output = new ByteArrayOutputStream()) { 
			Streams.copy(stream, output);
			String data = new String(output.toByteArray());
			logger.trace("response data:\n{}", data);
			return data;
		} catch (IOException e) {
			logger.error("error reading response data from stream", e);
		}
		return null;
	}

	/**
	 * @throws IOException 
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws IOException {
		if(stream != null) {
			stream.close();
		}		
	}	
}
