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

import static org.teiid.example.H2PERFTESTClient.*;
import static org.teiid.example.TeiidEmbeddedCaching.println;
import static org.teiid.example.TeiidEmbeddedCaching.prompt;
import static org.teiid.example.util.JDBCUtils.executeQueryCount;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import javax.resource.ResourceException;
import javax.sql.DataSource;

import org.teiid.deployers.VirtualDatabaseException;
import org.teiid.dqp.internal.datamgr.ConnectorManagerRepository.ConnectorManagerException;
import org.teiid.example.EmbeddedHelper;
import org.teiid.example.util.JDBCUtils;
import org.teiid.example.util.JDBCUtils.Entity;
import org.teiid.language.Command;
import org.teiid.language.QueryExpression;
import org.teiid.metadata.RuntimeMetadata;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.CacheDirective;
import org.teiid.translator.ExecutionContext;
import org.teiid.translator.ResultSetExecution;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;

public class TranslatorResultsCachingExample {
    
    static EmbeddedServer server = null;
    static Connection conn = null;
    
    static class TestH2ExecutionFactory extends H2ExecutionFactory {

        @Override
        public ResultSetExecution createResultSetExecution(
                QueryExpression command, ExecutionContext executionContext,
                RuntimeMetadata metadata, Connection conn)
                throws TranslatorException {

            CacheDirective cacheDirective = this.getCacheDirective(command, executionContext, metadata);
            cacheDirective.setScope(CacheDirective.Scope.VDB);
            cacheDirective.setPrefersMemory(true);
            cacheDirective.setReadAll(true);
            cacheDirective.setTtl(120000L);
            cacheDirective.setUpdatable(true);
            
            return super.createResultSetExecution(command, executionContext, metadata, conn);
        }

        @Override
        public CacheDirective getCacheDirective(Command command,
                ExecutionContext executionContext, RuntimeMetadata metadata)
                throws TranslatorException {
            
            return new CacheDirective();
        }
        
    }
    
    static void startup() throws TranslatorException, VirtualDatabaseException, ConnectorManagerException, IOException, SQLException, ResourceException {
        
        EmbeddedHelper.enableLogger(Level.INFO);
        
        server = new EmbeddedServer();
        
        H2ExecutionFactory factory = new TestH2ExecutionFactory();
        factory.start();
        factory.setSupportsDirectQueryProcedure(true);
        server.addTranslator("translator-h2", factory);
        
        DataSource ds = EmbeddedHelper.newDataSource(H2_JDBC_DRIVER, H2_JDBC_URL, H2_JDBC_USER, H2_JDBC_PASS);
        server.addConnectionFactory("java:/accounts-ds", ds);
        
        server.start(new EmbeddedConfiguration());
        
        server.deployVDB(TranslatorResultsCachingExample.class.getClassLoader().getResourceAsStream("rsCaching-h2-vdb.xml"));
        
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
        startup();
        query("SELECT * FROM PERFTESTVIEW");
        teardown();
        println("");
    }
    


}
