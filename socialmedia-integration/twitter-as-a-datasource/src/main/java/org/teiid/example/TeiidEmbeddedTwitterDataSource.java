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

import javax.resource.spi.ConnectionManager;
import javax.xml.ws.Service.Mode;

import org.teiid.resource.adapter.ws.WSManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ws.WSExecutionFactory;
import org.teiid.translator.ws.WSExecutionFactory.Binding;

public class TeiidEmbeddedTwitterDataSource {
    
    static {
        System.setProperty("http.proxyHost", "squid.apac.redhat.com");
        System.setProperty("http.proxyPort", "3128");
    }
    
    public static void main(String[] args) throws Exception {
        
        EmbeddedServer server = new EmbeddedServer();
        
        WSExecutionFactory translator = new WSExecutionFactory();
        translator.setDefaultBinding(Binding.HTTP);
        translator.setDefaultServiceMode(Mode.MESSAGE);
        translator.start();
        server.addTranslator("rest", translator);
		
        WSManagedConnectionFactory mcf = new WSManagedConnectionFactory();
        mcf.setSecurityType("OAuth");
        ConnectionManager cm = EmbeddedHelper.createConnectionFactory("picketbox/authentication.conf", "teiid-security-twitter", mcf);
        server.addConnectionFactory("java:/twitterDS", mcf.createConnectionFactory(cm));
        
        server.start(new EmbeddedConfiguration());
        
        server.deployVDB(TeiidEmbeddedTwitterDataSource.class.getClassLoader().getResourceAsStream("twitter-vdb.xml"));
        
        Connection c = server.getDriver().connect("jdbc:teiid:twitter", null);
        
        execute(c, "select * from TwitterUserTimelineView", true);
        
        server.stop();
	}

	

}
