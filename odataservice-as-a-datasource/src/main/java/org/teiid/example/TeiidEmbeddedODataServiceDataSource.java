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
    	
		server.deployVDB(TeiidEmbeddedODataServiceDataSource.class.getClassLoader().getResourceAsStream("odataNorthwindservice-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:NorthwindVDB", null);
		
		execute(c, "SELECT * FROM Categories", false);
		execute(c, "SELECT * FROM Customers", false);
		execute(c, "SELECT * FROM Employees", false);
		execute(c, "SELECT * FROM Orders", true);
		
		server.stop();
	}

}
