/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Activities library ("Activities").
 *
 * Activities is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Activities is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Activities. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.patterns.activities.adapters;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

import org.dihedron.patterns.activities.ActivityContext;
import org.dihedron.patterns.activities.base.Transformation;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapter used to retrieve data from an LDAP server.
 * 
 * @author Andrea Funto'
 */
public class LdapAdapter extends Transformation {
	
	/**
	 * The key of the initial context in the activity context, if any.
	 */
	public static final String INITIAL_CONTEXT_FACTORY_KEY = "INITIAL_CONTEXT_FACTORY";
	
	// TODO: get sensible value
	public static final String DEFAULT_INITIAL_CONTEXT_FACTORY = "";
	
	/**
	 * the default network port of the LDAP protocol.
	 */
	public static final int DEFAULT_LDAP_PORT = 389;
	
	/**
	 * The default method used to perform the authentication.
	 */
	public static final String DEFAULT_AUTHENTICATION_METHOD = "simple";
	
	/**
	 * The domain of the LDAP user, necessary to by non-anonymously to a server.
	 */
	private String domain;
	
	/**
	 * The user account used to bind to a server non-anonymously.
	 */
	private String username;
	
	/**
	 * The user account password.
	 */
	private String password;
	
	/**
	 * The method used to authenticate to the LDAP server.
	 */
	private String method = DEFAULT_AUTHENTICATION_METHOD;
	
	/**
	 * The host name of the LDAP server.
	 */
	private String hostname;
	
	/**
	 * The network port of the LDAP server.
	 */
	private int port = DEFAULT_LDAP_PORT;

	/**
	 * Returns the domain.
	 *
	 * @return 
	 *   the domain.
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Sets the value of the domain.
	 *
	 * @param domain 
	 *   the domain to set.
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * Returns the username.
	 *
	 * @return 
	 *   the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the value of the username.
	 *
	 * @param username 
	 *   the username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the password.
	 *
	 * @return 
	 *   the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value of the password.
	 *
	 * @param password 
	 *   the password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the bind method.
	 *
	 * @return 
	 *   the bind method.
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the value of the bind method.
	 *
	 * @param method 
	 *   the value of the bind method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	/**
	 * Returns the host name of the LDAP server to query.
	 *
	 * @return 
	 *   the host name of the LDAP server to query.
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Sets the host name of the LDAP server to query.
	 *
	 * @param hostname 
	 *   the host name of the LDAP server to set.
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * Returns the network port of the LDAP server.
	 *
	 * @return 
	 *   the network port of the LDAP server.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the value of the network port of the LDAP server.
	 *
	 * @param port 
	 *   the network port of the LDAP server to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * LDAP environment used for the connection.
	 */
	private static Map<String, String> environment = null;

	/**
	 * The logger defined for this class.
	 */
	private static Logger logger = LoggerFactory.getLogger(LdapAdapter.class);
	
//	/**
//	 * This method performs the actual LDAP search
//	 * 
//	 * @param searchFilter
//	 *            the search criteria to use for the query to be performed
//	 * @param responseAttributes
//	 *            The attributes to look for when performing the search
//	 * @return An enumeration of SearchResult objects ( actually one)
//	 * @throws NamingException
//	 *             if something goes wrong when performing the search
//	 */
//	private NamingEnumeration<SearchResult> query(String searchFilter, String[] responseAttributes) throws NamingException {
//		DirContext dctx = new InitialDirContext(environment);
//		SearchControls searchControls = new SearchControls();
//		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//		searchControls.setReturningAttributes(responseAttributes);
//		NamingEnumeration<SearchResult> res = dctx.search(ldapSearchBase, searchFilter, searchControls);
//		dctx.close();
//		return res;
//	}
//
//	/**
//	 * This method provides the implementation of a search operation executed
//	 * towards an LDAP repository.
//	 * 
//	 * @param parameters
//	 *   The map of input parameters to be used to perform the query.
//	 * @return 
//	 *   a Data object containing all the necessary information being retrieved 
//	 *   from the LDAP query.
//	 * @see 
//	 * @throws AdapterException
//	 *   if something goes wrong when performing the LDAP query.
//	 */
//	protected Data perform(ActivityContext context, ActivityData data) throws AdapterException {
//		String userLoginToLookup = (String) getData().get(USER_LOGIN_PARAMETER);
//		logger.info("Looking up login '{}' in the LDAP query", userLoginToLookup);
//		String userList = "(name=" + userLoginToLookup + ")";
//		Data dataToReturn = new Data();
//		String searchFilter = "(&(objectCategory=person)(|" + userList + "))";
//		logger.info("The search filter used within the query is '{}'", searchFilter);
//		
//		try {
//			// get the name of the LDAP attributes to be searched for
//			Map<String, String> mappings = getSourceToEntityMapping();
//			String [] ldapAttributes = new String[mappings.size()];
//			mappings.keySet().toArray(ldapAttributes);
//			
//			NamingEnumeration<SearchResult> resultsEnum = query(searchFilter, ldapAttributes);
//			
//			if (resultsEnum != null) {
//				SearchResult results = resultsEnum.next();				
//
//				Attributes attributes = results.getAttributes();
//				for(String ldapAttribute : ldapAttributes) {
//					logger.debug("Translating source attribute '{}'", ldapAttribute);
//					Attribute attribute = attributes.get(ldapAttribute);
//					Serializable value = attribute != null ? (Serializable) attribute.get() : null;
//					String entityAttribute = getEntityAttributeForSourceAttribute(ldapAttribute);
//					logger.debug("Storing entity attribute '{}', value is '{}'", entityAttribute, value);
//					dataToReturn.put(entityAttribute, value);
//				}
//			}
//		} catch (NamingException e) {
//			logger.error("An error occurred when performing the ldap search " + e.getMessage());
//			throw new AdapterException(e);
//		}
//		return dataToReturn;
//	}

	@Override
	protected Scalar transform(ActivityContext context, Scalar scalar) throws ActivityException {
				
		if(!(scalar.get() instanceof LdapQuery)) {
			throw new ActivityException("Input to LdapAdapter must be an LDAP query");
		}
		LdapQuery query = (LdapQuery)scalar.get();
		
		logger.trace("running LDAP query: '{}'", query.toString());
		

		return null;
	}

	private String getInitialContextFactory(ActivityContext context) {
		String result = DEFAULT_INITIAL_CONTEXT_FACTORY;
		if(context != null && context.hasValue(INITIAL_CONTEXT_FACTORY_KEY)) {
			String value = (String)context.getValue(INITIAL_CONTEXT_FACTORY_KEY); 
			if(value != null && value.trim().length() > 0) {
				result = value;
			}
		}
		return result;
	}
	
	private Map<String, String> makeEnvironment(ActivityContext context) {
		if(environment == null) {
			environment = new HashMap<String, String>();
			environment.put(Context.INITIAL_CONTEXT_FACTORY, getInitialContextFactory(context));
			environment.put(Context.PROVIDER_URL, "ldap://" + hostname + ":" + port);
			environment.put(Context.SECURITY_AUTHENTICATION, method);
			environment.put(Context.SECURITY_PRINCIPAL, domain + "\\" + username);
			environment.put(Context.SECURITY_CREDENTIALS, password);
		}
		return environment;
	}
}
