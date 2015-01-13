/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.ldap;

import org.dihedron.core.License;

/**
 * @author Andrea Funto'
 */
@License
public class LDAPServer {
	
	/**
	 * The default Client server host name.
	 */
	private static final String DEFAULT_LDAP_HOST = "";
	
	/**
	 * The default Client server service port. 
	 */
	private static final String DEFAULT_LDAP_PORT = "389";
	
	/**
	 * The server host name.
	 */
	private String host = DEFAULT_LDAP_HOST;
	
	/**
	 * The server port.
	 */
	private String port = DEFAULT_LDAP_PORT;

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public LDAPServer setHost(String host) {
		this.host = host;
		return this;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public LDAPServer setPort(String port) {
		this.port = port;
		return this;
	}
	
	

}
