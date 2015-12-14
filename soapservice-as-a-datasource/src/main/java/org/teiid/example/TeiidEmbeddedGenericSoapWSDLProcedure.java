package org.teiid.example;

import static org.teiid.example.JDBCUtils.close;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.teiid.core.types.SQLXMLImpl;
import org.teiid.resource.adapter.ws.WSManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ws.WSExecutionFactory;

/**
 * This example show how to invoke WSDL based Procedures
 * 
 * @author kylin
 *
 */
public class TeiidEmbeddedGenericSoapWSDLProcedure {
    
    static final String GET_ALL = "<GetAllStateInfo xmlns=\"http://www.teiid.org/stateService/\"/>";
    static final String GET_ONE = "<GetStateInfo xmlns=\"http://www.teiid.org/stateService/\"><stateCode xmlns=\"\">CA</stateCode></GetStateInfo>";

    public static void main(String[] args) throws Exception {

        
        EmbeddedServer es = new EmbeddedServer();
        
        WSExecutionFactory ef = new WSExecutionFactory();
        ef.start();
        es.addTranslator("translator-ws", ef);
        
        WSManagedConnectionFactory wsmcf = new WSManagedConnectionFactory();
        wsmcf.setWsdl("http://localhost:8080/StateService/stateService/StateServiceImpl?WSDL");
        wsmcf.setNamespaceUri("http://www.teiid.org/stateService/");
        wsmcf.setServiceName("stateService");
        wsmcf.setEndPointName("StateServiceImplPort");
        wsmcf.setEndPoint("http://localhost:8080/StateService/stateService/StateServiceImpl?WSDL");
        es.addConnectionFactory("java:/StateServiceWebSvcSource", wsmcf.createConnectionFactory());
        
        es.start(new EmbeddedConfiguration());
        
        es.deployVDB(TeiidEmbeddedGenericSoapWSDLProcedure.class.getClassLoader().getResourceAsStream("webservice-wsdl-vdb.xml"));
        
        Connection conn = es.getDriver().connect("jdbc:teiid:StateServiceWSDLVDB", null);
        
        String call = "exec GetStateInfo('<GetStateInfo xmlns=\"http://www.teiid.org/stateService/\"><stateCode xmlns=\"\">CA</stateCode></GetStateInfo>')";
        CallableStatement cStmt = conn.prepareCall(call);
        cStmt.execute();
        SQLXMLImpl xml = (SQLXMLImpl) cStmt.getObject(1);
        System.out.println(xml.getString());
        close(cStmt);
        
        call = "exec GetAllStateInfo('<GetAllStateInfo xmlns=\"http://www.teiid.org/stateService/\"/>')";
        cStmt = conn.prepareCall(call);
        cStmt.execute();
        xml = (SQLXMLImpl) cStmt.getObject(1);
        System.out.println(xml.getString());
        close(cStmt);

        close(conn);
        
        es.stop();
                
    }

}
