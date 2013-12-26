/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
 */

package org.dihedron.commons.variables;


/**
 * Returns the value corresponding to the given variable name.
 * 
 * @author Andrea Funto'
 */
public class EnvironmentValueProvider implements ValueProvider {
	
	/**
	 * Returns the value of the given environment variable (if available), null 
	 * otherwise.
	 * 
	 * @see org.dihedron.commons.variables.ValueProvider#onVariable(java.lang.String)
	 */
	@Override
	public String onVariable(String variable) {
		return System.getenv(variable);
	}
}
