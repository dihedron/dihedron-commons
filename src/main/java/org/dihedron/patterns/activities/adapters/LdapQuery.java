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
public class LdapQuery {
	
	@License
	public enum Scope {
		BASE,
		ONE,
		SUBTREE
	}
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(LdapQuery.class);

	/**
	 * The object at which to begin the search; no objects above the base DN 
	 * are returned
	 */
	private String base;
	
	/**
	 * The scope of the search; this is base, one, or subtree.
	 */
	private Scope scope;

	/**
	 * A filter which limits the entries that are returned by the server.
	 */
	private String filter;		
	
	/**
	 * The attributes of the result object to return.
	 */
	private String[] attributes;
	
	/**
	 * Constructor.
	 */
	public LdapQuery() {
	}
	
	/**
	 * Constructor.
	 *
	 * @param base
	 *   the base DN at which to begin the search; no objects above the base DN
	 *   will be returned.
	 * @param filter
	 *   a filter which limits the entries returned by the server.
	 * @param scope
	 *   the scope of the search.
	 * @param attributes
	 *   the attributes of the matching object to be returned.
	 */
	public LdapQuery(String base, String filter, Scope scope, String... attributes) {
		this.base = base;
		this.filter = filter;
		this.scope = scope;
		this.attributes = attributes;
	}

	/**
	 * Returns the base.
	 *
	 * @return 
	 *   the base.
	 */
	public String getBase() {
		return base;
	}

	/**
	 * Sets the value of the base.
	 *
	 * @param base 
	 *   the base to set.
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * Returns the scope.
	 *
	 * @return 
	 *   the scope.
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * Sets the value of the scope.
	 *
	 * @param scope 
	 *   the scope to set.
	 */
	public void setScope(Scope scope) {
		this.scope = scope;
	}

	/**
	 * Returns the filter.
	 *
	 * @return 
	 *   the filter.
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * Sets the value of the filter.
	 *
	 * @param filter 
	 *   the filter to set.
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * Returns the attributes.
	 *
	 * @return 
	 *   the attributes.
	 */
	public String[] getAttributes() {
		return attributes;
	}

	/**
	 * Sets the value of the attributes.
	 *
	 * @param attributes 
	 *   the attributes to set.
	 */
	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}
}
