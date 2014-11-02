/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.variables;

import org.dihedron.core.License;


/**
 * Returns the system property corresponding to the given variable name.
 * 
 * @author Andrea Funto'
 */
@License
public class KnownVariablesValueProvider implements ValueProvider {
	
	/**
	 * Returns the value of the system property or environment variable corresponding 
	 * to the given variable name, {@code null} if not matching any of the known
	 * variables.
	 * 
	 * @see org.dihedron.core.variables.ValueProvider#onVariable(java.lang.String)
	 */
	@Override
	public String onVariable(String variable) {
		switch(variable) {
		case "USERHOME":
			return System.getProperty("user.home");
		case "WINDIR":
			return System.getenv("windir");
		case "SYSTEMROOT":
			return System.getenv("SystemRoot");
		case "COMMONPROGRAMFILES":
			return System.getenv("CommonProgramFiles");
		case "PROGRAMFILES":
			return System.getenv("ProgramFiles");
		case "TEMP":
			return System.getenv("TEMP");
		case "SYSTEMDRIVE":
			return System.getenv("SystemDrive");
		case "USERPROFILE":
			return System.getenv("USERPROFILE");
		case "APPDATA":
			return System.getenv("APPDATA");
		}
		return null;
	}
}
