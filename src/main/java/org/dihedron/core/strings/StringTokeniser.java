/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.strings;


import java.util.ArrayList;
import java.util.List;

import org.dihedron.core.License;

/**
 * Provider a more flexible implementation of Java's StringTokenizer, allowing 
 * for other types of delimiters besides \r\t\n.
 * 
 * @author Andrea Funto'
 */
@License
public class StringTokeniser {
		
	/**
	 * The default behaviour with respect to empty tokens: by default they are 
	 * reported among the output tokens.
	 */
	public static final boolean DEFAULT_SKIM_EMPTY = false;
	
	/**
	 * The default behaviour with respect to trimming of leading and trailing 
	 * spaces in the tokens as they are processed: by default they are removed.
	 */
	public static final boolean DEFAULT_TRIM_TOKENS = true;
	
	/**
	 * The delimiter used in the tokenising.
	 */
	private String delimiter = null;
	
	/**
	 * Controls whether the empty tokens should be removed from the output.
	 */
	private boolean skimEmpty = DEFAULT_SKIM_EMPTY;
	
	/**
	 * Controls whether the tokens' trailing and leading spaces should be trimmed.
	 */
	private boolean trimTokens = DEFAULT_TRIM_TOKENS;
	
	/**
	 * Constructor.
	 * 
	 * @param delimiter
	 *   the delimiter used in the tokenising.
	 */
	public StringTokeniser(String delimiter) {
		this.delimiter = delimiter;
	}
	
	/**
	 * Sets whether the empty tokens should be removed from the output.
	 * 
	 * @param skimEmpty
	 *   whether the empty tokens should be removed from the output.
  	 * @return
	 *   the object itself, for chaining.
	 */
	public StringTokeniser setSkimEmpty(boolean skimEmpty) {
		this.skimEmpty = skimEmpty;
		return this;
	}
	
	/**
	 * Sets whether the tokens should be trimmed as they are processed.
	 * 
	 * @param trimTokens
	 *   whether the tokens should be trimmed as they are processed.
  	 * @return
	 *   the object itself, for chaining.
	 */
	public StringTokeniser setTrimSpaces(boolean trimTokens) {
		this.trimTokens = trimTokens;
		return this;
	}
	
	/**
	 * Tokenises the input string using the given delimiter.
	 * 
	 * @param string
	 *   the input string.
	 * @return
	 *   the list of tokens.
	 */
	public String[] tokenise(String string) {
		String str = string;
		if(str == null || str.length() == 0) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		int length = delimiter.length();
		int idx = str.indexOf(delimiter);
		while(idx != -1) {
			String token = str.substring(0, idx);
			str = str.substring(idx + length);
			if(trimTokens) {
				token = token.trim();
			}			
			if(!skimEmpty || token.length() > 0) {
				list.add(token);
			}			
			idx = str.indexOf(delimiter);
		}
		
		// handle the case where there is only one token
		// or there is a token after the last separator
		if(str.trim().length() > 0) {
			if(trimTokens) {
				str = str.trim();
			}
			list.add(str);
		}
		
		String [] tokens = new String[list.size()];
		list.toArray(tokens);
		return tokens;
	}
}
