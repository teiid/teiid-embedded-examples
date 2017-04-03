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

import static org.teiid.example.JDBCUtils.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Properties;

import org.teiid.resource.adapter.couchbase.CouchbaseManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.couchbase.CouchbaseExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedCouchbase {
    
    static {
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
    }
    
	private static String CONNECTIONSTRING = "127.0.0.1" ;
	private static String KEYSPACE = "default" ;
	
	static String[] querys = new String[] {
	    
	    "SELECT * FROM \"default\"",
	    "SELECT * FROM default_nestedArray",
	    "SELECT * FROM default_nestedArray_dim2",
	    "SELECT * FROM default_nestedArray_dim2_dim3",
	    "SELECT * FROM default_nestedArray_dim2_dim3_dim4",
	    
	    "SELECT * FROM Customer",
        "SELECT * FROM Customer_SavedAddresses",
        "SELECT * FROM Oder",
        "SELECT * FROM Oder_Items",
	    
//	    "SELECT * FROM test",
//	    "SELECT * FROM test_CreditCard AS T",
//	    "SELECT * FROM test_CreditCard",
//	    "SELECT * FROM test_SavedAddresses",
//	    "SELECT * FROM test_SavedAddresses AS T",
//	    "SELECT * FROM test_Items",
//	    "SELECT Name FROM test LIMIT 2, 2",
//	    "SELECT Name, Type FROM test ORDER BY Name",
//	    "SELECT Type FROM test ORDER BY Name", //OrderByUnrelated
//	    "SELECT Name, Type FROM test ORDER BY Type", //NullOrdering
//	    "SELECT Name, COUNT(*) AS count FROM test GROUP BY Name",
//	    "SELECT Name, Type  FROM test WHERE Name = 'John Doe'",
//	    "SELECT DISTINCT attr_int FROM test",
//	    "SELECT ALL attr_int FROM test",
//	    "SELECT attr_int, attr_long FROM test UNION ALL SELECT attr_int, attr_long FROM test_attr_jsonObject",
//	    "SELECT attr_int, attr_long FROM test UNION SELECT attr_int, attr_long FROM test_attr_jsonObject",
//	    "SELECT attr_int, attr_long FROM test INTERSECT SELECT attr_int, attr_long FROM test_attr_jsonObject",
//	    
//	    "SELECT LCASE(attr_string) FROM test_attr_jsonObject",
//	    "SELECT UCASE(attr_string) FROM test_attr_jsonObject",
//	    "SELECT TRANSLATE(attr_string, 'is', 'are') FROM test_attr_jsonObject",
//	    "SELECT couchbase.CONTAINS(attr_string, 'is') FROM test_attr_jsonObject",
//	    "SELECT couchbase.TITLE(attr_string) FROM test_attr_jsonObject",
//	    "SELECT couchbase.LTRIM(attr_string, 'This') FROM test_attr_jsonObject",
//	    "SELECT couchbase.TRIM(attr_string, 'is') FROM test_attr_jsonObject",
//	    "SELECT couchbase.RTRIM(attr_string, 'value') FROM test_attr_jsonObject",
//	    "SELECT couchbase.POSITION(attr_string, 'is') FROM test_attr_jsonObject",
//	    "SELECT CEILING(attr_number_float) FROM test_attr_jsonObject",
//	    "SELECT LOG(attr_number_double) FROM test_attr_jsonObject",
//	    "SELECT LOG(attr_number_double) FROM test_attr_jsonObject",
//	    "SELECT LOG(attr_number_double) FROM test_attr_jsonObject",
//	    "SELECT convert(attr_number_float, double) FROM test",
//	    "SELECT convert(attr_number_byte, boolean) FROM test",
//	    "SELECT convert(attr_number_long, string) FROM test",
//	    "SELECT couchbase.CLOCK_MILLIS() FROM test",
//	    "SELECT couchbase.CLOCK_STR() FROM test",
//	    "SELECT couchbase.CLOCK_STR('2006-01-02') FROM test",
//	    "SELECT couchbase.DATE_ADD_MILLIS(1488873653696, 2, 'century') FROM test",
//	    "SELECT couchbase.DATE_ADD_MILLIS(1488873653696, 2, 'decade') FROM test",
//	    "SELECT couchbase.DATE_ADD_MILLIS(1488873653696, 2, 'year') FROM test",
//	    "SELECT couchbase.DATE_ADD_STR('2017-03-08', 2, 'century') FROM test",
//	    "SELECT couchbase.DATE_ADD_STR('2017-03-08', 2, 'decade') FROM test",
//	    "SELECT couchbase.DATE_ADD_STR('2017-03-08', 2, 'year') FROM test",
//	    "SELECT couchbase.DATE_DIFF_MILLIS(2488873653696, 1488873653696, 'year') FROM test",
//	    "SELECT couchbase.DATE_DIFF_STR('2027-03-08', '2017-03-08', 'year') FROM test"
	};
	
	static String[] simpleQuerys = new String[] {
	    "SELECT * FROM Customer",
	    "SELECT * FROM Customer_SavedAddresses",
	    "SELECT * FROM Oder",
	    "SELECT * FROM Oder_Items",
	    
	/*    "call getTextDocuments('customer%', 'test')",
	    "call getDocuments('customer%', 'test')",
	    "call getTextDocument('customer-1', 'test')",
	    "call getDocument('customer-1', 'test')",
	    "call getTextMetadataDocument('test')",
	    "call getMetadataDocument('test')"*/
	};

	public static void main(String[] args) throws Exception {
		
	    // Prepare Sample Data
	    initCouchProperties();
//		LoadDataSimple.load(CONNECTIONSTRING, KEYSPACE);
//		LoadData.load(CONNECTIONSTRING, KEYSPACE);
		
		EmbeddedServer server = new EmbeddedServer();
		
		CouchbaseExecutionFactory factory = new CouchbaseExecutionFactory();
		factory.start();
		server.addTranslator("translator-couchbase", factory);
		
		CouchbaseManagedConnectionFactory mcf = new CouchbaseManagedConnectionFactory();
        mcf.setConnectionString(CONNECTIONSTRING); //$NON-NLS-1$
        mcf.setKeyspace(KEYSPACE); //$NON-NLS-1$
        mcf.setNamespace("default");
		server.addConnectionFactory("java:/couchbaseDS", mcf.createConnectionFactory());

		server.start(new EmbeddedConfiguration());
    	
		server.deployVDB(TeiidEmbeddedCouchbase.class.getClassLoader().getResourceAsStream("couchbase-vdb.xml"));
		
		Connection conn = server.getDriver().connect("jdbc:teiid:CouchbaseVDB", null);
		
		Arrays.asList(querys).forEach(query -> {
		    execute(conn, query);
		});
		
//		Arrays.asList(simpleQuerys).forEach(query -> {
//		    execute(conn, query);
//		});
		
//		ResultSet tables =conn.getMetaData().getTables(null, "CouchbaseModel", null, null);
//		while(tables.next()) {
//		    System.out.println(tables.getString(2) + ", " + tables.getString(3));
//		}
		
        execute(conn, "SELECT * FROM Oder");
		
//		execute(conn, "SELECT * FROM default_nestedArray_dim2_dim3_dim4");

				
		close(conn);
		
		server.stop();

	}

	private static void initCouchProperties() throws IOException {

		Properties prop = new Properties();
        InputStream in = TeiidEmbeddedCouchbase.class.getClassLoader().getResourceAsStream("couchbase.properties");
        prop.load(in);
        in.close();
        CONNECTIONSTRING = prop.getProperty("couchbase.ConnectionString", CONNECTIONSTRING);
        KEYSPACE = prop.getProperty("couchbase.Keyspace", KEYSPACE);
	}

}
