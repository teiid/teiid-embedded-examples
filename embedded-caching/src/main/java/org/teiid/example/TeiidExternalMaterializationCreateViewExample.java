package org.teiid.example;

import static org.teiid.example.H2PERFTESTClient.H2_JDBC_DRIVER;
import static org.teiid.example.H2PERFTESTClient.H2_JDBC_PASS;
import static org.teiid.example.H2PERFTESTClient.H2_JDBC_URL;
import static org.teiid.example.H2PERFTESTClient.H2_JDBC_USER;
import static org.teiid.example.util.JDBCUtils.execute;
import static org.teiid.example.util.JDBCUtils.close;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;

public class TeiidExternalMaterializationCreateViewExample {
    
    static EmbeddedServer server = null;
    static Connection conn = null;

    public static void main(String[] args) throws Exception {

        DataSource ds = EmbeddedHelper.newDataSource(H2_JDBC_DRIVER, H2_JDBC_URL, H2_JDBC_USER, H2_JDBC_PASS);
        RunScript.execute(ds.getConnection(), new InputStreamReader(TeiidExternalMaterializationCreateViewExample.class.getClassLoader().getResourceAsStream("h2-schema.sql")));
        
        
        server = new EmbeddedServer();
        
        H2ExecutionFactory factory = new H2ExecutionFactory();
        factory.start();
        factory.setSupportsDirectQueryProcedure(true);
        server.addTranslator("translator-h2", factory);
        
        server.addConnectionFactory("java:/accounts-ds", ds);
        
        EmbeddedConfiguration config = new EmbeddedConfiguration();
        config.setTransactionManager(EmbeddedHelper.getTransactionManager());
        server.start(config);
                
        server.deployVDB(TeiidExternalMaterializationCreateViewExample.class.getClassLoader().getResourceAsStream("teiid-mat-example-vdb.xml"));
        
        Properties info = new Properties();
        conn = server.getDriver().connect("jdbc:teiid:MatVDBVIEW", info);
        
        execute(conn, "SELECT * FROM VIEW_A", false);
        execute(conn, "SELECT * FROM VIEW_B", false);
        execute(conn, "SELECT * FROM VIEW_C", false);
        execute(conn, "SELECT * FROM VIEW_D", false);
        execute(conn, "exec SYSADMIN.loadMatView('Model_E', 'VIEW_E')", false);
        execute(conn, "SELECT * FROM VIEW_E", false);
        execute(conn, "SELECT * FROM VIEW_F", false);
        
        execute(conn, "SELECT * FROM status", false);
        
        close(conn);
    }

}
