package org.teiid.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.teiid.resource.adapter.couchbase.CouchbaseManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.couchbase.CouchbaseExecutionFactory;
import org.teiid.transport.SocketConfiguration;
import org.teiid.transport.WireProtocol;

public class TeiidEmbeddedCouchbaseSocket {
    
    static {
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
    }
    
    private static String CONNECTIONSTRING = "127.0.0.1" ;
    private static String KEYSPACE = "default" ;

    public static void main(String[] args) throws Exception {
        
        initMongoProperties();

        SocketConfiguration s = new SocketConfiguration();
        s.setBindAddress("localhost");
        s.setPortNumber(31000);
        s.setProtocol(WireProtocol.teiid);
        
        EmbeddedServer server = new EmbeddedServer();
        
        CouchbaseExecutionFactory factory = new CouchbaseExecutionFactory();
        factory.start();
        server.addTranslator("translator-couchbase", factory);
        
        CouchbaseManagedConnectionFactory mcf = new CouchbaseManagedConnectionFactory();
        mcf.setConnectionString(CONNECTIONSTRING); //$NON-NLS-1$
        mcf.setKeyspace(KEYSPACE); //$NON-NLS-1$
        server.addConnectionFactory("java:/couchbaseDS", mcf.createConnectionFactory());

        EmbeddedConfiguration config = new EmbeddedConfiguration();
        config.addTransport(s); 
        server.start(config);
        
        server.deployVDB(TeiidEmbeddedCouchbase.class.getClassLoader().getResourceAsStream("couchbase-vdb.xml"));
                
        Thread.sleep(Long.MAX_VALUE);
        
    }
    
    private static void initMongoProperties() throws IOException {

        Properties prop = new Properties();
        InputStream in = TeiidEmbeddedCouchbase.class.getClassLoader().getResourceAsStream("couchbase.properties");
        prop.load(in);
        in.close();
        CONNECTIONSTRING = prop.getProperty("couchbase.ConnectionString", CONNECTIONSTRING);
        KEYSPACE = prop.getProperty("couchbase.Keyspace", KEYSPACE);
    }

}
