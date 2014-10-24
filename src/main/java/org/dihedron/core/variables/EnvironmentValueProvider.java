/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.variables;

import org.dihedron.core.License;


/**
 * Returns the value corresponding to the given variable name.
 * 
 * @author Andrea Funto'
 */
@License
public class EnvironmentValueProvider implements ValueProvider {
	
	/**
	 * Returns the value of the given environment variable (if available), null 
	 * otherwise.
	 * 
	 * @see org.dihedron.core.variables.ValueProvider#onVariable(java.lang.String)
	 */
	@Override
	public String onVariable(String variable) {
		return System.getenv(variable);
	}
}
