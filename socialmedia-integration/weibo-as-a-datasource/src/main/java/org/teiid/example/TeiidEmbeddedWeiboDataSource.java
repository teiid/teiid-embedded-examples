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

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;

import javax.xml.ws.Service.Mode;

import org.teiid.resource.adapter.ws.WSManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ws.WSExecutionFactory;
import org.teiid.translator.ws.WSExecutionFactory.Binding;

public class TeiidEmbeddedWeiboDataSource {

    
    public static void main(String[] args) throws Exception {
        
        EmbeddedServer server = new EmbeddedServer();
        
        WSExecutionFactory translator = new WSExecutionFactory();
        translator.setDefaultBinding(Binding.HTTP);
        translator.setDefaultServiceMode(Mode.MESSAGE);
        translator.start();
        server.addTranslator("rest", translator);
		
        WSManagedConnectionFactory mcf = new WSManagedConnectionFactory();
        server.addConnectionFactory("java:/weiboDS", mcf.createConnectionFactory());
        
        server.start(new EmbeddedConfiguration());
        
        server.deployVDB(TeiidEmbeddedWeiboDataSource.class.getClassLoader().getResourceAsStream("weibo-vdb.xml"));
        
        Connection conn = server.getDriver().connect("jdbc:teiid:WeiboVDB", null);
        
        execute(conn, "EXEC friendships_followers('https://api.weibo.com/2/friendships/followers.json?access_token=2.00JEVMVG6u_COCa5f58ca2ado84mtC&uid=5957842765')", true);

	}

	

}
