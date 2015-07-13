/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.teiid.example;

import static org.teiid.example.util.JDBCUtils.execute;
import static org.teiid.example.util.IOUtils.findProperties;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.hive.HiveExecutionFactory;

public class TeiidEmbeddedHadoopDataSource {
    
    static String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
    static String JDBC_URL = "jdbc:hive2://127.0.0.1:10000/default";
    static String JDBC_USER = "hive";
    static String JDBC_PASS = "";
	

	public static void main(String[] args) throws Exception {
	    
	    initDBProperties();
		
		DataSource ds = EmbeddedHelper.newDataSource(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASS);
				
		EmbeddedServer server = new EmbeddedServer();
		
		HiveExecutionFactory factory = new HiveExecutionFactory();
		factory.start();
		server.addTranslator("translator-hive", factory);
		
		server.addConnectionFactory("java:/hiveDS", ds);
		
		EmbeddedConfiguration config = new EmbeddedConfiguration();
		config.setTransactionManager(EmbeddedHelper.getTransactionManager());	
		server.start(config);
    	
		server.deployVDB(TeiidEmbeddedHadoopDataSource.class.getClassLoader().getResourceAsStream("hive-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:hivevdb", null);
		
		execute(c, "SELECT * FROM EMPLOYEEVIEW", true);
		
		server.stop();
	}
	
	private static void initDBProperties() throws IOException {
	    Properties prop = findProperties("hive.properties");
        JDBC_DRIVER = prop.getProperty("hive.driver", JDBC_DRIVER);
        JDBC_URL = prop.getProperty("hive.url", JDBC_URL);
        JDBC_USER = prop.getProperty("hive.user", JDBC_USER);
        JDBC_PASS = prop.getProperty("hive.pass", JDBC_PASS);
    }

}
