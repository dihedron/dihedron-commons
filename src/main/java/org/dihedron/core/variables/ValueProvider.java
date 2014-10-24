/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.variables;

import org.dihedron.core.License;


/**
 * @author Andrea Funto'
 */
@License
public interface ValueProvider {
	
	/**
	 * Given the name of a variable, returns its value if available, null 
	 * otherwise
	 * 
	 * @param variable
	 *   the variable key.
	 * @return
	 *   the variable value if available, null otherwise.
	 */
	String onVariable(String variable);
}
