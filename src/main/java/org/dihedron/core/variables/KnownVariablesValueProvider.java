/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.variables;


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
	 * @see org.dihedron.core.variables.ValueProvider#onVariable(java.lang.String)
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
