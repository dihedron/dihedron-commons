/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.regex;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dihedron.core.License;

/**
 * A class implementing a regular expression as an object; this class is thread-safe.
 * 
 * @author Andrea Funto'
 */
@License
public class Regex {
	
	/** 
	 * Whether by default regular expressions are case sensitive (yes). 
	 */
	public static final boolean DEFAULT_CASE_SENSITIVITY = true;
	
	/** 
	 * A regular expression pattern that matches all. 
	 */
	public static final String MATCH_ALL = ".*";
			
	/** 
	 * The regular expression pattern; subclasses may store their pattern in
	 * a different place, e.g the thread-local storage. 
	 */
	private Pattern pattern = null;

	/** 
	 * Whether the regular expression is case sensitive. 
	 */
	private boolean caseSensitive = DEFAULT_CASE_SENSITIVITY;
		
	/**
	 * Constructor.
	 */
	public Regex() {
		this(MATCH_ALL, DEFAULT_CASE_SENSITIVITY);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param regex
	 *   the regular expression.
	 */
	public Regex(String regex) {
		this(regex, DEFAULT_CASE_SENSITIVITY);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param regex
	 *   the regular expression.
	 * @param caseSensitive
	 *   whether the regular expression should be regarded as case insensitive.
	 */
	public Regex(String regex, boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
		pattern = Pattern.compile(regex, (caseSensitive ? 0 : Pattern.CASE_INSENSITIVE));			 		
	}
	
	/**
	 * Returns the string representation of the regular expression.
	 * 
	 * @return
	 *   the actual regular expression as a string.
	 */
	public String getRegex() {
		return pattern.pattern();
	}
	
	/**
	 * Returns whether the regular expression is case sensitive.
	 * 
	 * @return
	 *   whether the regular expression is case sensitive.
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	
	/**
	 * Sets whether the regular expression should be handled as case sensitive.
	 * 
	 * @param caseSensitive
	 *   whether the regular expression is case sensitive.
	 */
	protected void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * Checks whether the given string matches the regular expression.
	 * 
	 * @param string
	 *   the string to be matched against the regular expression
	 * @return
	 *   <code>true</code> if the string matches the regular expression, 
	 *   <code>false</code> otherwise.
	 */
	public boolean matches(String string) {		
		Matcher matcher = pattern.matcher(string);
		boolean result = matcher.matches();
		return result;		
	}

	/**
	 * Retrieves a list of tokens in the input string that
	 * match this regular expression.
	 * 
	 * @param string
	 *   the input string.
	 * @return
	 *   a list of arrays of strings, each representing a set of strings
	 *   matched in the input string.
	 */
	public List<String[]> getAllMatches(String string) {
		Matcher matcher = pattern.matcher(string);
		List<String[]> matched = new ArrayList<String[]>();
		while(matcher.find()) {
			int count = matcher.groupCount();
			String [] strings = new String[count];
			for(int i = 0; i < count; ++i) {
				strings[i] = matcher.group(i+1);				
			}
			matched.add(strings);
		} 
		return matched;
	}
	
	/**
	 * Returns the string representation of the object; as a matter of fact, it 
	 * returns the actual regular expression.
	 */
	@Override
	public String toString() {
		return getRegex();
	}

	/**
	 * Checks if this regular expression has the same pattern as that of the given object.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other != null && other instanceof Regex && ((Regex)other).getRegex() != null && ((Regex)other).getRegex().equals(getRegex())); 
	}
	
	/**
	 * Returns the hash code of the regular expression, for use as a map key.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return ("regex: " + getRegex()).hashCode();
	}	
}
