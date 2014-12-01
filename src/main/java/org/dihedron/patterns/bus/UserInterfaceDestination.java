/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.bus;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that switches the current thread to the user interface event
 * dispatching thread for the given adapted destination; this is useful to
 * let a user interface component handle the message in its event dispatching 
 * instead of having to switch to it.
 *   
 * @author Andrea Funto'
 */
public class UserInterfaceDestination<M> extends FilterDestination<M> {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(UserInterfaceDestination.class);
	
	/**
	 * Constructor.
	 * 
	 * @param destination
	 *   the filtered destination.
	 */
	public UserInterfaceDestination(Destination<M> destination) {
		super(destination);
	}
	
	@Override
	public String getId() {
		return "UserInterface@" + destination.getId();
	}

	/**
	 * @see org.dihedron.patterns.bus.Destination#onMessage(java.lang.Object)
	 */
	@Override
	public void onMessage(M message) {
		if(SwingUtilities.isEventDispatchThread()) {
			logger.trace("already in the event dispatching thread");
			destination.onMessage(message);
		} else {
			logger.trace("need to switch to the event dispatching thread");
			SwingUtilities.invokeLater(new Runnable() {
				
				/**
				 * The message to be dispatched.
				 */
				M message;
				
				/**
				 * Sets the message to be dispatched.
				 * 
				 * @param message
				 *   the message to be dispatched.
				 * @return
				 *   the object itself, for method chaining.
				 */
				Runnable setMessage(M message) {
					this.message = message;
					return this;
				}
				
				/**
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					logger.trace("in the event dispatching thread, forwarding the message");
					destination.onMessage(message);					
				}				
			}.setMessage(message));
		}
	}
}
