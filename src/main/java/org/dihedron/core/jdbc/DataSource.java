/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.dihedron.core.Credentials;
import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class DataSource {
	
	/**
	 * The logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(DataSource.class);
	
	private String host;	
	private String port;
	private String instance;
	private String driver;
	private String address;
	
	public DataSource(String host, String port, String instance, String driver, String address) {
		this.host = host;
		this.port = port;
		this.instance = instance;
		this.driver = driver;
		this.address = address;
	}
	

	public Connection getConnection(Credentials credentials) {
		return getConnection(credentials.getUsername(), credentials.getPassword());
	}

	public Connection getConnection(String username, String password) {
		Connection connection = null;
		logger.debug("trying to get connection to database using " + driver);
		try {
			Class.forName(driver); // or any other driver
			logger.debug("driver loaded");
			
			String url = address
					.replaceAll("\\$\\{host\\}", host)
					.replaceAll("\\$\\{port\\}", port)
					.replaceAll("\\$\\{instance\\}", instance);
			logger.debug("connection using " + url);
			if(username == null && password == null) {
				connection = DriverManager.getConnection(url);
			} else {
				connection = DriverManager.getConnection(url, username, password);
			}
		} catch(Exception e){
			logger.error("unable to load the driver class", e);
		}		
		return connection;
	}
}
