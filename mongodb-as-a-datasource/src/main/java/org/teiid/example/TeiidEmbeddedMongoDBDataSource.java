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
import static org.teiid.example.util.IOUtils.findFile;
import static org.teiid.example.util.IOUtils.findProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import org.teiid.resource.adapter.mongodb.MongoDBManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.mongodb.MongoDBExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedMongoDBDataSource {
	
	private static String SERVERLIST = "127.0.0.1:27017" ;
	private static String DBNAME = "mydb" ;

	public static void main(String[] args) throws Exception {
		
		initMongoProperties();
		
		EmbeddedServer server = new EmbeddedServer();
		
		MongoDBExecutionFactory factory = new MongoDBExecutionFactory();
		factory.start();
		server.addTranslator("translator-mongodb", factory);
		
		MongoDBManagedConnectionFactory managedconnectionFactory = new MongoDBManagedConnectionFactory();
		managedconnectionFactory.setRemoteServerList(SERVERLIST);
		managedconnectionFactory.setDatabase(DBNAME);
		server.addConnectionFactory("java:/mongoDS", managedconnectionFactory.createConnectionFactory());

		server.start(new EmbeddedConfiguration());
    	
		server.deployVDB(new FileInputStream(findFile("mongodb-vdb.xml")));
		
		Connection c = server.getDriver().connect("jdbc:teiid:nothwind", null);
		
		execute(c, "DELETE FROM Employee", false);
		execute(c, "INSERT INTO Employee(employee_id, FirstName, LastName) VALUES (1, 'Test1', 'Test1')", false);
		execute(c, "INSERT INTO Employee(employee_id, FirstName, LastName) VALUES (2, 'Test2', 'Test2')", false);
		execute(c, "INSERT INTO Employee(employee_id, FirstName, LastName) VALUES (3, 'Test3', 'Test3')", false);
		execute(c, "SELECT * FROM Employee", true);
		
		server.stop();
	}

	private static void initMongoProperties() throws IOException {
		
		Properties prop = findProperties("mongodb.properties");
		SERVERLIST = prop.getProperty("server.list", SERVERLIST);
		DBNAME = prop.getProperty("db.name", DBNAME);
	}

}
