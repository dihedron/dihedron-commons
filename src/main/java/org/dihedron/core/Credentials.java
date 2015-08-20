/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core;


/**
 * @author Andrea Funto'
 */
@License
public class Credentials {
	
	/**
	 * The username to use for authentication.
	 */
	private String username;
	
	/**
	 * The password to use for authentication.
	 */
	private String password;
	
	/**
	 * The domain to use for authentication.
	 */
	private String domain;
	
	/**
	 * The method to use for authentication.
	 */
	private String method;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public Credentials setUsername(String username) {
		this.username = username;
		return this;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public Credentials setPassword(String password) {
		this.password = password;
		if(password == null) {
			throw new IllegalArgumentException("You must set the password before proceeding!!!");
		}
		return this;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Sets the new value of the authentication domain.
	 * 
	 * @param domain
	 *   the value of the authentication method.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public Credentials setDomain(String domain) {
		this.domain = domain;
		return this;
	}

	/**
	 * Returns the value of the authentication method.
	 * 
	 * @return 
	 *   the value of the authentication method.
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the new value of the authentication method.
	 * 
	 * @param method 
	 *   the new value of the authentication method.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public Credentials setMethod(String method) {
		this.method = method;
		return this;
	}	
}
