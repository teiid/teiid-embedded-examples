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
import static org.teiid.example.TeiidEmbeddedCaching.*;
import static org.teiid.example.util.JDBCUtils.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.teiid.example.EmbeddedHelper;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;

public class ExternalMaterializationExample {
    
    static EmbeddedServer server = null;
    static Connection conn = null;
    
    static final String MSG = "wait for loadMatView() finish refresh external materialized table";
    
    static void startup() throws Exception {
        
        
        DataSource ds = EmbeddedHelper.newDataSource(H2_JDBC_DRIVER, H2_JDBC_URL, H2_JDBC_USER, H2_JDBC_PASS);
        
        server = new EmbeddedServer();
        
        H2ExecutionFactory factory = new H2ExecutionFactory();
        factory.start();
        factory.setSupportsDirectQueryProcedure(true);
        server.addTranslator("translator-h2", factory);
        
        server.addConnectionFactory("java:/accounts-ds", ds);
        
        EmbeddedConfiguration config = new EmbeddedConfiguration();
        config.setTransactionManager(EmbeddedHelper.getTransactionManager());
        server.start(config);
                
        server.deployVDB(ExternalMaterializationExample.class.getClassLoader().getResourceAsStream("mat-h2-vdb.xml"));
        
        Properties info = new Properties();
        conn = server.getDriver().connect("jdbc:teiid:MatVDB", info);
        
    }
    
    static void teardown() throws SQLException {
        close(conn);
        server.stop();
    }

    public static void main(String[] args) throws Exception {

        startup();
        
        sleep(MSG, 5000L);
        
        execute(conn, "select * from MatView", false);
        
        execute(conn, "INSERT INTO PRODUCT (ID,SYMBOL,COMPANY_NAME) VALUES(2000,'RHT','Red Hat Inc')", false);
        
        sleep(MSG, 20000L);
        
        execute(conn, "select * from MatView", false);
        
        execute(conn, "DELETE FROM PRODUCT  WHERE ID = 2000", false);
        
        sleep(MSG, 20000L);
        
        execute(conn, "select * from MatView", false);
        
        teardown();
        
        println("");
        
    }

}
