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

import static org.teiid.example.JDBCUtils.close;
import static org.teiid.example.TeiidEmbeddedRestInvokeHTTP.getStringFromStream;

import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.resource.ResourceException;

import org.apache.cxf.helpers.IOUtils;
import org.teiid.deployers.VirtualDatabaseException;
import org.teiid.dqp.internal.datamgr.ConnectorManagerRepository.ConnectorManagerException;
import org.teiid.resource.adapter.ws.WSManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.swagger.SwaggerExecutionFactory;


/**
 *  This dependent on swagger translator and ws resource adapter.
 *  
 * @author kylin
 *
 */
public class TeiidEmbeddedRestSwaggerProcedure {
    
    private static String[] getCalls = new String[] {"EXEC customer_getByNumCityCountry('161', 'Burlingame', 'USA', jsonObject('application/json' as ContentType, jsonArray('gzip', 'deflate') as \"Accept-Encoding\"))",
                                                     "EXEC customer_customerList()",
                                                     "EXEC customer_getAll()",
                                                     "EXEC customer_getByCity('Burlingame')",
                                                     "EXEC customer_getByCountry('USA')",
                                                     "EXEC customer_getByName('Technics Stores Inc.')",
                                                     "EXEC customer_getByNumber('161')",
                                                     "EXEC customer_status()"};
    
    private static String[] delCalls = new String[] {"EXEC customer_delete('103')",
                                                     "EXEC customer_deleteByNumber('103')",
                                                     "EXEC customer_deleteByName('kylin')",
                                                     "EXEC customer_deleteByCity('Beijing')",
                                                     "EXEC customer_deleteByCountry('China')",
                                                     "EXEC customer_deleteByNumCityCountry('103', 'Beijing', 'China')"};
    
    private static String postJson = "{\"customernumber\":\"100\",\"customername\":\"kylin\",\"contactlastname\":\"Soong\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
    private static String postXml  = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><customer><addressline1>Beijing</addressline1><addressline2>China Beijing</addressline2><city>Beijing</city><contactfirstname>Kylin</contactfirstname><contactlastname>Soong</contactlastname><country>China</country><creditlimit>1200</creditlimit><customername>kylin</customername><customernumber>101</customernumber><phone>18611907049</phone><postalcode>100020</postalcode><salesrepemployeenumber>123456</salesrepemployeenumber><state>Beijing</state></customer>";
    
    private static String[] postCalls = new String[]{"EXEC customer('" + postJson +"')", 
                                                     "EXEC customer('" + postXml +"')"};

    public static void main(String[] args) throws ResourceException, TranslatorException, VirtualDatabaseException, ConnectorManagerException, IOException, SQLException {
        
        EmbeddedServer server = new EmbeddedServer();
        
        SwaggerExecutionFactory factory = new SwaggerExecutionFactory();
        factory.start();
        server.addTranslator("translator-rest", factory);
        
        WSManagedConnectionFactory managedconnectionFactory = new WSManagedConnectionFactory();
        managedconnectionFactory.setSwaggerUrl("http://localhost:8080/swagger.json");
        server.addConnectionFactory("java:/CustomerRESTWebSvcSource", managedconnectionFactory.createConnectionFactory());

        server.start(new EmbeddedConfiguration());
        
        server.deployVDB(TeiidEmbeddedRestSwaggerProcedure.class.getClassLoader().getResourceAsStream("restwebservice-swagger-vdb.xml"));
        
        Connection conn = server.getDriver().connect("jdbc:teiid:restwebservice", null);
        
//        for(String call : getCalls) {
//            CallableStatement cStmt = conn.prepareCall(call);
//            cStmt.execute();
//            Blob blob = (Blob) cStmt.getObject(1);
//            IOUtils.copy(blob.getBinaryStream(), System.out);
//            System.out.println();
//        }
        
//        for(String call : delCalls) {
//            CallableStatement cStmt = conn.prepareCall(call);
//            cStmt.execute();
//            Blob blob = (Blob) cStmt.getObject(1);
//            IOUtils.copy(blob.getBinaryStream(), System.out);
//            System.out.println();
//        }
        
        CallableStatement cStmt = conn.prepareCall(postCalls[1]);
        cStmt.execute();
        Blob blob = (Blob) cStmt.getObject(1);
        IOUtils.copy(blob.getBinaryStream(), System.out);
        System.out.println();
//        System.out.println(getStringFromStream(blob.getBinaryStream()));
        
        close(conn);
        server.stop();

    }

}
