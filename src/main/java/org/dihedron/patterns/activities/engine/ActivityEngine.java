/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine;

import org.dihedron.patterns.activities.TypedVector;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.ActivityData;



/**
 * The base interface for activity engines: implementations of this subclass
 * take a set of activities and execute them on a host system, which may be the
 * local machine, the application server, a set of nodes on the network, or a
 * pool of threads. 
 * 
 * @author Andrea Funto'
 */
public interface ActivityEngine {
	
	/**
	 * Performs a given set of activities on the input data.
	 * 
	 * @param infos
	 *   the information about the activities to execute.
	 * @return
	 *   the results object, either a vector or a scalar.
	 */
	ActivityData execute(TypedVector<ActivityInfo> infos) throws ActivityException;
}
