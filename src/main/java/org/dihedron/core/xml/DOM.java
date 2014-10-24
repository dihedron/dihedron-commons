/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.xml;

import java.util.ArrayList;
import java.util.List;

import org.dihedron.core.License;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Funto'
 */
@License
public final class DOM {
	
	public static List<Element> getChildrenByTagName(Document document, String name) {
		List<Element> nodes = new ArrayList<Element>();
		for (Node child = document.getFirstChild(); child != null; child = child.getNextSibling()) {
			if (child.getNodeType() == Node.ELEMENT_NODE && name.equals(child.getNodeName())) {
				nodes.add((Element) child);
			}
		}
		return nodes;		
	}
	
	public static List<Element> getDescendantsByTagName(Document document, String name) {
		List<Element> nodes = new ArrayList<Element>();
		NodeList children = document.getElementsByTagName(name);
		for(int i = 0; i < children.getLength(); ++i) {
			nodes.add((Element)children.item(i));
		}
		return nodes;		
	}
		
	public static List<Element> getChildrenByTagName(Element parent, String name) {
		List<Element> nodes = new ArrayList<Element>();
		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if (child.getNodeType() == Node.ELEMENT_NODE && name.equals(child.getNodeName())) {
				nodes.add((Element) child);
			}
		}
		return nodes;
	}
	
	public static List<Element> getDescendantsByTagName(Element parent, String name) {
		List<Element> nodes = new ArrayList<Element>();
		NodeList children = parent.getElementsByTagName(name);
		for(int i = 0; i < children.getLength(); ++i) {
			nodes.add((Element)children.item(i));
		}
		return nodes;		
	}
	

	public static Element getFirstChildByTagName(Element parent, String name) {
		List<Element> nodes = getChildrenByTagName(parent, name);
		if(!nodes.isEmpty()) {
			return nodes.get(0);
		}
		return null;
	}
	
	public static String getElementText(Element element) {
		if(element != null) {
			return element.getTextContent();
		}
		return null;
	}
	
	/**
	 * Private constructor to prevent utility class instantiation. 
	 */
	private DOM() {
	}
}
