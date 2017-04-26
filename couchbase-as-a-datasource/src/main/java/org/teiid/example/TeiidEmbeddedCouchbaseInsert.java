package org.teiid.example;

import static org.teiid.example.JDBCUtils.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.teiid.resource.adapter.couchbase.CouchbaseManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.couchbase.CouchbaseExecutionFactory;

public class TeiidEmbeddedCouchbaseInsert {
    
    static {
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
    }
    
    private static String CONNECTIONSTRING = "127.0.0.1" ;
    private static String KEYSPACE = "default" ;

    public static void main(String[] args) throws Exception  {

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
        
//        execute(conn, "SELECT 1 AS c_0 FROM Customer WHERE documentID = 'customer-3' LIMIT 1");
        
//        execute(conn, "SELECT COUNT(*) AS count FROM Customer WHERE ID = 'Customer_12345'");
        execute(conn, "UPSERT INTO Customer VALUES ('customer-3', 'Customer_12345', 'Customer', 'Kylin Soong')");
        
        close(conn);
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
