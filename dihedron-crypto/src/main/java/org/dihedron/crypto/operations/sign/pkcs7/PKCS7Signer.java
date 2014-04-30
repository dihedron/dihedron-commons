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
package org.dihedron.crypto.operations.sign.pkcs7;

import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.bc.BcRSASignerInfoVerifierBuilder;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.dihedron.crypto.KeyRing;
import org.dihedron.crypto.constants.SignatureAlgorithm;
import org.dihedron.crypto.exceptions.CryptoException;
import org.dihedron.crypto.operations.sign.Signer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class PKCS7Signer extends Signer {
	
	/**
	 * The logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(PKCS7Signer.class);
	
	/**
	 * The digest and encryption algorithm combination used to create the signature.
	 */
	private SignatureAlgorithm algorithm;
	
	/**
	 * A BouncyCastle to create signature data.
	 */
	private SignerInfoGenerator signerInfoGenerator = null;
	
	/**
	 * The underlying BouncyCastle certificate store.
	 */
	private Store certificateStore = null;

	/**
	 * Constructor.
	 * 
	 * @param alias
	 *   the alias of the certificate to be used for signing.
	 * @param keyring
	 *   the key ring containing the private key used for signing.
	 * @param provider
	 *   the security provider backing up the key ring functionalities.
	 * @param algorithm
	 *   the digest and encryption algorithm combination used to create the 
	 *   signature.
	 * @throws CryptoException
	 *   if any among alias, key ring and provider is null. 
	 */
	public PKCS7Signer(String alias, KeyRing keyring, Provider provider, SignatureAlgorithm algorithm) throws CryptoException {
		super(alias, keyring, provider);
		this.algorithm = algorithm;
		logger.debug("creating PKCS#7 signer with '{}' signature algorithm", this.algorithm);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param alias
	 *   the alias of the certificate to be used for signing.
	 * @param keyring
	 *   the key ring containing the private key used for signing.
	 * @param provider
	 *   the security provider backing up the key ring functionalities.
	 * @param digest
	 *   the algorithm used to hash the data.
	 * @param encryption
	 *   the algorithm used to encrypt the hash.
	 * @throws CryptoException
	 *   if any among alias, key ring and provider is null. 
	 */
	public PKCS7Signer(String alias, KeyRing keyring, Provider provider, String digest, String encryption) throws CryptoException {
		  this(alias, keyring, provider, SignatureAlgorithm.fromAlgorithmDescriptions(digest, encryption));
	}
	
	@Override
	public void initialise() throws CryptoException {		
		try {				
			logger.info("signing with alias '{}'", getAlias());
							
			// retrieve key and certificate
			Key key = keyring.getPrivateKey(getAlias());
			X509Certificate x509certificate = (X509Certificate)keyring.getCertificate(getAlias());
			
			// this may throw a CertificateExpiredException or
			// CertificateNotYetValidException
			x509certificate.checkValidity();
			
			logger.info("certificate is valid at current date");
						
			// prepare the certificates store
			List<Certificate> certificates = new ArrayList<Certificate>();      	      
			certificates.add(x509certificate);			
			certificateStore = new JcaCertStore(certificates);									
			
			String algo = algorithm.toBouncyCastleCode();
			logger.debug("going to sign with algorithm '{}'...", algorithm);
			
			ContentSigner signer = new JcaContentSignerBuilder(algo).setProvider(provider.getName()).build((PrivateKey)key);
			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder().setProvider("BC").build();
			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(dcp);
			sigb.setDirectSignature(false); // include signed attributes
			sigb.setSignedAttributeGenerator(new Pkcs7AttributeTableGenerator(algorithm.getDigestAlgorithm(), x509certificate));			  
			signerInfoGenerator = sigb.build(signer, x509certificate);
			logger.debug("signer for PKCS#7 files initialised");
    	} catch (OperatorCreationException e) {
			logger.error("error creating operator", e);
			throw new CryptoException("Error creating signing operator (BouncyCastle)", e);
		} catch (CertificateEncodingException e) {
			logger.error("invalid certificate encoding", e);
			throw new org.dihedron.crypto.exceptions.CertificateEncodingException("Invalid certificate encoding", e);
		} catch (CertificateExpiredException e) {
			logger.error("expired certificate", e);
			throw new org.dihedron.crypto.exceptions.CertificateExpiredException("Expired certificate", e);
		} catch (CertificateNotYetValidException e) {
			logger.error("certificate is not yet valid (may still need to be activated?)", e);
			throw new org.dihedron.crypto.exceptions.CertificateNotYetValidException("Certificate not yet valid", e);
		}
	}	
		
	public byte [] sign(byte [] data) throws CryptoException {
		try {
			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
			generator.addSignerInfoGenerator(signerInfoGenerator);
			generator.addCertificates(certificateStore);
			//generator.addCRLs(crlStore);
			
			CMSTypedData message = new CMSProcessableByteArray(data);		
			CMSSignedData signedData = generator.generate(message, isEncapsulateData());
			return signedData.getEncoded();
		} catch (IOException e){
			logger.error("error accessing data (I/O error)", e);
			throw new CryptoException("I/O error accessing data", e);
		} catch (CMSException e) {
			logger.error("CMS error", e);
			throw new CryptoException("CMS error", e);
		}
	}
	
    
	public boolean verify(byte [] signed) throws CryptoException {
		try {
			return verify(new CMSSignedData(signed), null);
		} catch (CMSException e) {
			logger.error("error creating CMSSignedData object", e);
			throw new CryptoException("Error creating CMSSignedData object", e);
		}
	}
	
	public boolean verify(byte [] signed, byte [] data) throws CryptoException {
		try {
			return verify(new CMSSignedData(signed), data);
		} catch (CMSException e) {
			logger.error("error creating CMSSignedData object", e);
			throw new CryptoException("Error creating CMSSignedData object", e);
		}
	}
	
	private boolean verify(CMSSignedData signed, byte [] data) throws CryptoException {
		
		boolean result = true;
		
		// verification
		logger.debug("starting CMSSignedData verification ... ");
		
		Store certificates = signed.getCertificates();
    	SignerInformationStore signers = signed.getSignerInfos();

    	logger.debug(signers.getSigners().size() + " signers found"); 
    	try {
	    	for (Object i : signers.getSigners()) {
	    		SignerInformation signer = (SignerInformation)i;
	    		logger.debug(certificates.getMatches(signer.getSID()).size() + " certificates fround for " + signer.getSID());
	    		for (Object j : certificates.getMatches(signer.getSID())) {
	    			X509CertificateHolder certificate = (X509CertificateHolder)j;
	    			if(signer.verify(new BcRSASignerInfoVerifierBuilder(
	    					new DefaultDigestAlgorithmIdentifierFinder(),
	    					new BcDigestCalculatorProvider()).build(certificate))){
						logger.info("signature verified");
						result = result && true;  				
	    			} else {
						logger.error("signature verification failed!");
						result = false;
	    			}
	    		}
	       
	    		if (data != null) {
	    			if(MessageDigest.isEqual(data, signer.getContentDigest())) {
	    				logger.info("content digest verified");
	    				result = result && true;
	    			} else {
	    				logger.error("content digest verification failed!");
	    				result = false;
	    			}
	    		}	
	    	}
	    	return result;
    	} catch (OperatorCreationException e) {
			logger.error("error creating operator", e);
			throw new CryptoException("Error creating operator", e);
		} catch (CertificateException e) {
			logger.error("invalid certificate", e);
			throw new CryptoException("Invalid certificate", e);
		} catch (CMSException e) {
			logger.error("CMS error", e);
			throw new CryptoException("CMS error", e);
		}
	}	
}
