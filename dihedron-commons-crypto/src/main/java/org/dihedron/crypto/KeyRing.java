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


import java.io.Closeable;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import org.dihedron.crypto.exceptions.CryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The base abstract class providing an abstract interface to several different 
 * kinds of key stores. Support for the {@code Closeable} interface provides a 
 * way to use this class (and its concrete sub-classes) in try-with-resources
 * blocks (Java7 and later).
 * 
 * @author Andrea Funto'
 */
public abstract class KeyRing implements Closeable {
	
	/**
	 * The critical extension that signature certificates must have in order to 
	 * be recognised as valid for signing.
	 */
	public static final String SIGNATURE_CRITICAL_EXTENSION_OID = "2.5.29.15";
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(KeyRing.class);
	
	/**
	 * The actual Java key store object.
	 */
	private KeyStore keystore;
		
	/**
	 * Acquires a reference to a KeyStore and returns its reference after having
	 * logged in. Once the application is done with the KeyStore, it should go 
	 * back to its wrapper to release its reference; this will effectively attempt
	 * to log out of it. 
	 * 
	 * @return
	 *   a reference to the object itself, for method chaining.
	 * @throws CryptoException 
	 */
	public KeyRing open() throws CryptoException {
		if(keystore == null) {
			keystore = openImpl();
		}
		return this;
	}
	
	/**
	 * Attempts to log out of the KeyStore, and releases its reference.
	 * 
	 * @throws CryptoException
	 */
	public void close() {
		try {
			closeImpl();
		} catch (CryptoException e) {
			logger.error("error releasing cryptographic resources", e);
		}
		keystore = null;
	}

	/**
	 * Loads and logs in to the key store.
	 * 
	 * @throws CryptoException
	 *   if an I/O or a key store specific error occurs while loading the key
	 *   store.
	 */
	protected abstract KeyStore openImpl() throws CryptoException;
	
	/**
	 * Logs out of the key store, if supported by the underlying technology.
	 * 
	 * @throws CryptoException
	 *   if a communication error with the underlying device occurs.
	 */
	protected abstract void closeImpl() throws CryptoException;
	
	/**
	 * Gives access to the underlying key store; it is deprecated because improper 
	 * manipulation of the wrapped key store may result in inconsistent system state.
	 * 
	 * @return
	 *   the internal key store object.
	 */
	@Deprecated
	public KeyStore getKeyStore() {
		return keystore;
	}
	
	/**
	 * Returns a list of aliases for the given key store.
	 * 
	 * @return
	 *   a list of aliases for the given key store.
	 * @throws CryptoException
	 *   if an error occurs while enumerating the aliases in the key store. 
	 */
	public List<String> enumerateAliases() throws CryptoException {
		List<String> aliases = null;		
		try {
			Enumeration<String> enumeration = keystore.aliases();
			aliases = new ArrayList<String>();
			logger.debug("enumerating aliases...");
			while(enumeration.hasMoreElements()) {
				String alias = enumeration.nextElement();
				logger.debug("... alias: '{}'", alias);
				aliases.add(alias);
			}
		} catch(KeyStoreException e) {
			logger.error("error enumerating aliases in the keystore", e);
			throw new CryptoException("error enumerating aliases in the keystore", e);			
		}
		return aliases;
	}
	
	/**
	 * Checks whether the alias has a corresponding private key.
	 * 
	 * @param alias
	 *   the name of the alias
	 * @return
	 *   <code>true</code> if it corresponds to a private key, <code>false</code> 
	 *   otherwise.
	 * @throws CryptoException 
	 *   if an error occurs while checking the given alias with the key store. 
	 */
	public boolean isPrivateKeyAlias(String alias) throws CryptoException {
		try {
	        // does alias refer to a private key?
			return keystore.isKeyEntry(alias);
		} catch(KeyStoreException e) {
			logger.error("error checking alias '" + alias + "' with the keystore", e);
			throw new CryptoException("error checking alias '" + alias + "' with the keystore", e);			
		}
	}

