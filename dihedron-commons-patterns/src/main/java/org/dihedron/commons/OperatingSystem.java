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
package org.dihedron.commons;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains utility methods for inspecting operating system properties. 
 * In a future version it may rely on native methods for some of its queries.
 * 
 * @author Andrea Funto'
 */
public final class OperatingSystem {
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(OperatingSystem.class);
	
	/**
	 * The name of the system property holding the operating system architecture.
	 */
	private static final String OS_ARCH = "os.arch";

	/**
	 * The name of the system property holding the name of the operating system.
	 */
	private static final String OS_NAME = "os.name";
	
	/** 
	 * 32 bits Windows label. 
	 */
	public static final String OS_WIN32 = "win32";
	
	/** 
	 * 64 bits Windows label. 
	 */
	public static final String OS_WIN64 = "win64";
	
	/** 
	 * 32 bits Linux label. 
	 */
	public static final String OS_LNX32 = "lnx32";
	
	/** 
	 * 64 bits Linux label. 
	 */
	public static final String OS_LNX64 = "lnx64";
	
	/** 
	 * 32 bits Apple label. 
	 */
	public static final String OS_MAC32 = "mac32";
	
	/** 
	 * 64 bits Apple label. 
	 */
	public static final String OS_MAC64 = "mac64";
	
	/**
	 * Returns the operating system name.
	 * 
	 * @return
	 *   the operating system name.
	 */
	public static String getName() {
		return System.getProperty(OS_NAME);
	}
	
	/**
	 * Returns the operating system architecture.
	 * 
	 * @return
	 *   the operating system architecture
	 */
	public static String getArchitecture() {
		return System.getProperty(OS_ARCH);
	}

	/**
	 * Returns whether it is a Windows machine 
	 * (regardless of the architecture).
	 * 
	 * @return
	 *   whether it is a Windows machine.
	 */
	public static boolean isWindows() {
		String os = System.getProperty(OS_NAME).toLowerCase();
		return (os.indexOf("win") >= 0);
	}

	/**
	 * Returns whether it is a Apple machine 
	 * (regardless of the architecture).
	 * 
	 * @return
	 *   whether it is a Apple machine.
	 */
	public static boolean isMac() {
		String os = System.getProperty(OS_NAME).toLowerCase();
		return (os.indexOf("mac") >= 0);
	}

	/**
	 * Returns whether it is a Unix machine 
	 * (regardless of the architecture).
	 * 
	 * @return
	 *   whether it is a Unix machine.
	 */
	public static boolean isUnix() {
		String os = System.getProperty(OS_NAME).toLowerCase();
		return (os.indexOf("nix") >= 0);
	}
	
	/**
	 * Returns whether it is a Linux machine 
	 * (regardless of the architecture).
	 * 
	 * @return
	 *   whether it is a Linux machine.
	 */
	public static boolean isLinux() {
		String os = System.getProperty(OS_NAME).toLowerCase();
		return (os.indexOf("nux") >= 0);
	}

	/**
	 * Returns whether it is a Linux/Unix machine 
	 * (regardless of the architecture).
	 * 
	 * @return
	 *   whether it is a Linux/Unix machine.
	 */
	public static boolean isUnixOrLinux() {
		String os = System.getProperty(OS_NAME).toLowerCase();
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
	}
	
