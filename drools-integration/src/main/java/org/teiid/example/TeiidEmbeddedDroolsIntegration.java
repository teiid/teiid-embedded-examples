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

import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;

public class TeiidEmbeddedDroolsIntegration {

	public static void main(String[] args) throws Exception {
	    		
		EmbeddedServer server = new EmbeddedServer();
		
		server.start(new EmbeddedConfiguration());
    	
		server.deployVDB(TeiidEmbeddedDroolsIntegration.class.getClassLoader().getResourceAsStream("drools-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:droolsVDB", null);
				
		execute(c, "SELECT performRuleOnData('org.teiid.example.drools.Message', 'Hello World', 0)", true);
		
		server.stop();
	}

}
