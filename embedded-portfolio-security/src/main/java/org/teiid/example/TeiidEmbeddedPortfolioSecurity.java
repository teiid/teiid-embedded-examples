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

import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;

import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.teiid.jdbc.TeiidSQLException;
import org.teiid.resource.adapter.file.FileManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.file.FileExecutionFactory;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;


/**
 * This example is show security authentication in Teiid Embedded
 */
@SuppressWarnings("nls")
public class TeiidEmbeddedPortfolioSecurity {
		
	public static void main(String[] args) throws Exception {
	    
	    EmbeddedHelper.enableLogger(Level.OFF);
		
		DataSource ds = EmbeddedHelper.newDataSource("org.h2.Driver", "jdbc:h2:mem://localhost/~/account", "sa", "sa");
		RunScript.execute(ds.getConnection(), new InputStreamReader(TeiidEmbeddedPortfolioSecurity.class.getClassLoader().getResourceAsStream("data/customer-schema.sql")));
		
		EmbeddedServer server = new EmbeddedServer();
		
		H2ExecutionFactory executionFactory = new H2ExecutionFactory() ;
		executionFactory.setSupportsDirectQueryProcedure(true);
		executionFactory.start();
		server.addTranslator("translator-h2", executionFactory);
		
		server.addConnectionFactory("java:/accounts-ds", ds);
		
    	FileExecutionFactory fileExecutionFactory = new FileExecutionFactory();
    	fileExecutionFactory.start();
    	server.addTranslator("file", fileExecutionFactory);
    	
    	FileManagedConnectionFactory managedconnectionFactory = new FileManagedConnectionFactory();
		managedconnectionFactory.setParentDirectory("src/main/resources/data");
		server.addConnectionFactory("java:/marketdata-file", managedconnectionFactory.createConnectionFactory());
	
		EmbeddedConfiguration config = new EmbeddedConfiguration();
		config.setTransactionManager(EmbeddedHelper.getTransactionManager());
		config.setSecurityDomain("teiid-security-file");
		config.setSecurityHelper(EmbeddedHelper.getSecurityHelper());
		server.start(config);
				
    	
		server.deployVDB(TeiidEmbeddedPortfolioSecurity.class.getClassLoader().getResourceAsStream("portfolio-vdb.xml"));
		
		System.out.println("\t\n A successful security logon...\n");
		
		Properties info = new Properties();
		info.put("user", "testUser");
		info.put("password", "password");
		Connection c = server.getDriver().connect("jdbc:teiid:Portfolio;version=1", info);
				
		execute(c, "select * from Stock", true);
		
		System.out.println("\t\n A unsuccess security logon...\n");
		
		try {
            info.put("password", "password-error");
            server.getDriver().connect("jdbc:teiid:Portfolio;version=1", info);
        } catch (TeiidSQLException e) {
            e.printStackTrace();
        }
				
	}

}
