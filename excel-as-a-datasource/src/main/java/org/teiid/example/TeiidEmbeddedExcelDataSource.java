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

import org.teiid.resource.adapter.file.FileManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.excel.ExcelExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedExcelDataSource {

	public static void main(String[] args) throws Exception {
		
		EmbeddedServer server = new EmbeddedServer();
		
		ExcelExecutionFactory factory = new ExcelExecutionFactory();
		factory.start();
		factory.setSupportsDirectQueryProcedure(true);
		server.addTranslator("excel", factory);
		
		FileManagedConnectionFactory managedconnectionFactory = new FileManagedConnectionFactory();
		managedconnectionFactory.setParentDirectory("src/main/resources/data");
		server.addConnectionFactory("java:/excel-file", managedconnectionFactory.createConnectionFactory());

		server.start(new EmbeddedConfiguration());
    	
		server.deployVDB(TeiidEmbeddedExcelDataSource.class.getClassLoader().getResourceAsStream("excel-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:ExcelVDB", null);
		
		execute(c, "SELECT * FROM Sheet1", false);
		execute(c, "SELECT * FROM PersonalHoldings", true);
		
		server.stop();
	}

}
