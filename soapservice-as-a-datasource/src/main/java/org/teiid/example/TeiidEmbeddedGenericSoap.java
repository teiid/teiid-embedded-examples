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
import org.teiid.translator.ws.WSExecutionFactory;

/**
 * This example shows invoking a generic soap service.
 * 
 */
@SuppressWarnings("nls")
public class TeiidEmbeddedGenericSoap {
	
	
	public static void main(String[] args) throws Exception {
				
		EmbeddedServer es = new EmbeddedServer();
		
		WSExecutionFactory ef = new WSExecutionFactory();
		ef.start();
		es.addTranslator("translator-ws", ef);
		
		//add a connection factory
		WSManagedConnectionFactory wsmcf = new WSManagedConnectionFactory();
		es.addConnectionFactory("java:/StateServiceWebSvcSource", wsmcf.createConnectionFactory());
		
		es.start(new EmbeddedConfiguration());
		
		es.deployVDB(TeiidEmbeddedGenericSoap.class.getClassLoader().getResourceAsStream("webservice-vdb.xml"));
		
		Connection c = es.getDriver().connect("jdbc:teiid:StateServiceVDB", null);
		
		//This assume 'stateService' run on localhost
		execute(c, "EXEC GetStateInfo('CA', 'http://localhost:8080/stateService?wsdl')", false);
		
		execute(c, "EXEC GetAllStateInfo('http://localhost:8080/stateService?wsdl')", true);
		
		es.stop();
	}

}
