/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.ldap;

import java.util.ArrayList;
import java.util.List;

import org.dihedron.core.License;

/**
 * @author Andrea Funto'
 */
@License
public class LDAPAttribute {

	/**
	 * The name of the attribute.
	 */
	private String name;
	
	/**
	 * The attribute values.
	 */
	private List<Object>values = new ArrayList<>();
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the name of the attribute.
	 */
	public LDAPAttribute(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the attribute.
	 * 
	 * @return
	 *   the name of the attribute.
	 */
	public String getName() {
		return name;
	}
	
	public LDAPAttribute addValue(Object value) {
		if(value != null) {
			values.add(value);
		}
		return this;
	}
	
	public List<Object> getValues() {
		return values;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof LDAPAttribute && name.equals(((LDAPAttribute)other).name);
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder("{");
		buffer.append("name: ").append("'").append(name).append("', ");
		buffer.append("values: [");
		for(Object value : values) {
			buffer.append("'").append(value.toString()).append("', ");
		}		
		buffer.append("]}");
		return buffer.toString();
	}	
}
