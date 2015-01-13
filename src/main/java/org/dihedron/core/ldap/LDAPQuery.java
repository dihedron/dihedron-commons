/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.ldap;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.dihedron.core.License;
import org.dihedron.core.strings.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public abstract class LDAPQuery {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(LDAPQuery.class);
	
	/**
	 * We're interested in all the attributes of the LDAP entity.
	 */
	public static final String[] ALL_ATTRIBUTES = { "*" };

	/**
	 * The default client search starting point: must be specified! 
	 */
	private static final String DEFAULT_LDAP_SEARCH_BASE = "";

	/**
	 * the search base filter to use when performing the Client query.
	 */
	private String searchBase = DEFAULT_LDAP_SEARCH_BASE;
	
	/**
	 * The attributes to look up.
	 */
	private String[] attributes = ALL_ATTRIBUTES;
	
	/**
	 * Returns the search base filter to use when performing the Client query.
	 * 
	 * @return 
	 *   the search base filter to use when performing the Client query.
	 */
	public String getSearchBase() {
		if(!Strings.isValid(searchBase)) {
			logger.warn("the search base is not valid: you may want to check your client code!");
		}
		return searchBase;
	}
	
	/**
	 * Stes the new value of the  search base filter to use when performing the 
	 * Client query.
	 * 
	 * @param searchBase
	 *   the new value of the search base filter to use when performing the Client 
	 *   query.
	 * @return 
	 *   the object itself, for method chaining.
	 */
	public LDAPQuery setSearchBase(String searchBase) {
		this.searchBase = searchBase;
		return this;
	}
	
	/**
	 * Returns the search filter.
	 * 
	 * @return
	 *   the search filter.
	 */
	public abstract String getSearchFilter();
	
	/**
	 * Returns the set of attributes to extract for each returned entity.
	 * 
	 * @return
	 *   the set of attributes to extract for each returned entity.
	 */
	public String [] getAttributes() {
		if(attributes == null || attributes.length != 1 || !attributes[0].equals(ALL_ATTRIBUTES)) {
			boolean found = false;
			if(attributes != null) {
				for(String attribute : attributes) {
					if(attribute.equals("distinguishedName")) {
						found = true;
						break;
					}
				}
			}
			if(!found) {
				String [] attributesx = new String[attributes != null ? (attributes.length + 1) : 1];
				if(attributes != null) {
					attributesx[0] = "distinguishedName";
					for(int i = 0; i < attributes.length; ++i) {
						attributesx[i+1] = attributes[i];
					}
				}
				attributes = attributesx;
			}
		}
		return attributes;
	}
	
	/**
	 * Sets the names of the attributes to extract for each returned entity.
	 * 
	 * @param attributes
	 *   the names of the attributes to extract for each returned entity.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public LDAPQuery setAttributes(String... attributes) {
		if(attributes != null && attributes.length > 0) {
			this.attributes = attributes;
		} else {
			this.attributes = ALL_ATTRIBUTES;
		}
		return this;
	}
	
	/**
	 * Sets the names of the attributes to extract for each returned entity.
	 * 
	 * @param attributes
	 *   the names of the attributes to extract for each returned entity.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public LDAPQuery setAttributes(List<String> attributes) {
		if(attributes != null && attributes.size() > 0) {
			this.attributes = new String[attributes.size()];
			int i = 0;
			for(String attribute : attributes) {
				this.attributes[i++] = attribute;
			}
		} else {
			this.attributes = ALL_ATTRIBUTES;
		}
		return this;
	}
	
	public List<LDAPEntity> execute(LDAPConnection connection) throws NamingException {
		List<LDAPEntity> entities = new ArrayList<>();
		
		DirContext context = connection.open();
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setCountLimit(0);
		controls.setReturningAttributes(getAttributes());
		NamingEnumeration<SearchResult> results = context.search(getSearchBase(), getSearchFilter(), controls);
		
		if (results != null && results.hasMore()) {				
			
			SearchResult result = results.next();	
			
			LDAPEntity entity = new LDAPEntity(null);
			NamingEnumeration<? extends Attribute> attribs = result.getAttributes().getAll();
			while(attribs.hasMore()) {					
				Attribute attrib = attribs.nextElement();
				LDAPAttribute attribute = new LDAPAttribute(attrib.getID());
				NamingEnumeration<?> vals = attrib.getAll();
				while(vals.hasMore()) {
					Object val = vals.nextElement();						
					attribute.addValue(val);
				}
				entity.addAttribute(attribute);
			}
			entities.add(entity);
		} else {
			logger.warn("error retrieving data from Client server");
		}

		
		return entities;
		
	}
}
