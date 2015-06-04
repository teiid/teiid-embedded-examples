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
import static org.teiid.example.util.IOUtils.findProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import org.teiid.resource.adapter.ldap.LDAPManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ldap.LDAPExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedLDAPDataSource {
	
	private static String ldapUrl = "ldap://127.0.0.1:389";
	private static String ldapAdminUserDN = "cn=Manager,dc=example,dc=com";
	private static String ldapAdminPassword = "redhat";

	public static void main(String[] args) throws Exception {
		
		initLDAPProperties();
		
		EmbeddedServer server = new EmbeddedServer();
		
		LDAPExecutionFactory factory = new LDAPExecutionFactory();
		factory.start();
		server.addTranslator("translator-ldap", factory);
		
		LDAPManagedConnectionFactory managedconnectionFactory = new LDAPManagedConnectionFactory();
		managedconnectionFactory.setLdapUrl(ldapUrl);
		managedconnectionFactory.setLdapAdminUserDN(ldapAdminUserDN);
		managedconnectionFactory.setLdapAdminUserPassword(ldapAdminPassword);
		server.addConnectionFactory("java:/ldapDS", managedconnectionFactory.createConnectionFactory());
		
		server.start(new EmbeddedConfiguration());
		server.deployVDB(new FileInputStream(findFile("ldap-vdb.xml")));
		Connection c = server.getDriver().connect("jdbc:teiid:ldapVDB", null);
		
		execute(c, "SELECT * FROM HR_Group", true);
		
		server.stop();
	}

	private static void initLDAPProperties() throws IOException {
		
		Properties prop = findProperties("ldap.properties");
		ldapUrl = prop.getProperty("ldap.url", ldapUrl);
		ldapAdminUserDN = prop.getProperty("ldap.adminUserDN", ldapAdminUserDN);
		ldapAdminPassword = prop.getProperty("ldap.adminUserPassword", ldapAdminPassword);
	}

}
