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

import static org.teiid.example.JDBCUtils.execute;

import java.sql.Connection;

import org.teiid.resource.adapter.ws.WSManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ws.WSExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedRestWebServiceDataSource {

	public static void main(String[] args) throws Exception {
		
		EmbeddedServer server = new EmbeddedServer();
		
		WSExecutionFactory factory = new WSExecutionFactory();
		factory.start();
		server.addTranslator("translator-rest", factory);
		
		WSManagedConnectionFactory managedconnectionFactory = new WSManagedConnectionFactory();
		server.addConnectionFactory("java:/CustomerRESTWebSvcSource", managedconnectionFactory.createConnectionFactory());

		server.start(new EmbeddedConfiguration());
    	
		server.deployVDB(TeiidEmbeddedRestWebServiceDataSource.class.getClassLoader().getResourceAsStream("restwebservice-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:restwebservice", null);
		
		execute(c, "EXEC getCustomer('http://localhost:8080/customer/customerList')", false);
		execute(c, "EXEC getAll('http://localhost:8080/customer/getAll')", false);
		execute(c, "EXEC getOne('http://localhost:8080/customer/getByNumber/161')", false);
		execute(c, "EXEC getOne('http://localhost:8080/customer/getByName/Technics%20Stores%20Inc.')", false);
		execute(c, "EXEC getOne('http://localhost:8080/customer/getByCity/Burlingame')", false);
		execute(c, "EXEC getOne('http://localhost:8080/customer/getByCountry/USA')", false);
		
		execute(c, "SELECT * FROM CustomersView", true);
		
		server.stop();
	}

}
