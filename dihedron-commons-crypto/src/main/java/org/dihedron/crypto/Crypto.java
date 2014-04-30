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
package org.dihedron.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dihedron.commons.properties.Properties;
import org.dihedron.commons.properties.PropertiesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public abstract class Crypto {
	
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(Crypto.class);

	/**
	 * The overall library properties.
	 */
	private static final Properties properties = new Properties();
	
	/**
	 * The library startup method; this method installs the required security 
	 * providers and should be invoked prior to any operations pertaining to 
	 * cryptography and digital signature.
	 */
	static {
		logger.info("installing BouncyCastle security provider...");		
		Security.addProvider(new BouncyCastleProvider());		
		logger.info("... done installing providers!");
		
		InputStream stream = null;
		try {
			stream = Crypto.class.getResourceAsStream("crypto.properties");
			properties.load(stream);
		} catch (IOException e) {
			logger.error("error loading library properties from input stream", e);
		} catch (PropertiesException e) {
			logger.error("error loading library properties from input stream", e);
		} finally {
			if(stream != null) {
				try {
					stream.close();
					stream = null;
				} catch(IOException e) {
					logger.error("error closing library properties stream", e);
				}
			}
		}
	}
	
	/**
	 * Returns the current name of the library, as per its POM.
	 * 
	 * @return
	 *   the current name of the library, as per its POM.
	 */
	public static String getName() {
		if(properties != null && properties.hasKey("crypto.name")) {
			return properties.get("crypto.version");
		}
		return null;
	}

	/**
	 * Returns the current version of the library, as per its POM.
	 * 
	 * @return
	 *   the current version of the library, as per its POM.
	 */
	public static String getVersion() {
		if(properties != null && properties.hasKey("crypto.version")) {
			return properties.get("crypto.version");
		}
		return null;
	}

	/**
	 * Returns the current web site of the library.
	 * 
	 * @return
	 *   the current web site of the library.
	 */
	public static String getWebSite() {
		if(properties != null && properties.hasKey("crypto.website")) {
			return properties.get("crypto.version");
		}
		return null;
	}
	
	/**
	 * Protected constructor, to be used only by inheriting classes.
	 */
	protected Crypto() {
	}
}