	/**
	 * Checks whether the underlying platform is a 32 
	 * bits Windows machine.
	 * 
	 * @return
	 *   whether the underlying platform is a 32 bits
	 *   Windows machine.
	 */
	public static boolean isWindows32() {
		if(isWindows()) {
			String architecture = System.getProperty(OS_ARCH);
			if(architecture.equalsIgnoreCase("x86")) {
				logger.trace("running on Windows, 32 bits");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the underlying platform is a 64 bits Windows machine.
	 * 
	 * @return
	 *   whether the underlying platform is a 64 bits Windows machine.
	 */
	public static boolean isWindows64() {
		if(isWindows()) {
			String architecture = System.getProperty(OS_ARCH);
			if(architecture.equalsIgnoreCase("x86_64") || architecture.equalsIgnoreCase("amd64")) {
				logger.trace("running on Windows, 64 bits");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the underlying platform is a 32 bits Linux machine.
	 * 
	 * @return
	 *   whether the underlying platform is a 32 bits Linux machine.
	 */
	public static boolean isLinux32() {
		if(isLinux()) {
			String architecture = System.getProperty(OS_ARCH);
			if(architecture.equalsIgnoreCase("x86") || architecture.equalsIgnoreCase("i386")) {
				logger.trace("running on Linux, 32 bits");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the underlying platform is a 64 bits Linux machine.
	 * 
	 * @return
	 *   whether the underlying platform is a 64 bits Linux machine.
	 */
	public static boolean isLinux64() {
		if(isLinux()) {
			String architecture = System.getProperty(OS_ARCH);
			if(architecture.equalsIgnoreCase("x86_64") || architecture.equalsIgnoreCase("amd64")) {
				logger.trace("running on Linux, 64 bits");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the underlying platform is a 32 bits Apple machine.
	 * 
	 * @return
	 *   whether the underlying platform is a 32 bits Apple machine.
	 */
	public static boolean isMac32() {
		if(isMac()) {
			String architecture = System.getProperty(OS_ARCH);
			if(architecture.equalsIgnoreCase("x86") || architecture.equalsIgnoreCase("i386")) {
				logger.trace("running on Mac, 32 bits");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the underlying platform is a 64 
	 * bits Apple machine.
	 * 
	 * @return
	 *   whether the underlying platform is a 64 bits
	 *   Apple machine.
	 */
	public static boolean isMac64() {
		if(isMac()) {
			String architecture = System.getProperty(OS_ARCH);
			if(architecture.equalsIgnoreCase("x86_64") || architecture.equalsIgnoreCase("amd64")) {
				logger.trace("running on Mac, 64 bits");
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * Returns a short string representation of the
	 * operating system label.
	 *  
	 * @return
	 *   a short label representing the underlying
	 *   operating system, or null if unsupported.
	 */
	public static String getOperatingSystemLabel() {
		if(isWindows32()) {
			return OS_WIN32;
		} else if(isWindows64()) {
			return OS_WIN64;
		} else if(isLinux32()) {
			return OS_LNX32;
		} else if(isLinux64()) {
			return OS_LNX64;
		} else if(isMac32()) {
			return OS_MAC32;
		} else if(isMac64()) {
			return OS_MAC64;
		}
		return null;
	}
	
	/**
	 * Returns the user's home directory.
	 * 
	 * @return
	 *   the user's home directory.
	 */
	public static File getUserHome() {
		String userHome = System.getProperty("user.home");
		return new File(userHome);
	}
	
	/**
	 * Returns the <code>File</code> object representing the application data
	 * directory under the user's profile, if it exists.
	 * 
	 * @return
	 *   the directory in which application data can be stored.
	 */
	public static File getApplicationDataDirectory() {
		String appData = System.getenv("APPDATA");
		if(isDirectory(appData)) {
			return new File(appData); 
		}
		return null;
	}
	
	public static String resolveSystemProperty(String path, String userVariable, String systemProperty) {
		String resolvedPath = path;
		if (resolvedPath == null || resolvedPath.length() == 0) {
			logger.error("invalid input path");
			return null;
		}
		
		if (userVariable == null || userVariable.length() == 0) {
			logger.error("invalid user variable (to be replaced)");
			return null;
		}

		if (systemProperty == null || systemProperty.length() == 0) {
			logger.error("invalid system property (used as replacement)");
			return null;
		}
		
		if (resolvedPath.matches(".*\\$\\{" + userVariable + "\\}.*")) {
			logger.debug("replacing user variable ${{}} with system property value: '{}'", userVariable, systemProperty);
			String property = System.getProperty(systemProperty);
			if(property != null) {
				property = property.replaceAll("\\\\", "\\\\\\\\");
			}
			resolvedPath = resolvedPath.replaceAll("\\$\\{" + userVariable + "\\}", property);			
		}
		return resolvedPath;
	}
	
	public static String resolveEnvironmentVariable(String path, String userVariable, String environmentVariable) {
		String resolvedPath = path;
		if (resolvedPath == null || resolvedPath.length() == 0) {
			logger.error("invalid input path");
			return null;
		}
		
		if (userVariable == null || userVariable.length() == 0) {
			logger.error("invalid user variable (to be replaced)");
			return null;
		}

		if (environmentVariable == null || environmentVariable.length() == 0) {			
			logger.error("invalid environment variable (used as replacement)");
			return null;
		}
		
		if (resolvedPath.matches(".*\\$\\{" + userVariable + "\\}.*")) {
			logger.debug("replacing user variable ${{}} with environment variable value: '{}'", userVariable, environmentVariable);
			String property = getEnvironmentVariable(environmentVariable);
			if(property != null) {
				property = property.replaceAll("\\\\", "\\\\\\\\");
				resolvedPath = resolvedPath.replaceAll("\\$\\{" + userVariable + "\\}", property);
			}			
		}
		return resolvedPath;
	}
	

	public static String resolveKnownPathVariables(String path) {
		String variable = path;
		variable = resolveSystemProperty(variable, "USERHOME", "user.home");
		variable = resolveEnvironmentVariable(variable, "WINDIR", "windir");
		variable = resolveEnvironmentVariable(variable, "SYSTEMROOT", "SystemRoot");
		variable = resolveEnvironmentVariable(variable, "COMMONPROGRAMFILES", "CommonProgramFiles");
		variable = resolveEnvironmentVariable(variable, "PROGRAMFILES", "ProgramFiles");
		variable = resolveEnvironmentVariable(variable, "TEMP", "TEMP");
		variable = resolveEnvironmentVariable(variable, "SYSTEMDRIVE", "SystemDrive");
		variable = resolveEnvironmentVariable(variable, "USERPROFILE", "USERPROFILE");

		return variable;
	}

	public static boolean isDirectory(File file) {
		return file != null && file.exists() && file.isDirectory();
	}
	
	public static boolean isDirectory(String path) {
		if(path != null && path.trim().length() > 0) {
			return isDirectory(new File(path.trim()));
		} 
		return false;
	}

	public static boolean isRegularFile(File file) {
		return file != null && file.exists() && file.isFile();
	}
	
	public static boolean isRegularFile(String path) {
		if(path != null && path.trim().length() > 0) {
			return isRegularFile(new File(path.trim()));
		} 
		return false;
	}
	
	public static Map<String, String> getSystemProperties() {
		Map<String, String> result = new HashMap<String, String>();
		Properties properties = System.getProperties();
		for(Entry<Object, Object> entry : properties.entrySet()) {
			result.put((String)entry.getKey(), (String)entry.getValue());
			logger.debug("system property:" + (String)entry.getKey() + ":=" + (String)entry.getValue());			
		}
		return result;		
	}
	
	
	public static String getEnvironmentVariable(String variableName) {
		return System.getenv(variableName);
	}
	
	/**
	 * Private constructor to prevent instantiation.
	 */
	private OperatingSystem() {
	}
}
