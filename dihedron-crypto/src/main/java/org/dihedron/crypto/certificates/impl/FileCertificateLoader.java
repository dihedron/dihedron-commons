/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Crypto library ("Crypto").
 *
 * Crypto is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Crypto is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Crypto. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.crypto.certificates.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Properties;

import org.dihedron.crypto.certificates.CertificateLoader;
import org.dihedron.crypto.exceptions.CertificateLoaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCertificateLoader implements CertificateLoader {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileCertificateLoader.class);
	
	private CertificateFactory certificateFactory;

	public FileCertificateLoader() throws CertificateLoaderException {
		try {
			certificateFactory = CertificateFactory.getInstance("X.509");
		} catch (CertificateException e) {
			throw new CertificateLoaderException("error instantiating certificate factory", e);
		}
	}
	
	public byte[] loadCertificateData(String filename) throws CertificateLoaderException {
		Properties properties = new Properties();
		properties.put("filename", filename);
		return loadCertificateData(properties);
	}
	
	public Certificate loadCertificate(String filename) throws CertificateLoaderException {
		Properties properties = new Properties();
		properties.put("filename", filename);
		return loadCertificate(properties);	
	}
	
	//Override
	public Certificate loadCertificate(Properties properties) throws CertificateLoaderException {
		Certificate certificate = null;		
		try {
			byte [] data = loadCertificateData(properties);
			certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(data));
		} catch (CertificateException e) {
			throw new CertificateLoaderException("error parsing certificate from file", e);
		}
		return certificate;
	}
	
	//Override
	public byte[] loadCertificateData(Properties properties)throws CertificateLoaderException {
		byte [] data = null;
		String filename = properties.getProperty("filename");
		if (filename != null) {
			FileInputStream fis = null;
			try {
				File f = new File(filename);
				
				fis = new FileInputStream(f);
				data = new byte[(int)f.length()];
				if(fis.read(data) != (int)f.length()){
					System.err.println("error reading data from file");
					throw new CertificateLoaderException("error reading data from file");
				}
			} catch (FileNotFoundException e) {
				logger.error("file '" + filename + "' not found", e);
				throw new CertificateLoaderException("error loading file " + filename, e);
			} catch (IOException e) {
				logger.error("error reading from file '" + filename + "'", e);
				throw new CertificateLoaderException("error reading certificate from " + filename, e);
			} finally {
				try {
					if(fis != null) {
						fis.close();
					}
				} catch(IOException e) {
					logger.error("error closing input stream", e);
				}
			}
		} else {
			logger.error("no valid filename supplied for certificate"); 
			throw new CertificateLoaderException("no valid filename supplied for certificate");
		}
		return data;
	}

}
