/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.dihedron.core.Credentials;
import org.dihedron.core.License;
import org.dihedron.core.strings.Strings;

/**
 * @author Andrea Funto'
 */
@License
public class LDAPConnection implements AutoCloseable {

	/**
	 * The default Client context factory; can be overridden through MongoDB's 
	 * configuration.
	 */
	private static final String DEFAULT_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";	
	
	/**
	 * Client environment used for the connection.
	 */
	private Hashtable<String, String> environment = null;
	
	/**
	 * Information about the server to connect to.
	 */
	private LDAPServer server = null;
	
	/**
	 * The credentials to use to authenticate against the server.
	 */
	private Credentials credentials = null;
	
	/**
	 * The directory context.
	 */
	private DirContext context = null;

	/**
	 * Returns information about the server to connect to.
	 * 
	 * @return
	 *   information about the server to connect to.
	 */
	public LDAPServer getServer() {
		return server;
	}

	/**
	 * Sets the new value for the information about the server to connect to.
	 * 
	 * @param server
	 *   the new value for the information about the server to connect to.
	 */
	public LDAPConnection setServer(LDAPServer server) {
		this.server = server;
		return this;
	}

	/**
	 * Returns the credentials to use to authenticate against the server.
	 * 
	 * @return 
	 *   the credentials to use to authenticate against the server.
	 */
	public Credentials getCredentials() {
		return credentials;
	}
	
	/**
	 * Sets the new value for the credentials to use to authenticate against the 
	 * server.
	 * 
	 * @param credentials
	 *   the credentials to use to authenticate against the server.
	 */
	public LDAPConnection setCredentials(Credentials credentials) {
		this.credentials = credentials;
		return this;
	}
	
	public DirContext getDirectoryContext() {
		return context;
	}
	
	public DirContext open() throws NamingException {
		return open(DEFAULT_CONTEXT_FACTORY);
	}

	public DirContext open(String contextFactory) throws NamingException {
		
		if(context == null) {
	
			String host = server != null ? server.getHost() : null;
			String port = server != null ? server.getPort() : null;
			String domain = credentials != null ? credentials.getDomain() : null;
			String method = credentials != null ? credentials.getMethod() : null;
			String username = credentials != null ? credentials.getUsername() : null;
			String password = credentials != null ? credentials.getPassword() : null;
	
			if(!Strings.isValid(host)) {
				host = System.getProperty("LDAP_HOST");
			}
			if(!Strings.isValid(port)) {
				port = System.getProperty("LDAP_PORT");
			}
			if(!Strings.isValid(username)) {
				username = System.getProperty("LDAP_USERNAME");
			}
			if(!Strings.isValid(password)) {			
				password = System.getProperty("LDAP_PASSWORD");
			}
			if(!Strings.isValid(domain)) {
				domain = System.getProperty("LDAP_DOMAIN");
			}
			if(!Strings.isValid(method)) {
				method = System.getProperty("LDAP_METHOD");
			}
							
			environment = new Hashtable<String, String>();
			environment.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
			environment.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port);
			environment.put(Context.SECURITY_AUTHENTICATION, method);
			environment.put(Context.SECURITY_PRINCIPAL, domain + "\\" + username);
			environment.put(Context.SECURITY_CREDENTIALS, password);
			
			context = new InitialDirContext(environment);
		}
		return context;
	}

	/**
	 * @throws NamingException 
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws NamingException {
		if(context != null) {
			context.close();
		}
	}
}

