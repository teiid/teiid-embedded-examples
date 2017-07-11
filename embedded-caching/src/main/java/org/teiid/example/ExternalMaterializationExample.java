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
