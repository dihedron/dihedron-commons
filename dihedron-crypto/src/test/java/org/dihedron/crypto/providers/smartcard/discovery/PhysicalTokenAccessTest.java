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
package org.dihedron.crypto.providers.smartcard.discovery;

import java.io.IOException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import org.dihedron.crypto.CryptoService;
import org.dihedron.crypto.KeyRing;
import org.dihedron.crypto.exceptions.ProviderException;
import org.dihedron.crypto.providers.ProviderFactory;
import org.dihedron.crypto.providers.smartcard.SmartCardKeyRing;
import org.dihedron.crypto.providers.smartcard.SmartCardProviderFactory;
import org.dihedron.crypto.providers.smartcard.SmartCardTraits;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * NOTE: this test should only be run if you have a physical token attached to your PC.
 * 
 * @author Andrea Funto'
 */
@Ignore
public class PhysicalTokenAccessTest {

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(CryptoService.class);
	
	private static SmartCardTraits getSmartCardTraits() throws IOException, ProviderException {
		DataBase database = DataBaseLoader.loadDefault();
		
		List<Reader> readers = new ArrayList<Reader>();
		while(readers.size() != 1) {
			readers.clear();
			for(Reader reader : Readers.enumerate()) {
				logger.trace("reader:\n{}", reader);
				if(reader.hasSmartCard()) {
					readers.add(reader);
				}
			}
		}

		SmartCard smartcard = database.get(readers.get(0).getATR());
		logger.trace("selected smartcard:\n{}", smartcard);
		
		return new SmartCardTraits(readers.get(0), smartcard);
	}
	
	
	/**
	 * @param args
	 */
	@Test
	public void testLoadCertificates() throws Exception {
		
		ProviderFactory<SmartCardTraits> factory = null;
		Provider provider = null;
		try {
			factory = new SmartCardProviderFactory();		
						
			provider = factory.acquire(getSmartCardTraits());
			
			String password = "12345678";
			
			@SuppressWarnings("resource") KeyRing keyring = new SmartCardKeyRing(provider, password).open();
			
			for(String alias : keyring.getSignatureKeyAliases()) {
				logger.info("signature alias: '{}'", alias);
			}
			
			keyring.close();			
			
		} finally {
			new SmartCardProviderFactory().release(provider);
		}
	}

}
