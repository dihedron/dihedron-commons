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
 * Returns the system property corresponding to the given variable name.
 * 
 * @author Andrea Funto'
 */
public class KnownVariablesValueProvider implements ValueProvider {
	
	/**
	 * Returns the value of the system property or environment variable corresponding 
	 * to the given variable name, {@code null} if not matching any of the known
	 * variables.
	 * 
	 * @see org.dihedron.commons.variables.ValueProvider#onVariable(java.lang.String)
	 */
	@Override
	public String onVariable(String variable) {
		if(variable.equals("USERHOME")) {
			return System.getProperty("user.home");
		} else if(variable.equals("WINDIR")) {
			return System.getenv("windir");
		} else if(variable.equals("SYSTEMROOT")) {
			return System.getenv("SystemRoot");
		} else if(variable.equals("COMMONPROGRAMFILES")) {
			return System.getenv("CommonProgramFiles");
		} else if(variable.equals("PROGRAMFILES")) {
			return System.getenv("ProgramFiles");
		} else if(variable.equals("TEMP")) {
			return System.getenv("TEMP");
		} else if(variable.equals("SYSTEMDRIVE")) {
			return System.getenv("SystemDrive");
		} else if(variable.equals("USERPROFILE")) {
			return System.getenv("USERPROFILE");
		} else if(variable.equals("APPDATA")) {
			return System.getenv("APPDATA");
		}
		return null;
	}
}
