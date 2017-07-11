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
import org.teiid.translator.jdbc.vertica.VerticaExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedVerticaDataSource {
    
    static String JDBC_DRIVER = "com.vertica.jdbc.Driver";
    static String JDBC_URL = "jdbc:vertica://127.0.0.1:5433/VMart";
    static String JDBC_USER = "dbadmin";
    static String JDBC_PASS = "redhat";
	

	public static void main(String[] args) throws Exception {
	    
	    initDBProperties();
		
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


    private static void initDBProperties() throws IOException {
        Properties prop = new Properties();
        InputStream in = TeiidEmbeddedVerticaDataSource.class.getClassLoader().getResourceAsStream("vertica.properties");
        prop.load(in);
        in.close();
        JDBC_DRIVER = prop.getProperty("vertica.driver", JDBC_DRIVER);
        JDBC_URL = prop.getProperty("vertica.url", JDBC_URL);
        JDBC_USER = prop.getProperty("vertica.user", JDBC_USER);
        JDBC_PASS = prop.getProperty("vertica.pass", JDBC_PASS);
    }
	

	


}
