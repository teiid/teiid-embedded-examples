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

import static org.teiid.example.util.JDBCUtils.close;
import static org.teiid.example.TeiidEmbeddedRestInvokeHTTP.getStringFromStream;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.resource.ResourceException;

import org.teiid.deployers.VirtualDatabaseException;
import org.teiid.dqp.internal.datamgr.ConnectorManagerRepository.ConnectorManagerException;
import org.teiid.resource.adapter.ws.WSManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.ws.WSExecutionFactory;
import org.teiid.translator.ws.BinaryWSProcedureExecution.StreamingBlob;

/**
 *  This dependent on swagger translator and ws resource adapter.
 *  
 * @author kylin
 *
 */
public class TeiidEmbeddedRestSwaggerProcedure {
    
    private static String[] calls = new String[] {"EXEC customer_customerList()",
                                                  "EXEC customer_getAll()",
                                                  "EXEC customer_getByNumber('161')",
                                                  "EXEC customer_getByCity('Burlingame')",
                                                  "EXEC customer_getByCountry('USA')"};

    public static void main(String[] args) throws ResourceException, TranslatorException, VirtualDatabaseException, ConnectorManagerException, IOException, SQLException {
        
        EmbeddedServer server = new EmbeddedServer();
        
        WSExecutionFactory factory = new WSExecutionFactory();
        factory.start();
        server.addTranslator("translator-rest", factory);
        
        WSManagedConnectionFactory managedconnectionFactory = new WSManagedConnectionFactory();
        managedconnectionFactory.setSwaggerUrl("http://localhost:8080/swagger.json");
        server.addConnectionFactory("java:/CustomerRESTWebSvcSource", managedconnectionFactory.createConnectionFactory());

        server.start(new EmbeddedConfiguration());
        
        server.deployVDB(TeiidEmbeddedRestSwaggerProcedure.class.getClassLoader().getResourceAsStream("restwebservice-swagger-vdb.xml"));
        
        Connection conn = server.getDriver().connect("jdbc:teiid:restwebservice", null);
        
        for(String call : calls) {
            CallableStatement cStmt = conn.prepareCall(call);
            cStmt.execute();
            StreamingBlob blob = (StreamingBlob) cStmt.getObject(1);
            System.out.println(getStringFromStream(blob.getBinaryStream()));
        }
        
        close(conn);
        server.stop();

    }

}
