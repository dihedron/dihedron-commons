/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.os;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An enumeration of all (potentially) supported Java Virtual Machine platforms; 
 * on 32 bits JVMs the platform will always be the 32 bits flavour, no matter 
 * whether the underlying operating system is running on 32 or 64 bits. Thus it 
 * is safe to assume the native operations (e.g. loading a native library into 
 * the JVM) will succeed if the address size of the binary module to load matches 
 * that of the platform, not that of the underlying operating system (e.g. loading
 * a 32 bits DLL on a 32 bits JVM running on Windows 64 bits will succeed).  
 * 
 * @author Andrea Funto'
 */
@License
public enum Platform {
	
	/**
	 * 32-bits Windows, or 32-bits JVM running on 64-bits Windows
	 * operating systems.
	 */
	WINDOWS_32("win32", OperatingSystem.WINDOWS, Addressing.SIZE_32),
	
	/**
	 * 64-bit Windows.
	 */
	WINDOWS_64("win64", OperatingSystem.WINDOWS, Addressing.SIZE_64),  
	
	/**
	 * 32-bit Linux, or 32-bit JVM running on 64-bits Linux operating systems.
	 */
	LINUX_32("lnx32", OperatingSystem.LINUX, Addressing.SIZE_32),
	
	/**
	 * 64-bit Linux.
	 */
	LINUX_64("lnx64", OperatingSystem.LINUX, Addressing.SIZE_64),
	
	/**
	 * 32-bit MacOS X, or 32-bit JVM running on 64-bits MacOS X operating systems.
	 */	
	MACOSX_32("mac32", OperatingSystem.MACOSX, Addressing.SIZE_32),
	
	/**
	 * 64-bit MacOS X.
	 */
	MACOSX_64("mac64", OperatingSystem.MACOSX, Addressing.SIZE_64),
	
	/**
	 * 32-bit Generic UNIX, or 32-bit JVM running on 64-bits UNIX operating systems.
	 */
	UNIX_32("unix32", OperatingSystem.UNIX, Addressing.SIZE_32),
	
	/**
	 * 64-bit generic UNIX.
	 */
	UNIX_64("unix64", OperatingSystem.UNIX, Addressing.SIZE_64);
	
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(Platform.class);
	
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
		logger.trace("detecting current LVM platform based on OS '{}' and architecture '{}'", os, architecture);
		if(os.indexOf("win") >= 0) {
			if("x86".equalsIgnoreCase(architecture) || "i386".equalsIgnoreCase(architecture)) {
				return Platform.WINDOWS_32;
			} else if("x86_64".equalsIgnoreCase(architecture) || "amd64".equalsIgnoreCase(architecture)) {
				return Platform.WINDOWS_64;
			}
		} else if(os.indexOf("nux") >= 0) {
			if("x86".equalsIgnoreCase(architecture) || "i386".equalsIgnoreCase(architecture)) {
				// "x86" for ordinary intel desktop processors; atom reports "i386" instead
				return Platform.LINUX_32;
			} else if("x86_64".equalsIgnoreCase(architecture) || "amd64".equalsIgnoreCase(architecture)) {
				return Platform.LINUX_64;
			}			
		} else if(os.indexOf("mac") >= 0) {
			if("x86".equalsIgnoreCase(architecture) || "i386".equalsIgnoreCase(architecture)) {
				return Platform.MACOSX_32;
			} else if("x86_64".equalsIgnoreCase(architecture) || "amd64".equalsIgnoreCase(architecture)) {
				return Platform.MACOSX_64;
			}			
		} else if(os.indexOf("nix") >= 0) {
			// TODO: this is for fun! x86 on UNIX? are we joking?
			if("x86".equalsIgnoreCase(architecture) || "i386".equalsIgnoreCase(architecture)) {
				return Platform.UNIX_32;
			} else if("x86_64".equalsIgnoreCase(architecture) || "amd64".equalsIgnoreCase(architecture)) {
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
	 * Returns the current operating system.
	 * 
	 * @return
	 *   the current operating system.
	 */
	public OperatingSystem getOperatingSystem() {
		return this.operatingSystem;
	}
	
	/**
	 * Returns the number of bits used by the JVM for addresses; it will
	 * be 32 on 32-bits JVMs, no matter how many bits the underlying 
	 * operating system runs on.
	 * 
	 * @return
	 *   the number of bits used by the JVM for memory addresses.
	 */
	public Addressing getAddressing() {
		return this.addressing;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param code
	 *   a short string representing the platform.
	 * @param operatingSystem
	 *   the operating system flavour.
	 * @param addressing
	 *   the size of the memory addresses (32 or 64 bits).
	 */
	private Platform(String code, OperatingSystem operatingSystem, Addressing addressing) {
		this.code = code;
		this.operatingSystem = operatingSystem;
		this.addressing = addressing;
	}
	
	/**
	 * A short string representing the platform.
	 */
	private String code;
	
	/**
	 * The label for the operating system (as seen by the JVM).
	 */
	private OperatingSystem operatingSystem;
	
	/**
	 * The number of bits used by the JVM architecture (32 or 64).
	 */
	private Addressing addressing;
}
