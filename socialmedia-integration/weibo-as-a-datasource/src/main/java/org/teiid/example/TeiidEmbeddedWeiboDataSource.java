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
