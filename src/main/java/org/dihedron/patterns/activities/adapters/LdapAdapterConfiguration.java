/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.adapters;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class LdapAdapterConfiguration {
	
	/**
	 * The default network port for the LDAP protocol.
	 */
	public static final int DEFAULT_LDAP_PORT = 389;
		
	/**
	 * The logger.
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(LdapAdapterConfiguration.class);
	
	/**
	 * The context of the query.
	 */
	@SuppressWarnings("unused")
	private String searchBase;
	
	/**
	 * The filter used in the query.
	 */
	@SuppressWarnings("unused")
	private String searchFilter;
	
	/**
	 * The host name of the LDAP server.
	 */
	@SuppressWarnings("unused")
	private String server;
	
	/**
	 * The port on which the LDAP server is listening.
	 */
	@SuppressWarnings("unused")
	private int port = DEFAULT_LDAP_PORT;
//	
//	
//	/**
//	 * The default LDAP context factory; can be overridden through MongoDB's 
//	 * configuration.
//	 */
//	private static final String DEFAULT_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
//	
//	/**
//	 * The default LDAP search starting point. 
//	 */
//	private static final String DEFAULT_LDAP_SEARCH_BASE = "DC=utenze,DC=B***T,DC=IT";
//	
//	/**
//	 * The default LDAP authentication method.
//	 */
//	private static final String DEFAULT_AUTHENTICATION_METHOD = "simple";
//	
//	/**
//	 * The default LDAP authentication domain.
//	 */
//	private static final String DEFAULT_AUTHENTICATION_DOMAIN = "UTENZE";
//	
//	/**
//	 * The default LDAP server host name.
//	 */
//	private static final String DEFAULT_BDI_LDAP_HOST = "utenze.b*t.it";
//	
//	/**
//	 * The default LDAP server service port. 
//	 */
//	private static final String DEFAULT_BDI_LDAP_PORT = "389";
//	
//	/**
//	 * The name of the parameter used as a primary key to identify the resource 
//	 * to be located on the LDAP server.
//	 */
//	public static final String USER_LOGIN_PARAMETER = "login";
//
//	/**
//	 * The name of the LDAP server host; can be overridden through MongoDB's 
//	 * configuration.
//	 */
//	private String ldapHost = DEFAULT_BDI_LDAP_HOST;
//	
//	/**
//	 * The port on which the LDAP server is listening; can be overridden through 
//	 * MongoDB's configuration.
//	 */
//	private String ldapPort = DEFAULT_BDI_LDAP_PORT;
//	
//	/**
//	 * The LDAP search starting point; can be overridden through MongoDB's 
//	 * configuration. 
//	 */
//	private String ldapSearchBase = DEFAULT_LDAP_SEARCH_BASE;
//	
//	/**
//	 * The LDAP context factory; can be overridden through MongoDB's configuration.
//	 */
//	private String ldapContextFactory = DEFAULT_CONTEXT_FACTORY;
//	
//	/**
//	 * The LDAP authentication method; can be overridden through MongoDB's
//	 *  configuration.
//	 */
//	private String ldapAuthenticationMethod = DEFAULT_AUTHENTICATION_METHOD;
//	
//	/**
//	 * The LDAP authentication domain; can be overridden through MongoDB's 
//	 * configuration.
//	 */
//	private String ldapAuthenticationDomain = DEFAULT_AUTHENTICATION_DOMAIN;
//
//	/**
//	 * The user name used to authenticate the request to the remote LDAP server; 
//	 * can be overridden through MongoDB, but it should be read from the vault
//	 * when needed, for security reasons.
//	 */
//	private String ldapUser = null;
//	
//	/**
//	 * The password used to authenticate the request to the remote LDAP server; 
//	 * can be overridden through MongoDB, but it should be read from the vault
//	 * when needed, for security reasons.
//	 */	
//	private String ldapPassword = null;
 
	
	/**
	 * Constructor.
	 */
	public LdapAdapterConfiguration() {
	}
}
