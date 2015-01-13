/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.ldap;

import org.dihedron.core.Credentials;
import org.dihedron.core.License;

/**
 * @author Andrea Funto'
 */
@License
public class LDAPCredentials extends Credentials {
		
	/**
	 * The default Client authentication domain.
	 */
	private static final String DEFAULT_AUTHENTICATION_DOMAIN = "";	
	
	/**
	 * The default Client authentication method.
	 */
	private static final String DEFAULT_AUTHENTICATION_METHOD = "simple";

	/**
	 * Constructor.
	 */
	public LDAPCredentials() {
		super();
		setDomain(DEFAULT_AUTHENTICATION_DOMAIN);
		setMethod(DEFAULT_AUTHENTICATION_METHOD);
	}
}
