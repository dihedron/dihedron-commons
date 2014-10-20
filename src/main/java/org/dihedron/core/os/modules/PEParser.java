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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.dihedron.core.formatters.BitMask;
import org.dihedron.core.os.modules.ImageFile.Addressing;
import org.dihedron.core.os.modules.ImageFile.Endianness;
import org.dihedron.core.os.modules.ImageFile.Format;
import org.dihedron.core.os.modules.ImageFile.InstructionSet;
import org.dihedron.core.os.modules.ImageFile.OperatingSystem;
import org.dihedron.core.os.modules.ImageFile.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Windows flavours use the Portable Executable format (PE) for their executables.
 * 
 * @see http://en.wikipedia.org/wiki/Portable_Executable and the MSDN for more details
 * on the IMAGE_NT_HEADERS structure.
 * @author Andrea Funto'
 */
public class PEParser extends ImageFileParser {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(PEParser.class);

	/**
	 * An enumeration representing the processor families supported by Windows.
	 * 
	 * @author Andrea Funto'
	 */
	public static enum ProcessorFamily {
		
		/**
		 * A constant indicating that the machine is unknown.
		 */
		IMAGE_FILE_MACHINE_UNKNOWN(0x0000),
		
		/**
		 * The constant representing the Matsushita AM33 processor family. 
		 */
		IMAGE_FILE_MACHINE_AM33(0x01d3),
		
		/**
		 * The constant representing the 32-bits Intel 386 or later processor family.
		 */
		IMAGE_FILE_MACHINE_I386(0x014c),
	
		/**
		 * The constant representing the Intel Itanium processor family.
		 */
		IMAGE_FILE_MACHINE_IA64(0x0200),
			
		/**
		 * The constant representing the AMD x64 processor family.
		 */
		IMAGE_FILE_MACHINE_AMD64(0x8664),
		
		/**
		 * The constant representing the ARM little endian processor family.
		 */
		IMAGE_FILE_MACHINE_ARM(0x01c0),
		
		/**
		 * The constant representing the EFI byte code.
		 */
		IMAGE_FILE_MACHINE_EBC(0x0ebc),
		 
		/**
		 * The constant prerepsenting the Mitsubishi M32R little endian processor family.
		 */
		IMAGE_FILE_MACHINE_M32R(0x9041),
		
		/**
		 * The constant representing the MIPS16 processor family.
		 */
		IMAGE_FILE_MACHINE_MIPS16(0x0266),
		
		/**
		 * The constant representing the MIPS with FPU processor family.
		 */
		IMAGE_FILE_MACHINE_MIPSFPU(0x0366), 
		
		/**
		 * The constant representing the MIPS16 with FPU.
		 */
		IMAGE_FILE_MACHINE_MIPSFPU1(0x0466),
		
		/**
		 * The constant representing the Power PC little endian processor family.
		 */
		IMAGE_FILE_MACHINE_POWERPC(0x01f0),
		
		/**
		 * The constant representing the Power PC with floating point support processor family.
		 */
		IMAGE_FILE_MACHINE_POWERPCFP(0x01f1),
		
		/**
		 * The constant representing the MIPS little endian processor family.
		 */
		IMAGE_FILE_MACHINE_R4000(0x0166),
		
		/**
		 * The constant representing the Hitachi SH3 processor family.
		 */
		IMAGE_FILE_MACHINE_SH3(0x01a2),
		
		/**
		 * The constant representing the Hitachi SH3 DSP processor family.
		 */
		IMAGE_FILE_MACHINE_SH3DSP(0x01a3),
		
		/**
		 * The constant representing the Hitachi SH4 processor family.
		 */
		IMAGE_FILE_MACHINE_SH4(0x01a6),
		
		/**
		 * The constant representing the Hitachi SH5 processor family.
		 */
		IMAGE_FILE_MACHINE_SH5(0x01a8),
		
		/**
		 * The constant representing the Thumb processor family.
		 */
		IMAGE_FILE_MACHINE_THUMB(0x01c2),
		
		/**
		 * The constant representing the MIPS little-endian WCE v2 processor family.
		 */
		IMAGE_FILE_MACHINE_WCEMIPSV2(0x0169);
		
