package org.teiid.example;

import static org.teiid.example.JDBCUtils.close;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.resource.ResourceException;

import org.teiid.deployers.VirtualDatabaseException;
import org.teiid.dqp.internal.datamgr.ConnectorManagerRepository.ConnectorManagerException;
import org.teiid.resource.adapter.ws.WSManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.ws.BinaryWSProcedureExecution.StreamingBlob;
import org.teiid.translator.ws.WSExecutionFactory;

/**
 *  From 
 *      https://docs.jboss.org/author/display/TEIID/Web+Services+Translator, 
 *  InvokeHTTP Procedure return blob.
 * 
 * @author kylin
 *
 */
public class TeiidEmbeddedRestInvokeHTTP {
    
    private static String[] operations = new String[]{"http://localhost:8080/customer/customerList", 
                                                      "http://localhost:8080/customer/getAll", 
                                                      "http://localhost:8080/customer/getByNumber/161",
                                                      "http://localhost:8080/customer/getByCity/Burlingame",
                                                      "http://localhost:8080/customer/getByCountry/USA"};

    public static void main(String[] args) throws TranslatorException, SQLException, VirtualDatabaseException, ConnectorManagerException, IOException, ResourceException {

        EmbeddedServer server = new EmbeddedServer();
        
        WSExecutionFactory factory = new WSExecutionFactory();
        factory.start();
        server.addTranslator("translator-rest", factory);
        
        WSManagedConnectionFactory managedconnectionFactory = new WSManagedConnectionFactory();
        server.addConnectionFactory("java:/CustomerRESTWebSvcSource", managedconnectionFactory.createConnectionFactory());

        server.start(new EmbeddedConfiguration());
        
        server.deployVDB(TeiidEmbeddedRestInvokeHTTP.class.getClassLoader().getResourceAsStream("restwebservice-vdb.xml"));
        
        Connection conn = server.getDriver().connect("jdbc:teiid:restwebservice", null);
        
        for(String operation : operations) {
            String call = "exec invokeHttp('GET', null, '" + operation + "', 'TRUE')";
            CallableStatement cStmt = conn.prepareCall(call);
            cStmt.execute();
            StreamingBlob blob = (StreamingBlob) cStmt.getObject(1);
            System.out.println(getStringFromStream(blob.getBinaryStream()));
        }
        
        close(conn);
        server.stop();
    }

    public static String getStringFromStream(InputStream in) throws IOException {
        
        InputStreamReader is = new InputStreamReader(in);
        StringBuilder sb=new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();

        while(read != null) {
            sb.append(read + "\n");
            read =br.readLine();

        }

        return sb.toString();
    }

}
