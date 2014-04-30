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

package org.dihedron.crypto.providers.smartcard.discovery;



import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.dihedron.core.streams.Streams;
import org.dihedron.core.xml.DOM;
import org.dihedron.crypto.exceptions.SmartCardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The class responsible of loading the database of supported smart cards, 
 * along with info about their their supporting PKCS#11 drivers.
 * 
 * @author Andrea Funto'
 */
public final class DataBaseLoader {
	
	/**
	 * The internal SAX event handling class.
	 * 
	 * @author Andrea Funto'
	 */
	private static class ConfigurationErrorHandler implements ErrorHandler {
		
		/**
		 * Callback for warnings emitted while parsing the XML.
		 */
	    public void warning(SAXParseException e) throws SAXException {
	        logger.warn(e.getMessage(), e);
	    }

	    /**
	     * Callback for errors emitted while parsing the XML.
	     */
	    public void error(SAXParseException e) throws SAXException {
	        logger.error(e.getMessage(), e);
	    }

	    /**
	     * Callback for unrecoverable errors emitted while parsing the XML.
	     */
	    public void fatalError(SAXParseException e) throws SAXException {
	        logger.error(e.getMessage(), e);
	    }
	}	

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataBaseLoader.class);
	
	/**
	 * Whether the input XML file should be validated.
	 */
	private static final boolean VALIDATE_XML = false;
	
	/**
	 * The name of the smart cards database schema.
	 */
	public static final String SMARTCARDS_XSD = "org/dihedron/crypto/providers/smartcard/discovery/smartcards.xsd";
	
	/**
	 * The name of the file containing the default smart cards database.
	 */
	public static final String DEFAULT_SMARTCARDS_XML = "org/dihedron/crypto/providers/smartcard/discovery/smartcards.xml";
		
	/**
	 * Loads the default smart cards database from the class path.
	 * 
	 * @return
	 *   a smart card database loaded with the default information from the JAR.
	 * @throws IOException
	 * @throws SmartCardException
	 */
	public static DataBase loadDefault() throws IOException, SmartCardException {
		return loadFromStream(Streams.fromClassPath(DEFAULT_SMARTCARDS_XML));
	}
	
//	/**
//	 * Initialises the database by parsing the input configuration file as read 
//	 * from the file-system.
//	 * 
//	 * @param filepath
//	 *   the path to the input configuration file on the file-system.
//	 * @throws IOException 
//	 * @throws SmartCardException 
//	 */
//	public static DataBase loadFromFileSystem(String filepath) throws IOException, SmartCardException {
//		InputStream stream = Streams.fromFile(filepath);
//		return loadFromStream(stream);
//	}
//	
//	/**
//	 * Initialises the configuration by parsing the input configuration file
//	 * as read from the file-system.
//	 * 
//	 * @param file
//	 *   the <code>File</code> object representing the configuration file on
//	 *   the file-system.
//	 * @throws IOException 
//	 * @throws SmartCardException
//	 */
//	public static DataBase loadFromFileSystem(File file) throws SmartCardException, IOException {
//		InputStream stream = Streams.fromFile(file);
//		return loadFromStream(stream);
//	}
//	
//	/**
//	 * Initialises the configuration by parsing the input configuration file
//	 * as read from the classpath.
//	 * 
//	 * @param path
//	 *   the path to the resource, to be located on the classpath.
//	 * @throws SmartCardException
//	 */
//	public static DataBase loadFromClassPath(String path) throws SmartCardException {
//		InputStream stream = Streams.fromClassPath(path);
//		return loadFromStream(stream);
//	}
//	
	/**
	 * Initialises the database by parsing the XML file passed in as an input 
	 * stream.
	 * 
	 * @param input
	 *   the database file as a stream; the stream will always be closed 
	 *   by the time the method returns. If the stream is null, the method
	 *   exits immediately without any complaint, in order to make smartcards'
	 *   loading optional.
	 * @throws SmartCardException
	 */
	public static DataBase loadFromStream(InputStream input) throws SmartCardException {
		
		if(input == null) {
			logger.warn("invalid input stream");
			return null;
		}
		InputStream stream = input; 
		
		InputStream xsd = null;
		try {
		
			DataBase database = new DataBase();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(VALIDATE_XML);
			factory.setNamespaceAware(true);

			xsd = Streams.fromClassPath(SMARTCARDS_XSD);
			if(xsd == null) {
				logger.warn("error loading XSD for smartcards database");
			} else {
				logger.trace("XSD for smartcards database loaded");
				SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
				factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource(xsd)}));
			}

			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new ConfigurationErrorHandler());
			
			Document document = builder.parse(stream);
			document.getDocumentElement().normalize();
			
			for(Element sc : DOM.getDescendantsByTagName(document, "smartcard")) {
				ATR atr = new ATR(sc.getAttribute("atr"));
				SmartCard smartcard = new SmartCard(atr);
				smartcard.setDescription(DOM.getElementText(DOM.getFirstChildByTagName(sc, "description")));
				smartcard.setManufacturer(DOM.getElementText(DOM.getFirstChildByTagName(sc, "manufacturer")));				
				
				for(Element dr : DOM.getDescendantsByTagName(sc, "driver")) {
					Driver driver = new Driver(dr.getAttribute("platform"));
					for(Element path : DOM.getDescendantsByTagName(dr, "path")) {
						driver.addPath(DOM.getElementText(path));
					}
					smartcard.addDriver(driver);
				}				
				database.put(atr, smartcard);
			}
			logger.info("database loaded");
			return database;
		} catch (Exception e) {
			logger.error("error parsing input XML database", e);
			throw new SmartCardException("Error parsing input XML database", e);
		} finally {
			if(stream != null) {
				try {
					stream.close();
					stream = null;
				} catch(IOException e) {
					throw new SmartCardException("Error closing XML database stream", e);
				}
			}
			if(xsd != null) {
				try {
					xsd.close();
					xsd = null;
				} catch(IOException e) {
					throw new SmartCardException("Error closing XSD database stream", e);
				}				
			}
		}
	}
}