	/**
	 * Checks whether the alias has a corresponding certificate.
	 * 
	 * @param alias
	 *   the name of the alias.
	 * @return
	 *   <code>true</code> if it corresponds to a certificate, <code>
	 *   false</code> otherwise.
	 * @throws CryptoException 
	 *   if an error occurs while checking the given alias with the key store. 
	 */
	public boolean isCertificateAlias(String alias) throws CryptoException {
		try {
	        // does alias refer to a trusted certificate?
			return keystore.isCertificateEntry(alias);
		} catch(KeyStoreException e) {
			logger.error("error checking alias '" + alias + "' with the keystore", e);
			throw new CryptoException("error checking alias '" + alias + "' with the keystore", e);			
		}
    }

	/**
	 * Returns the private key (or a proxy) for the given alias.
	 * 
	 * @param alias
	 *   the name of the alias.
	 * @return
	 *   the private key.
	 * @throws CryptoException
	 *   if there was an error accessing the private key for the given alias. 
	 */
	public Key getPrivateKey(String alias) throws CryptoException {
		return getPrivateKey(alias, null);
	}
		
	/**
	 * Returns the private key for the given alias, using the provided password 
	 * to unlock it; the key pair (private/public) password can differ from that 
	 * of the key store it is kept in. 
	 * 
	 * @param alias
	 *   the name of the alias.
	 * @param password
	 *   the private key password.
	 * @return
	 *   the private key.
	 * @throws CryptoException
	 *   if there was an error accessing the private key for the given alias. 
	 */
	public Key getPrivateKey(String alias, String password) throws CryptoException {
		try {
			Key key = keystore.getKey(alias, (password != null ? password.toCharArray() : null));
			if(key instanceof PrivateKey) {
				return key;
			}			
		} catch (UnrecoverableKeyException e) {
			logger.error("error recovering key for alias '" + alias + "' from the keystore", e);
			throw new CryptoException("error recovering key for alias '" + alias + "' from the keystore", e);			
		} catch (KeyStoreException e) {
			logger.error("error accessing the keystore", e);
			throw new CryptoException("error accessing the keystore", e);			
		} catch (NoSuchAlgorithmException e) {
			logger.error("invalid algorithm specified in keystore access", e);
			throw new CryptoException("invalid algorithm specified in keystore access", e);
		}
		return null;
	}
	
	/**
	 * Returns the public key for the given alias.
	 * 
	 * @param alias
	 *   the name of the alias.
	 * @return
	 *   the public key.
	 * @throws CryptoException
	 *   if there was an error accessing the public key for the given alias. 
	 */
	public Key getPublicKey(String alias) throws CryptoException {
		return getPublicKey(alias, null);
	}
	
	/**
	 * Returns the public key for the given alias, using the provided password 
	 * to unlock it; the key pair (private/public) password can differ from that 
	 * of the key store it is kept in.
	 * 
	 * @param alias
	 *   the name of the alias.
	 * @param password
	 *   the public key password.
	 * @return
	 *   the public key.
	 * @throws CryptoException
	 *   if there was an error accessing the public key for the given alias. 
	 */
	public Key getPublicKey(String alias, String password) throws CryptoException {
		try {
			Key key = getPrivateKey(alias, password);
			if (key instanceof PrivateKey) {
	            // get certificate of public key
	            Certificate certificate = keystore.getCertificate(alias);
	            // get public key from the certificate
	            return certificate.getPublicKey();
			}			
		} catch (KeyStoreException e) {
			logger.error("error accessing the keystore", e);
			throw new CryptoException("error accessing the keystore", e);			
		}
		return null;
	}
	
	/**
	 * Retrieves the certificate for the given alias.
	 * 
	 * @param alias
	 *   the name of the alias.
	 * @return
	 *   the certificate.
	 * @throws CryptoException 
	 *   if an error occurs extracting the certificate for the given alias from
	 *   the key store.
	 */
	public Certificate getCertificate(String alias) throws CryptoException {
		try {
			return keystore.getCertificate(alias);
		} catch (KeyStoreException e) {
			logger.error("error accessing the keystore", e);
			throw new CryptoException("error accessing the keystore", e);			
		}
	}

