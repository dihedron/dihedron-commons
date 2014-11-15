/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.dihedron.core.License;

/**
 * @author Andrea Funto'
 */
@License
public class HttpFileParameter extends HttpParameter {

	/**
	 * The name of the file to send.
	 */
	private String filename;
	
	/**
	 * The content type of the attached file.
	 */
	private String contentType;
	
	/**
	 * The input stream from which the file can be read.
	 */
	private InputStream stream;
	
	/**
	 * Constructor.
	 *
	 * @param name
	 *   the name of the HTTP parameter.
	 * @param filename
	 *   the name of the file.
	 * @param contentType
	 *   the content type of the attached file.
	 * @param file
	 *   a File object representing the file.
	 * @throws FileNotFoundException
	 *   if the File corresponds to no existing object on disk. 
	 */
	public HttpFileParameter(String name, String filename, String contentType, File file) throws FileNotFoundException {
		this(name, filename, contentType, new FileInputStream(file));
	}
	
	/**
	 * Constructor.
	 *
	 * @param name
	 *   the name of the HTTP parameter.
	 * @param filename
	 *   the name of the file.
	 * @param contentType
	 *   the content type of the attached file.
	 * @param stream
	 *   an input stream from which the file can be read.
	 */
	public HttpFileParameter(String name, String filename, String contentType, InputStream stream) {
		super(Type.FILE, name);
		this.filename = filename;
		this.contentType = contentType;
		this.stream = stream;
	}
	
	/**
	 * Returns the name of the file to send.
	 * 
	 * @return
	 *   the name of the file to send.
	 */
	public String getFileName() {
		return filename;
	}
	
	/**
	 * Returns the content type of the file to send.
	 * 
	 * @return
	 *   the content type of the file to send.
	 */
	public String getContentType() {
		return contentType;
	}	

	/**
	 * Returns the input stream from which the file can be read.
	 * 
	 * @return
	 *   the input stream from which the file can be read.
	 */
	public InputStream getInputStream() {
		return stream;
	}
}
