/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor;


/**
 * A class representing an object property, a node in the object graph.
 *  
 * @author Andrea Funto'
 */
public interface Node {
	
	/**
	 * Returns the value of the name.
	 *	
	 * @return 
	 *   the name.
	 */
	String getName();

	/**
	 * Returns the value of the property.
	 *	
	 * @return 
	 *   the property value.
	 * @throws VisitorException 
	 */
	Object getValue() throws VisitorException;

	/**
	 * Sets the new value of the property.
	 *	
	 * @param value 
	 *   the value to set.
	 * @throws VisitorException
	 */
	void setValue(Object value) throws VisitorException;
}
