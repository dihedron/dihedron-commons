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
package org.dihedron.commons.bus.messages;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * The base class for all processing-related events (those beating no document-
 * specific or related meaning).
 * 
 * @author Andrea Funto'
 */
public abstract class BusErrorMessage implements BusMessage {

	/**
	 * An event specific message.
	 */
	private String message;
	
	/**
	 * An optional event-specific exception.
	 */
	private Throwable exception;
	
	/**
	 * Constructor.
	 * 
	 * @param message
	 *   an event specific message.
	 */
	protected BusErrorMessage(String message) {
		this(message, null);
	}	

	/**
	 * Constructor.
	 * 
	 * @param message
	 *   an event specific message.
	 * @param exception
	 *   an event specific exception.
	 */
	protected BusErrorMessage(String message, Throwable exception) {
		this.message = message;
		this.exception = exception;
	}
	
	/**
	 * Returns the event-specific message.
	 * 
	 * @return
	 *   the event-specific message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Returns the event-specific (optional) exception.
	 * 
	 * @return
	 *   the event-specific (optional) exception.
	 */
	public Throwable getException() {
		return exception;
	}
	
	/**
	 * Returns a JSON-like representation of the bus message.
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("message: {\n");
		buffer.append("\tclass :   '").append(this.getClass().getSimpleName()).append("',\n");
		buffer.append("\tmessage : '").append(message).append("',\n");
		if(exception != null) {
			buffer.append("\tcause : {\n");
			buffer.append("\t\tclass : '").append(exception.getClass().getSimpleName()).append("',\n");
			buffer.append("\t\ttext : '").append(exception.getLocalizedMessage()).append("',\n");
//			StringWriter sw = new StringWriter();
//			exception.printStackTrace(new PrintWriter(sw));
			buffer.append("\t\tstack trace :\n").append(getStackTraceAsString(exception));
			buffer.append("\t}\n");
		}		
		buffer.append("}\n");
		return buffer.toString();
	}
	
	public static String getStackTraceAsString(Throwable th) { 
		StringWriter sw = new StringWriter();
		th.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
