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
	    Properties prop = new Properties();
	    InputStream in = TeiidEmbeddedHadoopDataSource.class.getClassLoader().getResourceAsStream("hive.properties");
	    prop.load(in);
	    in.close();
        JDBC_DRIVER = prop.getProperty("hive.driver", JDBC_DRIVER);
        JDBC_URL = prop.getProperty("hive.url", JDBC_URL);
        JDBC_USER = prop.getProperty("hive.user", JDBC_USER);
        JDBC_PASS = prop.getProperty("hive.pass", JDBC_PASS);
    }

}
