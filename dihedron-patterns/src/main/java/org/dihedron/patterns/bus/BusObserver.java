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

import org.dihedron.patterns.bus.messages.BusMessage;

public interface BusObserver {
	
	/**
	 * A method invoked whenever the a new message is available on the internal 
	 * message bus; this way of dispatching information around caters for loose
	 * coupling among back-end worker components and front-end components; all
	 * information regarding the model is passed around through the model itself, 
	 * which implements another for of observer pattern: registering one's 
	 * component as a bus listener is  a way to be informed of processing events.
	 * 
	 * @param message
	 *   the actual event.
	 */
	void onMessage(BusMessage event);
}	
