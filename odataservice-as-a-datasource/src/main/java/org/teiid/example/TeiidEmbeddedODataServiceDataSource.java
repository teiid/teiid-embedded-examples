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

import static org.teiid.example.util.JDBCUtils.execute;
import static org.teiid.example.util.IOUtils.findFile;

import java.io.FileInputStream;
import java.sql.Connection;

import org.teiid.resource.adapter.ws.WSManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.odata.ODataExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedODataServiceDataSource {

	public static void main(String[] args) throws Exception {
		
		EmbeddedServer server = new EmbeddedServer();
		
		ODataExecutionFactory factory = new ODataExecutionFactory();
		factory.start();
		server.addTranslator("translator-odata", factory);
		
		WSManagedConnectionFactory managedconnectionFactory = new WSManagedConnectionFactory();
		managedconnectionFactory.setEndPoint("http://services.odata.org/Northwind/Northwind.svc/");
		server.addConnectionFactory("java:/ODataNorthwindDS", managedconnectionFactory.createConnectionFactory());

		server.start(new EmbeddedConfiguration());
    	
		server.deployVDB(new FileInputStream(findFile("odataNorthwindservice-vdb.xml")));
		
		Connection c = server.getDriver().connect("jdbc:teiid:NorthwindVDB", null);
		
		execute(c, "SELECT * FROM Categories", false);
		execute(c, "SELECT * FROM Customers", false);
		execute(c, "SELECT * FROM Employees", false);
		execute(c, "SELECT * FROM Orders", true);
		
		server.stop();
	}

}
