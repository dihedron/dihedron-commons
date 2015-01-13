/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.ldap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dihedron.core.License;
import org.dihedron.core.strings.Strings;

/**
 * @author Andrea Funto'
 */
@License
public class LDAPEntity {

	/**
	 * The id of this entity.
	 */
	private String id;
	
	/**
	 * The map of attributes.
	 */
	private Set<LDAPAttribute> attributes = new HashSet<>();
	
	/**
	 * Constructor.
	 * 
	 * @param id
	 *   the id of the entity.
	 */
	public LDAPEntity(String id) {
		this.id = id;
	}
	
	/**
	 * Returns the entity id.
	 * 
	 * @return
	 *   the entity id.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id of the entity.
	 * 
	 * @param id
	 *   the id of the entity.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public LDAPEntity setId(String id) {
		this.id = id;
		return this;
	}	
	
	public LDAPEntity addAttribute(LDAPAttribute attribute) {
		if(attribute != null) {
			attributes.add(attribute);
			if(!Strings.isValid(id)) {
				if(attribute.getName().equals("distinguishedName")) {
					this.setId((String)attribute.getValues().get(0));
				}
			}
		}
		return this;
	}

	public LDAPEntity addAttribute(String name, List<Object> values) {
		if(Strings.isValid(name)) {
			LDAPAttribute attribute = new LDAPAttribute(name);
			if(values != null) {
				for(Object value : values) {
					attribute.addValue(value);
				}
			}
			addAttribute(attribute);
		}
		return this;
	}
	
	public LDAPAttribute getAttribute(String name) {
		if(attributes != null) {
			for(LDAPAttribute attribute : attributes) {
				if(attribute.getName().equals(name)) {
					return attribute;
				}
			}
		}
		return null;
	}	
		
	public Set<LDAPAttribute> getAttributes() {
		return attributes;
	}
	
	public String toString() {
		StringBuilder buffer = new StringBuilder("{");
		buffer.append("id: ").append("'").append(id).append("', ").append("\n");
		buffer.append("attributes: [").append("\n");
		for(LDAPAttribute attribute : attributes) {
			buffer.append("'").append(attribute.toString()).append("', ").append("\n");
		}		
		buffer.append("]}").append("\n");
		return buffer.toString();
	}
}
