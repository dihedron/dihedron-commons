/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;

import org.dihedron.core.License;
import org.dihedron.core.strings.Strings;
import org.dihedron.core.url.URLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class DataSourceFactory {
	
	/**
	 * The name of the system property or environment variable holding the 
	 * drivers configuration.
	 */
	public final static String DRIVERS_CONFIGURATION = "drivers.configuration";

	/**
	 * The name of the system property or environment variable holding the 
	 * datasources configuration.
	 */
	public final static String DATASOURCES_CONFIGURATION = "datasources.configuration";
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);
	
	private static Properties drivers = new Properties();
	private static Properties datasources = new Properties();
	static {
		// check if "drivers.configuration" and "datasources.configuration" system 
		// properties or environment variables are available and if so use them, 
		// otherwise fall back to default, built in configuration files
		
		String driversConfiguration = System.getProperty(DRIVERS_CONFIGURATION, System.getenv(DRIVERS_CONFIGURATION));
		if(!Strings.isValid(driversConfiguration)) {
			driversConfiguration = "classpath:org/dihedron/core/jdbc/drivers.configuration";
		}
		logger.info("loading drivers configuration from {}", driversConfiguration);
		
		String datasourcesConfiguration = System.getProperty(DATASOURCES_CONFIGURATION, System.getenv(DATASOURCES_CONFIGURATION));
		if(!Strings.isValid(datasourcesConfiguration)) {
			datasourcesConfiguration = "classpath:org/dihedron/core/jdbc/datasources.configuration";
		}
		logger.info("loading datasources configuration from {}", datasourcesConfiguration);
		
		
		try(InputStream f1 = URLFactory.makeURL(driversConfiguration).openStream();
			InputStream f2 = URLFactory.makeURL(datasourcesConfiguration).openStream()) {
			drivers.load(f1);
			datasources.load(f2);
		} catch (MalformedURLException e) {			
			logger.error("invalid URL for driver or datasource configuration", e);
		} catch (IOException e) {
			logger.error("error reading driver or datasource configuration", e);
		}
	}
	
	public static DataSource makeDataSource(String dsname) {
		assert(dsname != null);

		// hc1.type=oracle
		// hc1.host=saphc1
		// hc1.port=1532
		// hc1.instance=hc1
		// oracle.driver=oracle.jdbc.driver.OracleDriver
		// oracle.address=jdbc:oracle:thin:@${host}:${port}:${instance}
		dsname = dsname.toLowerCase();
				
		String host = datasources.getProperty(dsname + ".host");
		String port = datasources.getProperty(dsname + ".port");
		String instance = datasources.getProperty(dsname + ".instance");
		String type = datasources.getProperty(dsname + ".type");
		String driver = drivers.getProperty(type + ".driver");
		String address = drivers.getProperty(type + ".address");
		
		return new DataSource(host, port, instance, driver, address);
	}
}
