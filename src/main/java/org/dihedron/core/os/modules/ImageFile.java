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
package org.dihedron.core.os.modules;

import java.io.File;

/**
 * A class representing an image file.
 * 
 * @author Andrea Funto'
 */
public class ImageFile {

	/**
	 * The format of executable image.
	 * 
	 * @author Andrea Funto'
	 */
	public enum Format {
		
		/**
		 * The Extensible Linker Format (ELF).
		 */
		ELF("Extensible Linker Format (ELF)"),
		
		/**
		 * The Portable Executable (PE) format.
		 */
		PE("Portable Executable (PE)"),
		
		/**
		 * The MacOS X format.
		 */
		MACH_O("Mach Object (Mach-O)");
		
		/**
		 * Constructor.
		 *
		 * @param label
		 *   a user-friendly label for the binary module format.
		 */
		private Format(String label) {
			this.label = label;
		}
		
		/**
		 * Returns a user-friendly representation of the binary module format.
		 * 
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return label;
		}
		
		/**
		 * A user-friendly label for the binary module format.
		 */
		private String label;		
	}
	
	/**
	 * The number of bits used for internal memory addressing by the processor.
	 *  
	 * @author Andrea Funto'
	 */
	public enum Addressing {
		
		/**
		 * Memory addresses are 32-bits wide. 
		 */
		SIZE_32("32 bits"),
		
		/**
		 * Memory addresses are 64-bits wide.
		 */
		SIZE_64("64 bits");
		
		/**
		 * Constructor.
		 *
		 * @param label
		 *   a user-friendly label for the number of bits.
		 */
		private Addressing(String label) {
			this.label = label;
		}
		
		/**
		 * Returns a user-friendly representation of the number of bits.
		 * 
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return label;
		}
		
		/**
		 * A user-friendly label for the number of bits.
		 */
		private String label;
	}
	
	/**
	 * The "endianness" of the architecture.
	 * 
	 * @author Andrea Funto'
	 */
	public enum Endianness {
		
		/**
		 * The architecture is little-endian.
		 */
		LITTLE_ENDIAN("little endian"),
		
		/**
		 * the architecture is big-endian.
		 */
		BIG_ENDIAN("big endian");
		
		/**
		 * Constructor.
		 *
		 * @param label
		 *   a user-friendly label for the endianness.
		 */
		private Endianness(String label) {
			this.label = label;
		}
		
		/**
		 * Returns a user-friendly representation of the endianness.
		 * 
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return label;
		}
		
		/**
		 * A user-friendly label for the endianness.
		 */
		private String label;		
	}
	
	/**
	 * The set of supported operating systems.
	 * 
	 * @author Andrea Funto'
	 */
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
		 * The NetBSD operating system.
		 */
		NETBSD("NetBSD"),
		
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
		MACOSX("Apple MacOS X");
		
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
	
	/**
	 * The target instruction set for the binary module.
	 *  
	 * @author Andrea Funto'
	 */
	public enum InstructionSet {
		/**
		 * The SPARC instruction set.
		 */
		SPARC,
		
		/**
		 * The intel 32-bits instruction set.
		 */
		X86,
		
		/**
		 * The MIPS instruction set.
		 */
		MIPS,
		
		/**
		 * The PowerPC instruction set.
		 */
		POWERPC,
		
		/**
		 * The ARM instruction set.
		 */
		ARM,
		
		/**
		 * The SuperH instruction set.
		 */
		SUPERH,
		
		/**
		 * The Intel IA-64 instruction set.
		 */
		IA_64,
		
		/**
		 * The AMD 64-bits instruction set.
		 */
		X86_64,
		
		/**
		 * The AArch64 instruction set.
		 */
		AARCH64
	}
	
	/**
	 * The type of executable object.
	 *  
	 * @author Andrea Funto'
	 */
	public enum Type {
		/**
		 * A relocatable object, e.g. a compiled library (".a") 
		 */
		RELOCATABLE,
		
		/**
		 * An executable, e.g. an application main binary.
		 */
		EXECUTABLE,
		
		/**
		 * A shared object (".so").
		 */
		SHARED,
		
		/**
		 * A kernel module (".ko").
		 */
		CORE		
	}

	/**
	 * The file system path of the physical image.
	 */
	private File file;
	
