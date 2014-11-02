/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.zip;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Provides operations on a ZIP archive.
 * 
 * @author Andrea Funto'
 */
@License
public class ZipArchive {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ZipArchive.class);

	/**
	 * Creates a new object, creating a ZipOutputStream on the
	 * provided OutputStream (be it a BufferedOutputStream a 
	 * FileOutputStream or any other kind of output stream).
	 * 
	 * @param os
	 *   The OutputStream to create the ZipOutputStream on.
	 */
	public ZipArchive(OutputStream os) {
		if(os instanceof ZipOutputStream) {
			logger.debug("using an existing ZipOutputStream");
			stream = (ZipOutputStream)os;
		} else {
			logger.debug("wrapping an OutputStream in a new ZipOutputStream");
			stream = new ZipOutputStream(os);
		}
	}	
	
	/**
	 * Creates a new ZIP archive, backed up by the file passed in as input 
	 * parameter.
	 * 
	 * @param pathname 
	 *   the path of the file backing up the ZIP archive.
	 * @throws FileNotFoundException  
	 */
	public ZipArchive(String pathname) throws FileNotFoundException  {
		this(new File(pathname));
	}
	
	/**
	 * Creates a new ZIP archive, backed up by the given File object.
	 * 
	 * @param file
	 *   The File object.
	 * @throws FileNotFoundException
	 */
	public ZipArchive(File file) throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(file);
		stream = new ZipOutputStream( new BufferedOutputStream(fos) );
	}
	
	/**
	 * Closes the underlying ZipOutputStream.
	 */
	public void close() throws IOException {
		stream.finish();
		stream.flush();
		stream.close();
	}
 
	/**
	 * Adds a file to the ZIP archive, given its content as an array of bytes.
	 * 
	 * @param zipEntry 
	 *   name of the entry in the archive.
	 * @param data 
	 *   data bytes to be stored as file contents in the archive.
	 */
	public void addFile(String zipEntry, byte[] data) throws IOException  {
		stream.putNextEntry(new ZipEntry(zipEntry));
		stream.write(data);
	}
	
	/**
	 * Adds the contents of a ByteArrayOutputStream to the ZIP archive.
	 * 
	 * @param zipEntry 
	 *   name of the entry in the archive.
	 * @param baos 
	 *   ByteArrayOutputStream to be added to the archive.
	 */
	public void addFile(String zipEntry, ByteArrayOutputStream baos) throws IOException {
		addFile(zipEntry, baos.toByteArray());
	}

	/**
	 * Adds a file to a ZIP archive.
	 * 
	 * @param zipEntry
	 *   the name of entry in the archive; if null, the name of the original 
	 *   file is used.
	 * @param file 
	 *   File object representing the file to be added to the archive.
	 */
	public void addFile(String zipEntry, File file) throws IOException {
		String zipEntryName;
		if(zipEntry == null || zipEntry.trim().length() == 0) {
			zipEntryName = file.getName();
		} else {
			zipEntryName = zipEntry;
		}
		logger.debug("adding " + file.getName() + " as " + zipEntryName);
		long size = file.length();
		byte[] data = new byte[(int)size];
		FileInputStream fis = new FileInputStream(file);
		if(fis.read(data) != size) {
			logger.error("error reading data into buffer");
		}
		fis.close();
		
		addFile(zipEntryName, data);
	}

	/**
	 * Adds a file to a ZIP archive, given its path.
	 * 
	 * @param zipEntry
	 *   the name of the entry in the archive; if null, the name of the original 
	 *   file is used.
	 * @param filename 
	 *   The path of a file to be added to the ZIP archive.
	 */
	public void addFile(String zipEntry, String filename) throws IOException {
		addFile(zipEntry, new File(filename));
	}

	/**
	 * Adds multiple entries to the ZIP archive, given their names and 
	 * data in a map. The data can be stored as a byte array, as a 
	 * File object, as a ByteArrayOutputStream or as a String (the file 
	 * path), and even be of mixed types: the method will automatically 
	 * switch to the most proper behaviour depending on the object type.
	 *  
	 * @param zipEntries
	 *   A Map containing zipEntry names and data.
	 * @throws IOException 
	 */
	public void addFiles(Map<String, Object> zipEntries) throws IOException {
		logger.debug("adding {} entries to the ZIP file", zipEntries.size());
		for (String zipEntry : zipEntries.keySet()) {
			Object value = zipEntries.get(zipEntry);
			if(value instanceof byte[]) {
				logger.debug("adding '{}' defined as byte[]", zipEntry);
				addFile(zipEntry, (byte[])value);
			} else if(value instanceof ByteArrayOutputStream) {
				logger.debug("adding '{}', defined as ByteArrayOutputStream", zipEntry);
				addFile(zipEntry, (ByteArrayOutputStream)value);
			} else if(value instanceof File) {
				logger.debug("adding '{}', defined as File", zipEntry);
				addFile(zipEntry, (File)value);
			} else if(value instanceof String) {
				logger.debug("adding '{}', defined as String (path)", zipEntry);
				addFile(zipEntry, (String)value);
			} else {
				logger.error("unsupported data type for {}", zipEntry);
			}
		}
	}
	
	/**
	 * Adds multiple files to the ZIP archive, given their names.
	 * @param files
	 *   The File objects representing the files.
	 */
	public void addFiles(File[] files) throws IOException {
		for (int i = 0; i < files.length; i++) {
			addFile(null, files[i]);
		}
	}

	/**
	 * Adds multiple files to the ZIP archive, given their names.
	 * @param filenames
	 *   The Strings representing eth file names.
	 */
	public void addFiles(String[] filenames) throws IOException {
		for (int i = 0; i < filenames.length; i++) {
			addFile(null, filenames[i]);
		}
	}

	/**
	 * Adds a text file (encoded as a list of strings) to the archive.
	 * @param zipEntry 
	 *   New entry name in the archive.
	 * @param text 
	 *   A list of Strings to be added as a text file to the archive.
	 * @return
	 */
	public void addTextFile(String zipEntry, String[] text) throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(baos);
		for (int i = 0; i < text.length; i++) {
			// add the line
			writer.write(text[i] + "\n");
		}
		writer.close();
		addFile(zipEntry, baos);
	}
	
	/**
	 * The underlying ZipOutputStream.
	 */
	private ZipOutputStream stream = null;
}
