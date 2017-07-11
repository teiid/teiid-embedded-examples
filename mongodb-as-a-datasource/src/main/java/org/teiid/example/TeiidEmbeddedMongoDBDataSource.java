/*
 * Copyright Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags and
 * the COPYRIGHT.txt file distributed with this work.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.teiid.example;

import static org.teiid.example.JDBCUtils.execute;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.teiid.resource.adapter.mongodb.MongoDBManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.mongodb.MongoDBExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedMongoDBDataSource {
	
	private static String SERVERLIST = "127.0.0.1:27017" ;
	private static String DBNAME = "test" ;

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
    	
		server.deployVDB(TeiidEmbeddedMongoDBDataSource.class.getClassLoader().getResourceAsStream("mongodb-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:nothwind", null);
		
		execute(c, "DELETE FROM Employee", false);
		execute(c, "INSERT INTO Employee(employee_id, FirstName, LastName) VALUES (1, 'Test1', 'Test1')", false);
		execute(c, "INSERT INTO Employee(employee_id, FirstName, LastName) VALUES (2, 'Test2', 'Test2')", false);
		execute(c, "INSERT INTO Employee(employee_id, FirstName, LastName) VALUES (3, 'Test3', 'Test3')", false);
		execute(c, "SELECT * FROM Employee", true);
		
		server.stop();
	}

	private static void initMongoProperties() throws IOException {

		Properties prop = new Properties();
        InputStream in = TeiidEmbeddedMongoDBDataSource.class.getClassLoader().getResourceAsStream("mongodb.properties");
        prop.load(in);
        in.close();
		SERVERLIST = prop.getProperty("server.list", SERVERLIST);
		DBNAME = prop.getProperty("db.name", DBNAME);
	}

}
