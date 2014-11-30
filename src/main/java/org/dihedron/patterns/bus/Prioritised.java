/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import org.dihedron.core.License;

/**
 * An interface for prioritised messages.
 * 
 * @author Andrea Funto'
 */
@License
public interface Prioritised {
	
	/**
	 * The priority for unprioritised tasks.
	 */
	public static final int NO_PRIORITY = -1;
	
	/**
	 * Returns the current message's priority.
	 * 
	 * @return
	 *   the current messages's priority.
	 */
	int getPriority();
}