	/**
	 * Gets a private and public key pair for the
	 * given alias.
	 * 
	 * @param alias
	 *   the name of the alias.
	 * @param password
	 *   the private an public key password.
	 * @return
	 *   a key pair, containing both private and
	 *   public key.
	 * @throws CryptoException
	 *   if an error occurs extracting either the private of the public key. 
	 */
	public KeyPair getKeyPair(String alias, String password) throws CryptoException {
		PrivateKey privateKey = (PrivateKey)getPrivateKey(alias, password);
		if(privateKey != null) {
			PublicKey publicKey = (PublicKey)getPublicKey(alias, password);
			return new KeyPair(publicKey, privateKey);		
		}
		return null;
	}
	
	/**
	 * Retrieves a list of aliases corresponding to certificates that bear the 
	 * "signature" critical extension OID.
	 * 
	 * @return
	 *   a list of signature aliases.
	 * @throws CryptoException 
	 *   if it cannot enumerate aliases in the keystore.
	 */
	public List<String> getSignatureKeyAliases() throws CryptoException {
		try {
			List<String> signatureAliases = new ArrayList<String>();
			Enumeration<String> aliases = keystore.aliases();
			while(aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				try {
					logger.debug("analysing alias: '{}'", alias);
					
					Certificate certificate = keystore.getCertificate(alias);
					if(certificate instanceof X509Certificate) {
						logger.debug(" - certificate     : 'X.509'");
						X509Certificate x509certificate = (X509Certificate)certificate;
						Set<String> extensions = x509certificate.getCriticalExtensionOIDs();
						for(String extension : extensions) {
							logger.debug(" - extension       : '{}'", extension);
							if(extension.contains(SIGNATURE_CRITICAL_EXTENSION_OID)) {
								logger.debug(" - good for signing: true");
								signatureAliases.add(alias);
								break;
							} else {
								logger.debug(" - good for signing: false");
							}
						}
					} else {
						logger.debug(" - certificate     : 'plain'");
						logger.debug(" - good for signing: false");
					}
				} catch (KeyStoreException e) {
					// this error is swallowed intentionally, as a failure on one 
					// certificate should not prevent the analysis of the others 
					logger.error("error accessing certificate for alias '{}'", alias);
				}
			}
			return signatureAliases;
		} catch(KeyStoreException e) {
			logger.error("error accessing the keystore to enumerate aliases", e);
			throw new CryptoException("error accessing the keystore to enumerate aliases", e);						
		}
	}
	
	/**
	 * Gets the certificate chain for the given alias.
	 * 
	 * @param signatureAlias
	 *   the name of the alias.
	 * @return
	 *   the certificate chain.
	 * @throws CryptoException 
	 *   if it cannot reconstruct the certificate chain.
	 */
	public List<Certificate> getCertificateChain(String signatureAlias) throws CryptoException {
		try {
			ArrayList<Certificate> list = new ArrayList<Certificate>();
			Certificate[] certificates = keystore.getCertificateChain(signatureAlias);
			for (int i = 0, length = certificates == null ? 0 : certificates.length; i < length; i++) {
				list.add(certificates[i]);
			}
			return list;
		} catch(GeneralSecurityException e) {
			logger.error("error retrieving certificate chain for alias '{}'", signatureAlias);
			throw new CryptoException("error retrieving certificate chain for alias '" + signatureAlias + "'", e);
		}
	}

	/**
	 * Gets the certificate chain for the given alias, as an array of Certificates.
	 * 
	 * @param signatureAlias
	 *   the name of the alias.
	 * @return
	 *   an array of Certificates, or <code>null</code> if none found.
	 * @throws CryptoException 
	 *   if it cannot reconstruct the certificate chain.
	 */
	public Certificate[] getCertificateChainAsArray(String signatureAlias) throws CryptoException {
		List<Certificate> chain = getCertificateChain(signatureAlias);
		if(chain != null && chain.size() > 0) {
			Certificate [] certificates = new Certificate[chain.size()];
			chain.toArray(certificates);
			return certificates;
		}
		return null;
	}
}
