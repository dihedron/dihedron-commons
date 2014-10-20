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
