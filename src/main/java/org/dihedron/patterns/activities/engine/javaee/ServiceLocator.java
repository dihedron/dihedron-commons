/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine.javaee;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ServiceLocator class provides JNDI lookups wherever CDI does not work.
 * 
 * @author Andrea Funto'
 */
@License
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
	 * The @License
private classholding the reference to the unique instance of
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