	/**
	 * The format of executable image.
	 */
	private Format format;
	
	/**
	 * The size of memory addresses (as part of the processor/JVM architecture.
	 */
	private Addressing addressing; 
	
	/**
	 * The "endianness" of the architecture.
	 */
	private Endianness endianness;
	
	/**
	 * The operating system family.
	 */
	private OperatingSystem operatingSystem;
	
	/**
	 * The type of binary object (an executable, a shared object...).
	 */
	private Type type;
	
	/**
	 * The target instruction set.
	 */
	private InstructionSet instructionSet;
	
	/**
	 * Constructor.
	 */
	public ImageFile() {
	}
	
	/**
	 * Constructor.
	 *
	 * @param path
	 *   the file system path of the underlying image file.
	 */
	public ImageFile(String path) {
		setFile(new File(path));
	}
	
	/**
	 * Constructor.
	 * 
	 * @param file
	 *   the underlying image file.
	 */
	public ImageFile(File file) {
		setFile(file);
	}
	
	/**
	 * Returns a JSON representation of the object.
	 * 
	 * @return
	 *   a JSON representation of the object.
	 */
	public String toJSON() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("{\n");
		buffer.append("\tfile: \'").append(file.getAbsolutePath()).append("',\n");
		buffer.append("\tformat: \'").append(format).append("',\n");
		buffer.append("\taddressing: \'").append(addressing).append("',\n");
		buffer.append("\tendianness: \'").append(endianness).append("',\n");
		buffer.append("\toperatingSystem: \'").append(operatingSystem).append("',\n");
		buffer.append("\ttype: \'").append(type).append("',\n");
		buffer.append("\tinstructionSet: \'").append(instructionSet).append("'\n");
		buffer.append("}");
		return buffer.toString();
	}
	
	/**
	 * Returns the file system path.
	 * 
	 * @return
	 *   the file system path.
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * Sets the underlying file.
	 * 
	 * @param file
	 *   the fileto set.
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	/**
	 * Returns the format of the executable image.
	 * 
	 * @return
	 *   the format of the executable image.
	 */
	public Format getFormat() {
		return format;
	}
	
	/**
	 * Sets the format of the executable image.
	 * 
	 * @param format
	 *   the format of the executable image.
	 */
	void setFormat(Format format) {
		this.format = format;
	}

	/**
	 * Returns the addressing mode (memory addresses size).
	 *
	 * @return 
	 *   the addressing mode (memory addresses size).
	 */
	public Addressing getAddressing() {
		return addressing;
	}

	/**
	 * Sets the value of the addressing mode (memory addresses size).
	 *
	 * @param addressing 
	 *   the addressing to set mode (memory addresses size).
	 */
	public void setAddressing(Addressing addressing) {
		this.addressing = addressing;
	}

	/**
	 * Returns the endianness of the architecture.
	 *
	 * @return 
	 *   the endianness of the architecture.
	 */
	public Endianness getEndianness() {
		return endianness;
	}

	/**
	 * Sets the value of the endianness of the architecture.
	 *
	 * @param endianness 
	 *   the endianness of the architecture to set.
	 */
	public void setEndianness(Endianness endianness) {
		this.endianness = endianness;
	}

	/**
	 * Returns the supported operating system.
	 *
	 * @return 
	 *   the supported operating system.
	 */
	public OperatingSystem getOperatingSystem() {
		return operatingSystem;
	}

	/**
	 * Sets the value of the supported operating system.
	 *
	 * @param operatingSystem 
	 *   the supported operating system to set.
	 */
	public void setOperatingSystem(OperatingSystem operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	/**
	 * Returns the type of binary module.
	 *
	 * @return 
	 *   the type of binary module.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the value of the type of binary module.
	 *
	 * @param type 
	 *   the type of binary module to set.
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Returns the module's target instruction set.
	 *
	 * @return 
	 *   the module's target instruction set.
	 */
	public InstructionSet getInstructionSet() {
		return instructionSet;
	}

	/**
	 * Sets the value of the module's target instruction set.
	 *
	 * @param instructionSet 
	 *   the module's target instruction set.
	 */
	public void setInstructionSet(InstructionSet instructionSet) {
		this.instructionSet = instructionSet;
	}
}