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

import static org.teiid.example.JDBCUtils.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.prestodb.PrestoDBExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedPrestoDataSource {
    
    static String JDBC_DRIVER = "com.facebook.presto.jdbc.PrestoDriver";
    static String JDBC_URL = "jdbc:presto://localhost:8080";
    static String JDBC_USER = "sa";
    static String JDBC_PASS = "sa";
	

	public static void main(String[] args) throws Exception {
	    
	    initDBProperties();
		
		DataSource ds = EmbeddedHelper.newDataSource(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASS);
				
		EmbeddedServer server = new EmbeddedServer();
		
		PrestoDBExecutionFactory factory = new PrestoDBExecutionFactory();
		factory.start();
		server.addTranslator("translator-prestodb", factory);
		
		server.addConnectionFactory("java:/prestoDS", ds);
		
		EmbeddedConfiguration config = new EmbeddedConfiguration();
		config.setTransactionManager(EmbeddedHelper.getTransactionManager());	
		server.start(config);
    	
		server.deployVDB(TeiidEmbeddedPrestoDataSource.class.getClassLoader().getResourceAsStream("presto-vdb.xml"));
		
		Connection conn = server.getDriver().connect("jdbc:teiid:PrestoDB", null);
		
		//DML
		
		close(conn);
		
		server.stop();
	}


    private static void initDBProperties() throws IOException {
        Properties prop = new Properties();
        InputStream in = TeiidEmbeddedPrestoDataSource.class.getClassLoader().getResourceAsStream("presto.properties");
        prop.load(in);
        in.close();
        JDBC_DRIVER = prop.getProperty("presto.driver", JDBC_DRIVER);
        JDBC_URL = prop.getProperty("presto.url", JDBC_URL);
        JDBC_USER = prop.getProperty("presto.user", JDBC_USER);
        JDBC_PASS = prop.getProperty("presto.pass", JDBC_PASS);
    }
	

	


}
