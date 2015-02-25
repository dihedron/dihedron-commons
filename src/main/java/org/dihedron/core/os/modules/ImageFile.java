/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.os.modules;

import java.io.File;

import org.dihedron.core.License;
import org.dihedron.core.os.Addressing;
import org.dihedron.core.os.Endianness;
import org.dihedron.core.os.OperatingSystem;

/**
 * A class representing an image file.
 * 
 * @author Andrea Funto'
 */
@License
public class ImageFile {

	/**
	 * The format of executable image.
	 * 
	 * @author Andrea Funto'
	 */
	@License
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
	 * The target instruction set for the binary module.
	 *  
	 * @author Andrea Funto'
	 */
	@License
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
	@License
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
