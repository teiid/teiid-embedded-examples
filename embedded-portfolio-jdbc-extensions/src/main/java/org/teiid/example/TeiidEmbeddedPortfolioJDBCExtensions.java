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

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.teiid.jdbc.RequestOptions;
import org.teiid.jdbc.StatementCallback;
import org.teiid.jdbc.TeiidPreparedStatement;
import org.teiid.resource.adapter.file.FileManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.file.FileExecutionFactory;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedPortfolioJDBCExtensions {
	
    static String sql = "select * from Stock Order by product_id";
	
	public static void main(String[] args) throws Exception {		
		
		DataSource ds = EmbeddedHelper.newDataSource("org.h2.Driver", "jdbc:h2:mem://localhost/~/account", "sa", "sa");
		RunScript.execute(ds.getConnection(), new InputStreamReader(TeiidEmbeddedPortfolioJDBCExtensions.class.getClassLoader().getResourceAsStream("data/customer-schema.sql")));
		
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
		server.start(config);
    	
		server.deployVDB(TeiidEmbeddedPortfolioJDBCExtensions.class.getClassLoader().getResourceAsStream("portfolio-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:Portfolio", null);
		
		testNonBlockingExecution(c);
		
		testContinuousExecution(c);
		
		close(c);

	}
	
	static void testContinuousExecution(Connection connection) throws SQLException, InterruptedException {

	    System.out.println("JDBC Extensions Continuous Execution");
        
        PreparedStatement stmt = connection.prepareStatement(sql);
        TeiidPreparedStatement tStmt = stmt.unwrap(TeiidPreparedStatement.class);
        RequestOptions requestOptions = new RequestOptions();
        tStmt.submitExecute(new StatementCallback(){

            int times = 1;
            @Override
            public void onRow(Statement s, ResultSet rs) throws Exception {
               
                System.out.println(times++ + ": " + rs.getObject(1) + ", " + rs.getObject(2) + ", " + rs.getObject(3) + ", " + rs.getObject(4));
            }

            @Override
            public void onException(Statement s, Exception e) throws Exception {
                s.close();
            }

            @Override
            public void onComplete(Statement s) throws Exception {
                s.close();
            }}, requestOptions.continuous(true));
        
        
        //wait callback be executed
        Thread.sleep(500); 
    }

    static void testNonBlockingExecution(Connection connection) throws SQLException, InterruptedException{
	    
	    System.out.println("JDBC Extensions Blocking Statement Execution");
	    
	    PreparedStatement stmt = connection.prepareStatement(sql);
        TeiidPreparedStatement tStmt = stmt.unwrap(TeiidPreparedStatement.class);
        tStmt.submitExecute(new StatementCallback(){

            int times = 1;
            @Override
            public void onRow(Statement s, ResultSet rs) throws Exception {
                System.out.println(times++ + ": " + rs.getObject(1) + ", " + rs.getObject(2) + ", " + rs.getObject(3) + ", " + rs.getObject(4));
            }

            @Override
            public void onException(Statement s, Exception e) throws Exception {
                s.close();
            }

            @Override
            public void onComplete(Statement s) throws Exception {
                s.close();
            }}, new RequestOptions());
        
        
        //wait callback be executed
        Thread.sleep(500); 
	}



}
