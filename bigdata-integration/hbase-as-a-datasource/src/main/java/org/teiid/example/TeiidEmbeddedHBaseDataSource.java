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

import java.sql.Connection;

import javax.sql.DataSource;

import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.phoenix.PhoenixExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedHBaseDataSource {
	

	public static void main(String[] args) throws Exception {
		
		DataSource ds = EmbeddedHelper.newDataSource(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASS, "phoenix.connection.autoCommit=true");
		
		initSamplesData(ds);
		
		EmbeddedServer server = new EmbeddedServer();
		
		PhoenixExecutionFactory factory = new PhoenixExecutionFactory();
		factory.start();
		server.addTranslator("translator-hbase", factory);
		
		server.addConnectionFactory("java:/hbaseDS", ds);
		
		EmbeddedConfiguration config = new EmbeddedConfiguration();
		config.setTransactionManager(EmbeddedHelper.getTransactionManager());	
		server.start(config);
    	
		server.deployVDB(TeiidEmbeddedHBaseDataSource.class.getClassLoader().getResourceAsStream("hbase-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:hbasevdb", null);
		
		execute(c, "SELECT * FROM Customer", false);
		execute(c, "SELECT * FROM Customer ORDER BY name, city DESC", true);
		
		server.stop();
	}
	
	private static void initSamplesData(DataSource ds) throws Exception {
		Connection conn = ds.getConnection();
		// init test data
		execute(conn, CUSTOMER, false);
		execute(conn, "UPSERT INTO \"Customer\" VALUES('101', 'Los Angeles, CA', 'John White', '$400.00', 'Chairs')", false);
		execute(conn, "UPSERT INTO \"Customer\" VALUES('102', 'Atlanta, GA', 'Jane Brown', '$200.00', 'Lamps')", false);
		execute(conn, "UPSERT INTO \"Customer\" VALUES('103', 'Pittsburgh, PA', 'Bill Green', '$500.00', 'Desk')", false);
		execute(conn, "UPSERT INTO \"Customer\" VALUES('104', 'St. Louis, MO', 'Jack Black', '$8000.00', 'Bed')", false);
		execute(conn, "UPSERT INTO \"Customer\" VALUES('105', 'Los Angeles, CA', 'John White', '$400.00', 'Chairs')", true);
	}

	static final String CUSTOMER = "CREATE TABLE IF NOT EXISTS \"Customer\"(\"ROW_ID\" VARCHAR PRIMARY KEY, \"customer\".\"city\" VARCHAR, \"customer\".\"name\" VARCHAR, \"sales\".\"amount\" VARCHAR, \"sales\".\"product\" VARCHAR)";
	
	static final String JDBC_DRIVER = "org.apache.phoenix.jdbc.PhoenixDriver";
    static final String JDBC_URL = "jdbc:phoenix:127.0.0.1:2181";
    static final String JDBC_USER = "";
    static final String JDBC_PASS = "";


}
