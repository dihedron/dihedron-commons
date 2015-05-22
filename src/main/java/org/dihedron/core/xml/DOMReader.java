/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.dihedron.core.url.URLFactory;
import org.dihedron.patterns.activities.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Andrea Funto'
 */
public final class DOMReader {
	
	/**
	 * The internal DOM parser error handler.
	 * 
	 * @author Andrea Funto'
	 */
	private static class ParserErrorHandler implements ErrorHandler {

		/** 
		 * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
		 */
	    public void warning(SAXParseException e) throws SAXException {
	        logger.warn(e.getMessage(), e);
	    }

	    /**
	     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
	     */
	    public void error(SAXParseException e) throws SAXException {
	        logger.error(e.getMessage(), e);
	    }

	    /**
	     * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
	     */
	    public void fatalError(SAXParseException e) throws SAXException {
	        logger.error(e.getMessage(), e);
	    }
	}	

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(DOMReader.class);
	
	/**
	 * Whether the input XML file should be validated by default.
	 */
	private static final boolean DEFAULT_VALIDATE_XML = false;
	
	/**
	 * Parses an XML document and passes it on to the given handler, or to
	 * a lambda expression for handling; it does not perform validation.
	 * 
	 * @param xml
	 *   the URL of the XML to parse, as a string.
	 * @param xsd
	 *   the URL of the XML's schema definition, as a string.
	 * @param handler
	 *   the handler that will handle the document.
	 * @throws IOException
	 * @throws InvalidArgumentException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws Exception
	 *   any handler-related exceptions.
	 */
	public static void loadDocument(String xml, String xsd, DOMHandler handler) throws IOException, InvalidArgumentException, SAXException, ParserConfigurationException, Exception {
		loadDocument(xml, xsd, handler, DEFAULT_VALIDATE_XML);		
	}

	/**
	 * Parses an XML document and passes it on to the given handler, or to
	 * a lambda expression for handling; it does not perform validation.
	 * 
	 * @param xml
	 *   the URL of the XML to parse.
	 * @param xsd
	 *   the URL of the XML's schema definition.
	 * @param handler
	 *   the handler that will handle the document.
	 * @throws IOException
	 * @throws InvalidArgumentException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws Exception
	 *   any handler-related exceptions.
	 */
	public static void loadDocument(URL xml, URL xsd, DOMHandler handler) throws IOException, InvalidArgumentException, SAXException, ParserConfigurationException, Exception {
		loadDocument(xml, xsd, handler, DEFAULT_VALIDATE_XML);		
	}

	/**
	 * Parses an XML document and passes it on to the given handler, or to
	 * a lambda expression for handling.
	 * 
	 * @param xml
	 *   the URL of the XML to parse, as a string.
	 * @param xsd
	 *   the URL of the XML's schema definition, as a string.
	 * @param handler
	 *   the handler that will handle the document.
	 * @param validate
	 *   whether validation against the given schema should be performed.
	 * @throws IOException
	 * @throws InvalidArgumentException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws Exception
	 *   any handler-related exceptions.
	 */
	public static void loadDocument(String xml, String xsd, DOMHandler handler, boolean validate) throws IOException, InvalidArgumentException, SAXException, ParserConfigurationException, Exception {
		URL xmlUrl = URLFactory.makeURL(xml);
		URL xsdUrl = URLFactory.makeURL(xsd);
		loadDocument(xmlUrl, xsdUrl, handler, validate);		
	}
	
	/**
	 * Parses an XML document and passes it on to the given handler, or to
	 * a lambda expression for handling.
	 * 
	 * @param xml
	 *   the URL of the XML to parse.
	 * @param xsd
	 *   the URL of the XML's schema definition.
	 * @param handler
	 *   the handler that will handle the document.
	 * @param validate
	 *   whether validation against the given schema should be performed.
	 * @throws IOException
	 * @throws InvalidArgumentException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws Exception
	 *   any handler-related exceptions.
	 */
	public static void loadDocument(URL xml, URL xsd, DOMHandler handler, boolean validate) throws IOException, InvalidArgumentException, SAXException, ParserConfigurationException, Exception {
		
		if(xml == null || xsd == null) {
			logger.error("input URLs are not valid");
			throw new InvalidArgumentException("Invalid input URLs");
		}
		
		try(InputStream xmlStream = xml.openStream(); InputStream xsdStream = xsd.openStream()) {
			
			if(xmlStream == null) {
				logger.warn("error opening the input stream from '{}'", xml.toExternalForm());
				throw new InvalidArgumentException("Error opening stream from input URLs");
			}

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(validate);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setNamespaceAware(true);
			
			if(xsdStream == null) {
				logger.warn("error opening the XSD stream from '{}'", xsd.toExternalForm());
			} else {
				logger.trace("XSD stream opened");
				SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schema = schemaFactory.newSchema(new StreamSource(xsdStream));
				factory.setSchema(schema);
			}

			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new ParserErrorHandler());
			
			Document document = builder.parse(xmlStream);
			document.getDocumentElement().normalize();
			
			logger.trace("XML document loaded");
			
			if(handler != null) {
				handler.onDocument(document);
				logger.trace("handler invoked on XML document");
			}
		} catch(DOMHandlerException e) {
			logger.error("error handling DOM document, rethrowing nested exception", e);
			throw (Exception) e.getCause();
		}
	}
}
