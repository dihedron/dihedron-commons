/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.variables;

import java.util.Map;

/**
 * @author Andrea Funto'
 */
public class MapBasedValueProvider implements ValueProvider {

	/**
	 * The map of supported variables keys, along with their values.
	 */
	private Map<String, Object> variables;
	
	/**
	 * Constructor.
	 * 
	 * @param variables
	 *   the map of supported variables keys, along with their values.
	 */
	public MapBasedValueProvider(Map<String, Object> variables) {
		this.variables = variables; 
	}

	/**
	 * Returns the String representation of the value corresponding to the given 
	 * key in the map (if available), null otherwise.
	 * 
	 * @see org.dihedron.core.variables.ValueProvider#onVariable(java.lang.String)
	 */
	@Override
	public String onVariable(String variable) {
		String value = null;
		if(variables != null && variables.containsKey(variable)) {
			Object val = variables.get(variable);
			value = (val != null) ? val.toString() : null;
		}
		return value;
	}
}
