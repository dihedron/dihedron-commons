/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Crypto library ("Crypto").
 *
 * Crypto is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Crypto is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Crypto. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.core.os;

/**
 * An enumeration of all (potentially) supported platforms.
 * 
 * @author Andrea Funto'
 */
public enum Platform {
	
	/**
	 * 32-bits Windows, or 32-bits JVM running on 64-bits Windows
	 * operating systems.
	 */
	WINDOWS_32("win32"),
	
	/**
	 * 64-bit Windows.
	 */
	WINDOWS_64("win64"),  
	
	/**
	 * 32-bit Linux, or 32-bit JVM running on 64-bits Linux
	 * operating systems.
	 */
	LINUX_32("lnx32"),
	
	/**
	 * 64-bit Linux.
	 */
	LINUX_64("lnx64"),
	
	/**
	 * 32-bit MacOS X, or 32-bit JVM running on 64-bits MacOS X
	 * operating systems.
	 */	
	MACOSX_32("mac32"),
	
	/**
	 * 64-bit MacOS X.
	 */
	MACOSX_64("mac64"),
	
	/**
	 * 32-bit Generic UNIX, or 32-bit JVM running on 64-bits UNIX
	 * operating systems.
	 */
	UNIX_32("unix32"),
	
	/**
	 * 64-bit generic UNIX.
	 */
	UNIX_64("unix64");
	
	/**
	 * Tries to detect the current operating infrastructure. Note that this
	 * might be different from the actual operating system, e.g. when running
	 * a 32-bits Java Virtual Machine on a 64-bits operating system: in this
	 * case the 32-bits version of the operating system is reported.
	 * 
	 * @return
	 *   an enumeration value corresponding to the flavour of operating system
	 *   the application is running on.
	 */
	public static Platform getCurrent() {
		String os = System.getProperty("os.name").toLowerCase();
		String architecture = System.getProperty("os.arch");
		if(os.indexOf("win") >= 0) {
			if(architecture.equalsIgnoreCase("x86")) {
				return Platform.WINDOWS_32;
			} else if(architecture.equalsIgnoreCase("x86_64") || architecture.equalsIgnoreCase("amd64")) {
				return Platform.WINDOWS_64;
			}
		} else if(os.indexOf("nux") >= 0) {
			if(architecture.equalsIgnoreCase("x86")) {
				return Platform.LINUX_32;
			} else if(architecture.equalsIgnoreCase("x86_64") || architecture.equalsIgnoreCase("amd64")) {
				return Platform.LINUX_64;
			}			
		} else if(os.indexOf("mac") >= 0) {
			if(architecture.equalsIgnoreCase("x86")) {
				return Platform.MACOSX_32;
			} else if(architecture.equalsIgnoreCase("x86_64") || architecture.equalsIgnoreCase("amd64")) {
				return Platform.MACOSX_64;
			}			
		} else if(os.indexOf("nix") >= 0) {
			if(architecture.equalsIgnoreCase("x86")) {
				return Platform.UNIX_32;
			} else if(architecture.equalsIgnoreCase("x86_64") || architecture.equalsIgnoreCase("amd64")) {
				return Platform.UNIX_64;
			}			
		}
		return null;
	}
	
	/**
	 * Translates the input code (e.g. "win32") into the corresponding
	 * enumeration value.
	 * 
	 * @param code
	 *   an input code for the platform to retrieve.
	 * @return
	 *   the corresponding enumeration values if found, {@code null} otherwise.
	 */
	public static Platform fromString(String code) {
		if(code != null) {
			for(Platform platform : Platform.values()) {
				if(code.equalsIgnoreCase(platform.code)) {
					return platform;
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns a short description (the "code") for the given enumeration 
	 * value. 
	 */
	@Override
	public String toString() {
		return code;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param code
	 *   a short string representing the platform.
	 */
	private Platform(String code) {
		this.code = code;
	}
	
	/**
	 * A short string representing the platform.
	 */
	private String code;
}
