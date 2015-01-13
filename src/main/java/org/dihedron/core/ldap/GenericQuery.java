/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.ldap;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class GenericQuery extends LDAPQuery {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(GenericQuery.class);

	/**
	 * The login of the user to lookup.
	 */
	private String query;
	
	/**
	 * Constructor.
	 */
	public GenericQuery(String query) {
		this.query = query;
		logger.trace("looking up login '{}' in the Client query", query);
	}

	/**
	 * @see it.bancaditalia.oss.utils.ldap.LDAPQuery#getSearchFilter()
	 */
	@Override
	public String getSearchFilter() {
//		String filter = "(&(objectCategory=person)(|(name=" + login + ")))";
//		String filter = "(&(objectCategory=person)(name=" + login + "))";
		logger.trace("the search filter used within the query is '{}'", query);
		return query;
	}	
}
