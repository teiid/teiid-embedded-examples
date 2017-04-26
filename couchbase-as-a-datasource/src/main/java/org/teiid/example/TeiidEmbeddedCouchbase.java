package org.teiid.example;

import static org.teiid.example.JDBCUtils.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Properties;

import org.teiid.resource.adapter.couchbase.CouchbaseManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.couchbase.CouchbaseExecutionFactory;

public class TeiidEmbeddedCouchbase {
    
    static {
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
    }
    
    private static String CONNECTIONSTRING = "127.0.0.1" ;
    private static String KEYSPACE = "default" ;
    
    static String[] inserts = new String[] {
            "INSERT INTO Customer VALUES ('customer-3', 'Customer_12346', 'Customer', 'Kylin Soong')",
            "INSERT INTO Customer_SavedAddresses VALUES ('customer-3', 0,  'Beijing')",
            "INSERT INTO Customer_SavedAddresses VALUES ('customer-3', 1,  'Beijing')",
            "INSERT INTO Oder (documentID, CustomerID, type, CreditCard_CardNumber, CreditCard_Type, CreditCard_CVN, CreditCard_Expiry, Name) VALUES ('order-3', 'Customer_12346', 'Oder', '4111 1111 1111 111', 'Visa', 123, '12/12', 'Air Ticket')",
            "INSERT INTO Oder_Items (documentID, Oder_Items_idx, Oder_Items_Quantity, Oder_Items_ItemID) VALUES ('order-3', 0, 5, 92312)",
            "INSERT INTO Oder_Items (documentID, Oder_Items_idx, Oder_Items_Quantity, Oder_Items_ItemID) VALUES ('order-3', 1, 5, 92312)"
    };
    
    static String[] querys = new String[] {
            "SELECT * FROM Customer WHERE documentID = 'customer-3'",
            "SELECT * FROM Customer_SavedAddresses WHERE documentID = 'customer-3'",
            "SELECT * FROM Oder WHERE documentID = 'order-3'",
            "SELECT * FROM Oder_Items WHERE documentID = 'order-3'",
        };
    
    static String[] updates = new String[] {
            "UPDATE Customer SET Name = 'John Doe' WHERE documentID = 'customer-3'",
            "UPDATE Customer_SavedAddresses SET Customer_SavedAddresses = 'ChaoYang Beijing' WHERE documentID = 'customer-3' AND Customer_SavedAddresses_idx = 0",
            "UPDATE Customer_SavedAddresses SET Customer_SavedAddresses = 'ChaoYang Beijing' WHERE documentID = 'customer-3' AND Customer_SavedAddresses_idx = 1",
            "UPDATE Oder SET CreditCard_CVN = 100, CreditCard_CardNumber = '4111 1111 1111 112', CreditCard_Expiry = '14/12' WHERE documentID = 'order-3'",
            "UPDATE Oder_Items SET Oder_Items_ItemID = 80000, Oder_Items_Quantity = 10 WHERE documentID = 'order-3' AND Oder_Items_idx = 0",
            "UPDATE Oder_Items SET Oder_Items_ItemID = 80000, Oder_Items_Quantity = 10 WHERE documentID = 'order-3' AND Oder_Items_idx = 1"
    };
    
    static String[] deletes = new String[] {
            "DELETE FROM Customer_SavedAddresses WHERE documentID = 'customer-3' AND Customer_SavedAddresses_idx = 0",
            "DELETE FROM Customer_SavedAddresses WHERE documentID = 'customer-3' AND Customer_SavedAddresses_idx = 1",
            "DELETE FROM Customer WHERE documentID = 'customer-3'",
            "DELETE FROM Oder_Items WHERE documentID = 'order-3' AND Oder_Items_idx = 0",
            "DELETE FROM Oder_Items WHERE documentID = 'order-3' AND Oder_Items_idx = 1",
            "DELETE FROM Oder WHERE documentID = 'order-3'"
    };

    public static void main(String[] args) throws Exception {

        initCouchProperties();
        
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
        
        // delete if exist
        execute(conn, "EXEC CouchbaseVDB.native('DELETE FROM test USE KEYS [\"customer-3\", \"order-3\"]')");
        
        Arrays.asList(inserts).forEach(query -> {
            try {
                execute(conn, query);
            } catch (Exception e) {
                assert(false);
            }
        });
        
        Arrays.asList(querys).forEach(query -> {
            try {
                execute(conn, query);
            } catch (Exception e) {
                assert(false);
            }
        });
        
        Arrays.asList(updates).forEach(query -> {
            try {
                execute(conn, query);
            } catch (Exception e) {
                assert(false);
            }
        });
        
        Arrays.asList(querys).forEach(query -> {
            try {
                execute(conn, query);
            } catch (Exception e) {
                assert(false);
            }
        });
        
        Arrays.asList(deletes).forEach(query -> {
            try {
                execute(conn, query);
            } catch (Exception e) {
                assert(false);
            }
        });
        
        Arrays.asList(querys).forEach(query -> {
            try {
                execute(conn, query);
            } catch (Exception e) {
                assert(false);
            }
        });

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
