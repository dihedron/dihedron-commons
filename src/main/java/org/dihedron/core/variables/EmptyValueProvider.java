/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.variables;


/**
 * This value provider can be used as a fallback mechanism to replace unbound 
 * variables with the empty string.
 *  
 * @author Andrea Funto'
 */
public class EmptyValueProvider implements ValueProvider {

	/**
	 * Returns the empty string for all variables.
	 * 
	 * @see org.dihedron.core.variables.ValueProvider#onVariable(java.lang.String)
	 */
	@Override
	public String onVariable(String variable) {
		return "";
	}
}
