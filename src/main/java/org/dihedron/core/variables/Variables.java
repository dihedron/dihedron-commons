/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.variables;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dihedron.core.License;
import org.dihedron.core.regex.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public final class Variables {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Variables.class);
	
	/**
	 * Whether the variable pattern matching is applied in a case sensitive way
	 * by default.
	 */
	public static final boolean DEFAULT_CASE_SENSITIVE = true;
	
	/**
	 * Regular expression to identify scalar variables.
	 */
	private static final String VARIABLE_PATTERN = "(?:\\$\\{([A-Za-z_][\\-A-Za-z_\\.0-9]*)\\})";

	
	/**
	 * Matches all variables (according to the ${[a-zA-Z_][a-zA-Z0-9_\-]*} pattern)
	 * and replaces them with the value provided by the set of value provides.
	 * The processing is repeated until no more valid variable names can be found 
	 * in the input text, or no more variables can be bound (e.g. when some values 
	 * are not available); the match is performed in a case sensitive fashion. 
	 * 
	 * @param text
	 *   the text possibly containing variable identifiers.
	 * @param providers
	 *   a set of zero or more value providers.
	 * @return
	 *   the text with all variables bound.
	 */
	public static final String replaceVariables(String text, ValueProvider... providers) {
		return replaceVariables(text, DEFAULT_CASE_SENSITIVE, providers);
	}
	
	/**
	 * Matches all variables (according to the ${[a-zA-Z_][a-zA-Z0-9_\-]*} pattern)
	 * and replaces them with the value provided by the set of value provides.
	 * The processing is repeated until no more valid variable names can be found 
	 * in the input text, or no more variables can be bound (e.g. when some values 
	 * are not available). 
	 * 
	 * @param text
	 *   the text possibly containing variable identifiers.
	 * @param caseSensitive
	 *   whether the variable names should be treated in a case sensitive way
	 *   (default: true).  
	 * @param providers
	 *   a set of zero or more value providers.
	 * @return
	 *   the text with all variables bound.
	 */
	public static final String replaceVariables(String text, boolean caseSensitive, ValueProvider... providers) {
		String replaceText = text;
		Regex regex = new Regex(VARIABLE_PATTERN, caseSensitive);
		List<String[]> variables = null;
		
		Set<String> unboundVariables = new HashSet<String>();
		
		boolean oneVariableBound = true;
		while(oneVariableBound && (variables = regex.getAllMatches(replaceText)).size() > 0) {
			oneVariableBound = false;			
			logger.trace("analysing text: '{}'", replaceText);
			for(String[] groups : variables) {
				String variable = groups[0];
				logger.trace("... handling variable '{}'...", variable);
				String value = null;
				for(ValueProvider provider : providers) {
					value = provider.onVariable(variable);
					if(value != null) {
						logger.trace("... replacing variable '{}' with value '{}'", variable, value);
						replaceText = replaceText.replace("${" + variable +"}", value);
						logger.trace("... text is now '{}'", replaceText);
						oneVariableBound = true;
						break;
					}
				}
				if(value == null) {
					unboundVariables.add(variable);
				}
			}
		}
		return replaceText;
	}
	 
	
	/**
	 * Private constructor to ensure library is never instantiated.
	 */
	private Variables() {
	}
}
