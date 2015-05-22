/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.xml;

import org.w3c.dom.Document;


/**
 * The signature of lambda expressions for handling DOM documents once
 * successfully parsed.
 * 
 * @author Andrea Funto'
 */
public interface DOMHandler {
	/**
	 * Receives and handles the given document.
	 * 
	 * @param document
	 *   the DOM document.
	 * @throws DOMHandlerException
	 *   the generic {@code RuntimeException} subclass, to enable usage 
	 *   with lambda expressions.
	 */
	public void onDocument(Document document) throws DOMHandlerException;
}