		/**
		 * Factory method, returns the enumeration value corresponding to the
		 * given flags value.
		 * 
		 * @param flags
		 *   the flags value for this processor family.
		 * @return
		 */
		public static ProcessorFamily fromFlags(int flags) {
			for(ProcessorFamily value : values()) {
				logger.trace("comparing {} against {}", String.format("%04x", flags), String.format("%04x", value.flags));
				if(value.flags == flags) {
					return value;
				}
			}
			return null;
		}
		
		/**
		 * Constructor.
		 *
		 * @param flags
		 *   the associated flags value as per Winnt.h.
		 */
		private ProcessorFamily(int flags) {
			this.flags = flags & 65535;
			logger.trace("flags: 0x{} - bitmask: {}", String.format("%04X", flags), BitMask.toBitMask(flags));
		}
		
		/**
		 * The associated flags value as per Winnt.h.
		 */
		private int flags;
	}
	
	/**
	 * Constructor.
	 */
	public PEParser() {
	}

	/**
	 * @see org.dihedron.core.os.modules.ImageFileParser#parse(java.io.RandomAccessFile)
	 */
	@Override
	public ImageFile parse(File file) throws IOException, ImageParseException {
				
		try(RandomAccessFile image = new RandomAccessFile(file, "r")) {
		
			ImageFile module = new ImageFile(file); 
			
			// the interesting parts of the headers are in the first kilobyte or so
			byte[] buffer = new byte[1000];
			image.readFully(buffer);
			
			//
			// the first 36 bytes represent the old DOS executable file header; the last DWORD in 
			// this structure provides the offset to the beginning of the new NT headers
			//
			// typedef struct _IMAGE_DOS_HEADER {
			// 		WORD  e_magic;      // 00: MZ Header signature
			// 		WORD  e_cblp;       // 02: Bytes on last page of file
			// 		WORD  e_cp;         // 04: Pages in file
			// 		WORD  e_crlc;       // 06: Relocations
			//		WORD  e_cparhdr;    // 08: Size of header in paragraphs
			//		WORD  e_minalloc;   // 0a: Minimum extra paragraphs needed	
			//		WORD  e_maxalloc;   // 0c: Maximum extra paragraphs needed 
			//		WORD  e_ss;         // 0e: Initial (relative) SS value
			//		WORD  e_sp;         // 10: Initial SP value
			//		WORD  e_csum;       // 12: Checksum
			//		WORD  e_ip;         // 14: Initial IP value	
			//		WORD  e_cs;         // 16: Initial (relative) CS value
			//		WORD  e_lfarlc;     // 18: File address of relocation table
			//		WORD  e_ovno;       // 1a: Overlay number
			//		WORD  e_res[4];     // 1c: Reserved words
			//		WORD  e_oemid;      // 24: OEM identifier (for e_oeminfo)
			//		WORD  e_oeminfo;    // 26: OEM information; e_oemid specific
			//		WORD  e_res2[10];   // 28: Reserved words	
			//		DWORD e_lfanew;     // 3c: Offset to extended header
			//	} IMAGE_DOS_HEADER, *PIMAGE_DOS_HEADER;
			
			//
			// 2 bytes at ofset 0x00: these are the magic number: "MZ" in ASCII
			//
			byte [] word = new byte[2];
			System.arraycopy(buffer, 0x00, word, 0, 2);
			String mz = new String(word);
			if(!mz.equals("MZ")) {
				logger.error("error in IMAGE_DOS_HEADER: this is not a valid PE file");
				throw new ImageParseException("This is not a valid PE module (no 'MZ' signature found in IMAGE_DOS_HEADER)");
			}
			
			// 
			// 4 bytes at offset 0x3C: this is the e_lfanew unsigned offset to the IMAGE_NT_HEADERS
			//
			byte [] dword = new byte[4];
			System.arraycopy(buffer, 0x3C, dword, 0, 4);
			long offset = ((long)ByteBuffer.wrap(dword).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer().get()) & 0xFFFFFFFF;
			logger.trace("dword = 0x{}{}{}{} -> offset {}", String.format("%02X", dword[0]), String.format("%02X", dword[1]), String.format("%02X", dword[2]), String.format("%02X", dword[3]), offset);
			
			module.setFormat(Format.PE);
			module.setOperatingSystem(OperatingSystem.WINDOWS);
			
			//
			// the IMAGE_NT_HEADERS is the actual Windows NT header
			//
			// typedef struct _IMAGE_NT_HEADERS {
			//		DWORD                 Signature;
			//		IMAGE_FILE_HEADER     FileHeader;
			//		IMAGE_OPTIONAL_HEADER OptionalHeader;
			// } IMAGE_NT_HEADERS, *PIMAGE_NT_HEADERS;
			
			//
			// first get the signature and check that it is "PE\0\0"
			System.arraycopy(buffer, (int)offset, word, 0, 2);
			String pe = new String(word);
			logger.trace("IMAGE_NT_HEADERS signature: '{}'", pe);
			System.arraycopy(buffer, (int)offset + 2, word, 0, 2);
			if(!pe.equals("PE") || !(word[0] == 0) || !(word[1] == 0)) {
				logger.error("error in IMAGE_NT_HEADERS: this is not a valid PE file");
				throw new ImageParseException("This is not a valid PE module (no 'PE\\0\\0' signature found in IMAGE_NT_HEADERS)");
			}
			
			//
			// next comes the IMAGE_FILE_HEADER, which contains the real information
			//
			// typedef struct _IMAGE_FILE_HEADER {
			//		WORD  Machine;
			//		WORD  NumberOfSections;
			//		DWORD TimeDateStamp;
			//		DWORD PointerToSymbolTable;
			//		DWORD NumberOfSymbols;
			//		WORD  SizeOfOptionalHeader;
			//		WORD  Characteristics;
			// } IMAGE_FILE_HEADER, *PIMAGE_FILE_HEADER;
			
			System.arraycopy(buffer, (int)offset + 4, word, 0, 2);
			int machine = (int)ByteBuffer.wrap(word).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get() & 0x0000FFFF;
			logger.trace("machine: 0x{}{} (0x{})", String.format("%02X", word[0]), String.format("%02X", word[1]), String.format("%04X", machine));			
			switch(ProcessorFamily.fromFlags(machine)) {
			case IMAGE_FILE_MACHINE_UNKNOWN:
				logger.trace("instruction set: unknown");
				break;
			case IMAGE_FILE_MACHINE_I386:
				logger.trace("instruction set: i386");				
				module.setInstructionSet(InstructionSet.X86);
				module.setEndianness(Endianness.LITTLE_ENDIAN);
				break;
			case IMAGE_FILE_MACHINE_IA64:
				logger.trace("instruction set: itanium");
				module.setInstructionSet(InstructionSet.IA_64);
				module.setEndianness(Endianness.LITTLE_ENDIAN);
				break;
			case IMAGE_FILE_MACHINE_AMD64:
				logger.trace("instruction set: amd64");
				module.setInstructionSet(InstructionSet.X86_64);
				module.setEndianness(Endianness.LITTLE_ENDIAN);
				break;
			
				//
				// partially supported
				//
			case IMAGE_FILE_MACHINE_ARM:
				logger.trace("instruction set: ARM");
				module.setInstructionSet(InstructionSet.ARM);
				module.setEndianness(Endianness.LITTLE_ENDIAN);
				break;
				
			case IMAGE_FILE_MACHINE_MIPS16:
				logger.trace("instruction set: MIPS16");
				module.setInstructionSet(InstructionSet.MIPS);
				break;

			case IMAGE_FILE_MACHINE_MIPSFPU:
				logger.trace("instruction set: MIPSFU");
				module.setInstructionSet(InstructionSet.MIPS);
				break;
	 				
			case IMAGE_FILE_MACHINE_MIPSFPU1:
				logger.trace("instruction set: MIPSFPU1");
				module.setInstructionSet(InstructionSet.MIPS);			
				break;
				
			case IMAGE_FILE_MACHINE_POWERPC:
				logger.trace("instruction set: PowerPC");
				module.setInstructionSet(InstructionSet.POWERPC);			
				break;
				
			case IMAGE_FILE_MACHINE_POWERPCFP:
				logger.trace("instruction set: PowerPCCPF");
				module.setInstructionSet(InstructionSet.POWERPC);			
				break;

			case IMAGE_FILE_MACHINE_WCEMIPSV2:		
				logger.trace("instruction set: WCE MIPS SV2");
				module.setInstructionSet(InstructionSet.MIPS);
				module.setEndianness(Endianness.LITTLE_ENDIAN);
				break;
				
				//
				// the following are unsupported
				//
			case IMAGE_FILE_MACHINE_AM33:
				logger.trace("instruction set: AM33 (unsupported)");			
				break;
								
			case IMAGE_FILE_MACHINE_EBC:
				logger.trace("instruction set: EBC (unsupported)");			
				break;
				 
			case IMAGE_FILE_MACHINE_M32R:
				logger.trace("instruction set: M32R (unsupported)");
				module.setEndianness(Endianness.LITTLE_ENDIAN);
				break;
				
			case IMAGE_FILE_MACHINE_R4000:
				logger.trace("instruction set: R4000 (unsupported)");
				module.setEndianness(Endianness.LITTLE_ENDIAN);
				break;

			case IMAGE_FILE_MACHINE_SH3:
				logger.trace("instruction set: SH3 (unsupported)");			
				break;
				
			case IMAGE_FILE_MACHINE_SH3DSP:
				logger.trace("instruction set: SH3 DSP (unsupported)");			
				break;
				
			case IMAGE_FILE_MACHINE_SH4:
				logger.trace("instruction set: SH4 (unsupported)");			
				break;
				
			case IMAGE_FILE_MACHINE_SH5:
				logger.trace("instruction set: SH5 (unsupported)");			
				break;

			case IMAGE_FILE_MACHINE_THUMB:
				logger.trace("instruction set: THUMB");
				module.setInstructionSet(InstructionSet.ARM);			
				break;
				
			default:
				logger.trace("unsupported/unrecognised instruction set");
				break;
			}

			//
			// 2 bytes: the characteristics 
			//
			System.arraycopy(buffer, (int)offset + 24, word, 0, 2);
			int characteristics = (int)ByteBuffer.wrap(word).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get() & 0x0000FFFF;
			logger.trace("characteristics: {} {} (0x{})", String.format("%02X", word[0]), String.format("%02X", word[1]), String.format("%04X", characteristics));	
			
			logger.trace("characteristics             : {}", BitMask.toBitMask(characteristics));
			logger.trace("IMAGE_FILE_EXECUTABLE_IMAGE : {}", BitMask.toBitMask((int)IMAGE_FILE_EXECUTABLE_IMAGE));
			logger.trace("IMAGE_FILE_SYSTEM           : {}", BitMask.toBitMask((int)IMAGE_FILE_SYSTEM));
			logger.trace("IMAGE_FILE_DLL              : {}", BitMask.toBitMask((int)IMAGE_FILE_DLL));
			logger.trace("IMAGE_FILE_32BIT_MACHINE    : {}", BitMask.toBitMask((int)IMAGE_FILE_32BIT_MACHINE));			
			
			if((characteristics & IMAGE_FILE_32BIT_MACHINE) > 0) {
				logger.trace("file is for 32-bits systems");
				module.setAddressing(Addressing.SIZE_32);
			} else {
				logger.trace("file is for 64-bits systems");
				module.setAddressing(Addressing.SIZE_64);
			}
			
			
			if((characteristics & IMAGE_FILE_DLL) > 0 || file.getName().toLowerCase().endsWith(".dll")) {
				logger.trace("file is a DLL");
				module.setType(Type.SHARED);
			} else if((characteristics & IMAGE_FILE_EXECUTABLE_IMAGE) > 0) {
				logger.trace("file is an EXE");
				module.setType(Type.EXECUTABLE);
			} else if((characteristics & IMAGE_FILE_SYSTEM) > 0) {
				logger.trace("file is a kernel module");
			}
			
			
			
			/*
			if(magic.equals("0x7F") && pe.equals("ELF")) {
				logger.trace("this is an ELF image");
				module.setFormat(Format.ELF);
			} else {
				logger.error("this file does not represent a valid ELF image");
				throw new ImageParseException("File does not represent a valid ELF image (invalid magic number).");
			}
			
			//
			// 1 byte: next comes the number of bits, '1' stands for 32, '2' for 64
			//
			if(buffer[4] == 1) {
				logger.trace("image is for 32 bits architectures");
				module.setAddressing(Addressing.SIZE_32);
			} else if(buffer[4] == 2) {
				logger.trace("image is for 64 bits architectures");
				module.setAddressing(Addressing.SIZE_64);				
			}

			//
			// 1 byte: next comes the endianness, '1' stands for "little-endian", '2' for "big-endian"
			//
			if(buffer[5] == 1) {
				logger.trace("image is for little-endian architectures");
				module.setEndianness(Endianness.LITTLE_ENDIAN);
			} else if(buffer[4] == 2) {
				logger.trace("image is for big-endian architectures");
				module.setEndianness(Endianness.BIG_ENDIAN);		
			}
			
			// 
			// 1 byte: next comes a byte that is used to state whether this file complies with the
			// original specification of ELF; we're not interested in it (buffer[6]) and we skip it
			//
			
			//
			// 1 byte: the supported operating system family 
			//
			switch(buffer[7]) {
			case 0x00:
				module.setOperatingSystem(OperatingSystem.SYSTEM_V);
				break;
			case 0x01:
				module.setOperatingSystem(OperatingSystem.HPUX);
				break;
			case 0x02:
				module.setOperatingSystem(OperatingSystem.NETBSD);
				break;
			case 0x03:
				module.setOperatingSystem(OperatingSystem.LINUX);
				break;
			case 0x06:
				module.setOperatingSystem(OperatingSystem.SOLARIS);
				break;
			case 0x07:
				module.setOperatingSystem(OperatingSystem.AIX);
				break;
			case 0x08:
				module.setOperatingSystem(OperatingSystem.IRIX);
				break;
			case 0x09:
				module.setOperatingSystem(OperatingSystem.FREEBSD);
				break;				
			case 0x0C:
				module.setOperatingSystem(OperatingSystem.OPENBSD);
				break;
			}
			logger.trace("operating system: '{}'", module.getOperatingSystem());
			
			// 
			// 8 bytes: next come 8 bytes that further specify the API or are unused, we can safely skip them
			//
			
			//
			// 2 bytes: next a specification of the type of executable image: 1, 2, 3, 4 specify whether 
			// the object is relocatable, executable, shared, or core, respectively.
			//
			switch(buffer[16]) {
			case 1:
				module.setType(Type.RELOCATABLE);
				break;
			case 2:
				module.setType(Type.EXECUTABLE);
				break;
			case 3:
				module.setType(Type.SHARED);
				break;
			case 4:
				module.setType(Type.CORE);
				break;
			}
			logger.trace("binary module type is '{}'", module.getType());
			
			//
			// 2 bytes: the target instruction set
			//
			byte [] tmp2 = new byte[3];
			System.arraycopy(buffer, 18, tmp2, 0, 2);

			short is = ByteBuffer.wrap(tmp2).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get();
			logger.trace("sghort for instrcution set is {}", is);
			switch(is) {
			case 0x02:// 	SPARC
				module.setInstructionSet(InstructionSet.SPARC);
				break;
			case 0x03:
				module.setInstructionSet(InstructionSet.X86);
				break;
			case 0x08:
				module.setInstructionSet(InstructionSet.MIPS);
				break;
			case 0x14:
				module.setInstructionSet(InstructionSet.POWERPC);
				break;
			case 0x28:
				module.setInstructionSet(InstructionSet.ARM);
				break;	
			case 0x2A:
				module.setInstructionSet(InstructionSet.SUPERH);
				break;
			case 0x32:
				module.setInstructionSet(InstructionSet.IA_64);
				break;
			case 0x3E:
				module.setInstructionSet(InstructionSet.X86_64);
				break;
			case 0xB7:
				module.setInstructionSet(InstructionSet.AARCH64);
				break;
			}
			logger.trace("instruction set: '{}'", module.getInstructionSet());
			
	//		StringBuilder sb = new StringBuilder();
	//		sb.append(b)
	//	    for (byte b : bytes) {
	//	        sb.append(String.format("%02X ", b));
	//	    }					
	//		for(int i = 0; i < 4; ++i) {
	//			logger.debug("magic[{}] = '{}'", i, magic[i]);
	//		}
	 */

			
			return module;
		}		
	}
			
	/** 
	 * A constant indicating that the computer supports 32-bit words.
	 */
	private static final short IMAGE_FILE_32BIT_MACHINE  = 0x0100;

	/**
	 * A constant indicating that the file is executable (there are no unresolved external references).
	 */
	private static final short IMAGE_FILE_EXECUTABLE_IMAGE = 0x0002;

	/**
	 * A constant indicating that the image is a system file.
	 */
	private static final short IMAGE_FILE_SYSTEM =  0x1000;

	/**
	 * A constant indicating that the image is a DLL file. While it is an executable file, it cannot be run directly.
	 */
	private static final short IMAGE_FILE_DLL = 0x2000;

	
}
