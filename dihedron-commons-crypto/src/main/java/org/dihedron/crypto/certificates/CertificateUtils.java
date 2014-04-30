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
package org.dihedron.crypto.certificates;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.jce.X509Principal;
import org.dihedron.crypto.constants.DigestAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class CertificateUtils {
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CertificateUtils.class);
	
	/**
	 * Possible values of certificates usage.
	 * 
	 * @author Andrea Funto'
	 */
	private enum KeyUsage {
		digitalSignature, 
		nonRepudiation, 
		keyEncipherment, 
		dataEncipherment, 
		keyAgreement, 
		keyCertSign, 
		cRLSign, 
		encipherOnly, 
		decipherOnly;
	};
	
	/**
	 * Checks if the given certificate has all the necessary extensions to be used 
	 * as a signing certificate.
	 * 
	 * @param certificate
	 *   the certificate to test.
	 * @return
	 *   whether the certificate is good for signing.
	 */
	public static boolean isSignatureX509Certificate(X509Certificate certificate) {
		boolean[] usage = certificate.getKeyUsage();
		
		for (KeyUsage u : KeyUsage.values()) {
			if (usage[u.ordinal()]) {
				//System.out.println(" - bit " + u + " set");
			}
		}
		if (usage != null 
				&& usage[KeyUsage.keyEncipherment.ordinal()] 
				&& usage[KeyUsage.digitalSignature.ordinal()]) {
			return true;
		}
		return false;	
	}
	
	/**
	 * Creates an IssuerSerial object for th given certificate.
	 * 
	 * @param x509certificate
	 *   the certificate whose issuer serial must be retrieved.
	 * @return
	 *   the IssuerSerial object for the certificate.
	 * @throws CertificateEncodingException
	 * @throws IOException
	 */
	public static IssuerSerial makeIssuerSerial(X509Certificate x509certificate) throws CertificateEncodingException, IOException {
		
		// get the certificate issuer and serial
		X509CertificateStructure structure = X509CertificateStructure.getInstance(ASN1Object.fromByteArray(x509certificate.getEncoded()));
		X509CertificateHolder holder = new X509CertificateHolder(structure);
		
		// get the certificate serial number			
		DERInteger serialNumber = new DERInteger(holder.getSerialNumber());
		logger.debug("signer's certificate serial no.: \"" + serialNumber.toString());
		
		// get the certificate principal
		X509Principal x509principal = new X509Principal(structure.getIssuer());			
		logger.debug("signer's certificate principal: \"" + x509principal);
		GeneralNames generalNames = new GeneralNames(new DERSequence(new GeneralName(x509principal)));

		// create the issuer and serial combination to put in the SigningCertificate[V2]
		return new IssuerSerial(generalNames, serialNumber);					
	}
	
	public static ESSCertID makeESSCertIdV1(X509Certificate x509certificate, IssuerSerial issuerSerial, DigestAlgorithm digestAlgorithm) throws NoSuchAlgorithmException, CertificateEncodingException {
    	logger.info("adding signing certificate v1 to signed attributes");
    	MessageDigest digest = MessageDigest.getInstance(digestAlgorithm.getAsn1Id());
    	ESSCertID essCertIdV1 = new ESSCertID(digest.digest(x509certificate.getEncoded()), issuerSerial);
    	return essCertIdV1;
    } 
	
	public static ESSCertIDv2[] makeESSCertIdV2(X509Certificate x509certificate, IssuerSerial issuerSerial, DigestAlgorithm digestAlgorithm) throws NoSuchAlgorithmException, CertificateEncodingException {
    	logger.info("adding signing certificate v2 to signed attributes");    	
    	MessageDigest digest = MessageDigest.getInstance(digestAlgorithm.getAsn1Id());
    	AlgorithmIdentifier algorithmId = new AlgorithmIdentifier(digestAlgorithm.getAsn1Id());
    	ESSCertIDv2 essCertIdv2 = new ESSCertIDv2(algorithmId, digest.digest(x509certificate.getEncoded()), issuerSerial);
    	ESSCertIDv2 essCertIdv2s[] = new ESSCertIDv2[1];
    	essCertIdv2s[0] = essCertIdv2;
    	return essCertIdv2s;
    }
	
	public static CertStore makeCertificateStore(Certificate certificate) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
		List<Certificate> certificates = new ArrayList<Certificate>();      	      
		certificates.add(certificate);			
		CertStore store = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certificates), "BC");
		return store;
	}
	
	public static boolean writeToFile(Certificate certificate, String filename) {
		boolean result = false;
		FileOutputStream fos = null;
		try {
			byte[] data = certificate.getEncoded();
			fos = new FileOutputStream(filename); 
			fos.write(data);	
			result = true;
		} catch (CertificateEncodingException e) {
			logger.error("certificate encoding error", e);
		} catch (FileNotFoundException e) {
			logger.error("file not found", e);
		} catch (IOException e) {
			logger.error("error writing certificate to disk", e);
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch(IOException e) {
				logger.error("error closing output stream", e);
			}
		}
		return result;
	}
	
}
