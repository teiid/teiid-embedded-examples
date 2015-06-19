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

import java.sql.Connection;

import javax.sql.DataSource;

import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.jdbc.vertica.VerticaExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedVerticaDataSource {
    
    static final String JDBC_DRIVER = "com.vertica.jdbc.Driver";
    static final String JDBC_URL = "jdbc:vertica://192.168.1.105:5433/VMart";
    static final String JDBC_USER = "dbadmin";
    static final String JDBC_PASS = "redhat";
	

	public static void main(String[] args) throws Exception {
		
		DataSource ds = EmbeddedHelper.newDataSource(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASS);
				
		EmbeddedServer server = new EmbeddedServer();
		
		VerticaExecutionFactory factory = new VerticaExecutionFactory();
		factory.start();
		server.addTranslator("translator-vertica", factory);
		
		server.addConnectionFactory("java:/verticaDS", ds);
		
		EmbeddedConfiguration config = new EmbeddedConfiguration();
		config.setTransactionManager(EmbeddedHelper.getTransactionManager());	
		server.start(config);
    	
		server.deployVDB(TeiidEmbeddedVerticaDataSource.class.getClassLoader().getResourceAsStream("vertica-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:VerticaVDB", null);
		
		execute(c, "SELECT * FROM employee_dimension_view", true);
		
		server.stop();
	}
	

	


}
