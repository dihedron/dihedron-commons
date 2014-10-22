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
 * The base interface for all bus observers.
 * 
 * @author Andrea Funto'
 */
public interface BusObserver<M> {
	
	/**
	 * A method invoked whenever the a new message is available on the internal 
	 * message bus; this way of dispatching information around caters for loose
	 * coupling among components. This default implementation simply ignores the 
	 * sender parameters; subclasses can always choose to override this method if 
	 * they feel they need to be notified about the actual sender object.
	 * 
	 * @param sender
	 *   the message sender; could be null if no sender was specified.
	 * @param message
	 *   the actual message.
	 */
	void onMessage(Object sender, M message);
	
	/**
	 * A method invoked whenever the a new message is available on the internal 
	 * message bus; this way of dispatching information around caters for loose
	 * coupling among components.
	 * 
	 * @param message
	 *   the actual message.
	 */
	void onMessage(M message);
}	
