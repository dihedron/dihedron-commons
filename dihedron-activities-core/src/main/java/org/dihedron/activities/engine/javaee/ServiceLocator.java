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
package org.dihedron.activities.engine.javaee;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ServiceLocator class provides JNDI lookups wherever CDI does not work.
 * 
 * @author Andrea Funto'
 */
public class ServiceLocator {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ServiceLocator.class);

	/**
	 * The JNDI initial context.
	 */
	private InitialContext context;

	/**
	 * The private classholding the reference to the unique instance of
	 * the ServiceLocator.
	 * 
	 * @author Andrea Funto'
	 */
	private static class SingletonHolder {
		private static ServiceLocator locator = new ServiceLocator();
	}
	
	/**
	 * Locates a service given its JNDI name and automatically casts its reference 
	 * to the appropriate class, as specified by the caller.
	 * 
	 * @param name
	 *   the JNDI name of the resource to lookup.
	 * @param clazz
	 *   the type to be used for casting the reference. 
	 * @return
	 *   the type-cast resource reference, or null if no resource found.
	 * @throws NamingException
	 *   if there was a problem with the JNDI lookup.
	 */
	public static <T> T getService(String name, Class<T> clazz) throws NamingException {		
		Object object = SingletonHolder.locator.context.lookup(name);
		if(object != null) {
			return clazz.cast(object);
		}
		return null;
	}
	
	/**
	 * Constructor.
	 */
	private ServiceLocator() {
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			logger.error("Error initialising ServiceLocator", e);
		}
	}	
}
