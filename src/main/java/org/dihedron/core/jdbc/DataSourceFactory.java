/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;

import org.dihedron.core.License;
import org.dihedron.core.url.URLFactory;

/**
 * @author Andrea Funto'
 */
@License
public class DataSourceFactory {
	
	private static Properties drivers = new Properties();
	private static Properties datasources = new Properties();
	static {		
		try(InputStream f1 = URLFactory.makeURL("classpath:org/dihedron/core/jdbc/drivers.configuration").openStream();
			InputStream f2 = URLFactory.makeURL("classpath:org/dihedron/core/jdbc/datasources.configuration").openStream()) {
			drivers.load(f1);
			datasources.load(f2);

		}catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
