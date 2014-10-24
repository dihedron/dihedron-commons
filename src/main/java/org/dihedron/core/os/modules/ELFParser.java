/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.os.modules;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.dihedron.core.License;
import org.dihedron.core.formatters.BitMask;
import org.dihedron.core.os.Addressing;
import org.dihedron.core.os.Endianness;
import org.dihedron.core.os.OperatingSystem;
import org.dihedron.core.os.modules.ImageFile.Format;
import org.dihedron.core.os.modules.ImageFile.InstructionSet;
import org.dihedron.core.os.modules.ImageFile.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UNIX flavours and Linux use the Executable and Linkable Format (or Executable 
 * Linking Format, ELF) for their executables.
 * 
 * @see http://en.wikipedia.org/wiki/Executable_and_Linkable_Format
 * @author Andrea Funto'
 */
@License
public class ELFParser extends ImageFileParser {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ELFParser.class);

	/**
	 * Constructor.
	 */
	public ELFParser() {
	}

	/**
	 * @see org.dihedron.core.os.modules.ImageFileParser#parse(java.io.RandomAccessFile)
	 */
	@Override
	public ImageFile parse(File file) throws IOException, ImageParseException {
				
		try(RandomAccessFile image = new RandomAccessFile(file, "r")) {
		
			ImageFile module = new ImageFile(file); 
			
			// the interesting part of the header are the first 20 to 24 bytes
			byte[] buffer = new byte[24];
			image.readFully(buffer);
			
			//
			// 4 bytes: these are the magic number: 0x7F followed by "ELF" in ASCII
			//
			String magic = String.format("0x%02X", buffer[0]);
			byte [] tmp1 = new byte[3];
			System.arraycopy(buffer, 1, tmp1, 0, 3);
			String elf = new String(tmp1);
			
			logger.trace("magic number is '{}' followed by '{}'", magic, elf);
			
			if(magic.equals("0x7F") && elf.equals("ELF")) {
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
			logger.trace("operating system: '{}' ({})", module.getOperatingSystem(), BitMask.toBitMask(buffer[7]));
			
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
			logger.trace("short for instruction set is {}", is);
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
			
			return module;
		}		
	}
}
