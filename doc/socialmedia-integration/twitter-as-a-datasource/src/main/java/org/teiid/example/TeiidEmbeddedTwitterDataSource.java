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

import javax.xml.ws.Service.Mode;

import org.teiid.resource.adapter.ws.WSManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ws.WSExecutionFactory;
import org.teiid.translator.ws.WSExecutionFactory.Binding;

public class TeiidEmbeddedTwitterDataSource {
    
/*    static {
        System.setProperty("http.proxyHost", "squid.apac.redhat.com");
        System.setProperty("http.proxyPort", "3128");
    }*/
    
    public static void main(String[] args) throws Exception {
        
        EmbeddedServer server = new EmbeddedServer();
        
        WSExecutionFactory translator = new WSExecutionFactory();
        translator.setDefaultBinding(Binding.HTTP);
        translator.setDefaultServiceMode(Mode.MESSAGE);
        translator.start();
        server.addTranslator("rest", translator);
		
        WSManagedConnectionFactory mcf = new WSManagedConnectionFactory();
        mcf.setSecurityType("OAuth");
        Object connectionFactory = EmbeddedHelper.createConnectionFactory("picketbox/authentication.conf", "teiid-security-twitter", mcf);
        server.addConnectionFactory("java:/twitterDS", connectionFactory);
        
        server.start(new EmbeddedConfiguration());
        
        server.deployVDB(TeiidEmbeddedTwitterDataSource.class.getClassLoader().getResourceAsStream("twitter-vdb.xml"));
        
        Connection c = server.getDriver().connect("jdbc:teiid:twitter", null);
        
        execute(c, "select * from TwitterUserTimelineView", true);
        
        server.stop();
	}

	

}
