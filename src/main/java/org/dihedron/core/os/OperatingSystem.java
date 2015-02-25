/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.os;

import org.dihedron.core.License;


/**
 * The set of supported operating systems.
 * 
 * @author Andrea Funto'
 */
@License
public enum OperatingSystem {
	/**
	 * The UNIX System-V operating system; this constants is often
	 * used when no further indication is provided (e.g it may be
	 * used on Linux machines).
	 */
	SYSTEM_V("System V"),
	
	/**
	 * The HP-UX operating system.
	 */
	HPUX("HP-UX"),
	
	/**
	 * The Linux operating system.
	 */
	LINUX("Linux"),
	
	/**
	 * The Oracle/Sun Solaris operating system.
	 */
	SOLARIS("Sun Solaris"),
	
	/**
	 * The IBM AIX operating system.
	 */
	AIX("IBM AIX"),
	
	/**
	 * The Irix operating system.
	 */
	IRIX("Irix"),
	
	/**
	 * The FreeBSD operating system.
	 */
	FREEBSD("FreeBSD"),
	
	/**
	 * The NetBSD operating system.
	 */
	NETBSD("NetBSD"),

	/**
	 * The OpenBSD operating system.
	 */
	OPENBSD("OpenBSD"),
	
	/**
	 * The Microsoft Windows operating system.
	 */
	WINDOWS("Microsoft Windows"),
	
	/**
	 * The Apple MacOS X operating system.
	 */
	MACOSX("Apple MacOS X"),
	
	/**
	 * A generic UNIX operating system.
	 */
	UNIX("UNIX");
	
	/**
	 * Constructor.
	 *
	 * @param label
	 *   the textual label for the given operating system.
	 */
	private OperatingSystem(String label) {
		this.label = label;
	}
	
	/**
	 * Returns the enumeration value as a string (its associated label).
	 * 
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return this.label;
	}
	
	/**
	 * A textual laabel for the operating system.
	 */
	private String label;
}
