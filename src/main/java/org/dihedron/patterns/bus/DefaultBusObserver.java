/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
 */

package org.dihedron.patterns.bus;

/**
 * A bus observer that provides a default implementation for the callback method
 * with a reference to the sender object.
 * 
 * @author Andrea Funto'
 */
public abstract class DefaultBusObserver<M> implements BusObserver<M> {
	
	/**
	 * @see org.dihedron.patterns.bus.BusObserver#onMessage(java.lang.Object, java.lang.Object)
	 */
	public void onMessage(Object sender, M message) {
		onMessage(message);
	}
}	
