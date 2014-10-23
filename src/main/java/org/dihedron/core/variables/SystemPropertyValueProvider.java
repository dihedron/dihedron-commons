/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.variables;


/**
 * Returns the system property corresponding to the given variable name.
 * 
 * @author Andrea Funto'
 */
public class SystemPropertyValueProvider implements ValueProvider {
	
	/**
	 * Returns the value of the given system property (if available), null 
	 * otherwise.
	 * 
	 * @see org.dihedron.core.variables.ValueProvider#onVariable(java.lang.String)
	 */
	@Override
	public String onVariable(String variable) {
		return System.getProperty(variable);
	}
}
