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

import static org.teiid.example.H2PERFTESTClient.*;
import static org.teiid.example.TeiidEmbeddedCaching.*;
import static org.teiid.example.util.JDBCUtils.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.resource.ResourceException;
import javax.sql.DataSource;

import org.teiid.deployers.VirtualDatabaseException;
import org.teiid.dqp.internal.datamgr.ConnectorManagerRepository.ConnectorManagerException;
import org.teiid.example.EmbeddedHelper;
import org.teiid.example.util.JDBCUtils;
import org.teiid.example.util.JDBCUtils.Entity;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;

public class ResultsCachingExample {
    
    static EmbeddedServer server = null;
    static Connection conn = null;
    
    static void startup() throws TranslatorException, VirtualDatabaseException, ConnectorManagerException, IOException, SQLException, ResourceException {
                
        server = new EmbeddedServer();
        
        H2ExecutionFactory factory = new H2ExecutionFactory();
        factory.start();
        factory.setSupportsDirectQueryProcedure(true);
        server.addTranslator("translator-h2", factory);
        
        DataSource ds = EmbeddedHelper.newDataSource(H2_JDBC_DRIVER, H2_JDBC_URL, H2_JDBC_USER, H2_JDBC_PASS);
        server.addConnectionFactory("java:/accounts-ds", ds);
        
        server.start(new EmbeddedConfiguration());
        
        server.deployVDB(ResultsCachingExample.class.getClassLoader().getResourceAsStream("rsCaching-h2-vdb.xml"));
        
        Properties info = new Properties();
        conn = server.getDriver().connect("jdbc:teiid:ResultsCachingH2VDB", info);
    }
    
    static void teardown() throws SQLException {
        JDBCUtils.close(conn);
        server.stop();
    }
    
   
    public static void query(String sql) throws Exception {
        
        startup();
        
        prompt("Execute '" + sql + "' 10 times, this may need some times");
       
        for(int i = 0 ; i < 10 ; i ++) {
            Entity entity = executeQueryCount(conn, sql);
            println("\t" + (i + 1) + ". Query Time: " + entity.getQueryTime());
        }
                
        teardown();
    }
    
    public static void main(String[] args) throws Exception {
        
        query("SELECT * FROM PERFTEST");
        query("SELECT * FROM PERFTESTVIEW");
        query("/*+ cache */ SELECT * FROM PERFTESTVIEW");
        
        println("");
        
    }
    
}
