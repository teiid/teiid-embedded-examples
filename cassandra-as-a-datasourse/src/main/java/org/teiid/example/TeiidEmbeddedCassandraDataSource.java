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

import org.teiid.resource.adapter.cassandra.CassandraManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.cassandra.CassandraExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedCassandraDataSource {
	
	private static String ADDRESS = "127.0.0.1";
	private static String KEYSPACE = "demo";

	public static void main(String[] args) throws Exception {
		
		initCassandraProperties();
		
		EmbeddedServer server = new EmbeddedServer();
		
		CassandraExecutionFactory factory = new CassandraExecutionFactory();
		factory.start();
		server.addTranslator("translator-cassandra", factory);
		
		CassandraManagedConnectionFactory managedconnectionFactory = new CassandraManagedConnectionFactory();
		managedconnectionFactory.setAddress(ADDRESS);
		managedconnectionFactory.setKeyspace(KEYSPACE);
		server.addConnectionFactory("java:/demoCassandra", managedconnectionFactory.createConnectionFactory());

		server.start(new EmbeddedConfiguration());
    	
		server.deployVDB(TeiidEmbeddedCassandraDataSource.class.getClassLoader().getResourceAsStream("cassandra-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:users", null);
		
		execute(c, "SELECT * FROM UsersView", true);
		
		server.stop();
	}

	private static void initCassandraProperties() throws IOException {
		
		Properties prop = new Properties();
        InputStream in = TeiidEmbeddedCassandraDataSource.class.getClassLoader().getResourceAsStream("cassandra.properties");
        prop.load(in);
        in.close();
		ADDRESS = prop.getProperty("cassandra.address", ADDRESS);
		KEYSPACE = prop.getProperty("cassandra.keyspace", KEYSPACE);
	}

}
