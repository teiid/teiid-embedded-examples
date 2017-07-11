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

import java.io.IOException;
import java.io.InputStream;
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
		server.deployVDB(TeiidEmbeddedLDAPDataSource.class.getClassLoader().getResourceAsStream("ldap-vdb.xml"));
		Connection c = server.getDriver().connect("jdbc:teiid:ldapVDB", null);
		
		execute(c, "SELECT * FROM HR_Group", true);
		
		server.stop();
	}

	private static void initLDAPProperties() throws IOException {
		
		Properties prop = new Properties();
        InputStream in = TeiidEmbeddedLDAPDataSource.class.getClassLoader().getResourceAsStream("ldap.properties");
        prop.load(in);
        in.close();
		ldapUrl = prop.getProperty("ldap.url", ldapUrl);
		ldapAdminUserDN = prop.getProperty("ldap.adminUserDN", ldapAdminUserDN);
		ldapAdminPassword = prop.getProperty("ldap.adminUserPassword", ldapAdminPassword);
	}

}
