package org.teiid.examples.app.customer.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class CustomerClientInvoke {
    
    private static final String CONNECTION_TIMEOUT = "javax.xml.ws.client.connectionTimeout"; 
    private static final String RECEIVE_TIMEOUT = "javax.xml.ws.client.receiveTimeout"; 
    
    private static String postJson = "{\"customernumber\":\"100\",\"customername\":\"kylin\",\"contactlastname\":\"Soong\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
    private static String postXml  = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><customer><addressline1>Beijing</addressline1><addressline2>China Beijing</addressline2><city>Beijing</city><contactfirstname>Kylin</contactfirstname><contactlastname>Soong</contactlastname><country>China</country><creditlimit>1200</creditlimit><customername>kylin</customername><customernumber>101</customernumber><phone>18611907049</phone><postalcode>100020</postalcode><salesrepemployeenumber>123456</salesrepemployeenumber><state>Beijing</state></customer>";
    
    
    private static HashMap<String, Object> requestContext = new HashMap<String, Object>();
    private static HashMap<String, Object> responseContext = new HashMap<String, Object>();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IllegalStateException, IOException {
        
        Map<String, List<String>> httpHeaders = (Map<String, List<String>>) requestContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        if(httpHeaders == null) {
            httpHeaders = new HashMap<String, List<String>>();
        }
        httpHeaders.put("Content-Type", Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.put("User-Agent", Collections.singletonList("Teiid Server"));
        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "POST");
                
        final URL url = new URL("http://localhost:8080/customer");
        final String httpMethod = (String)requestContext.get(MessageContext.HTTP_REQUEST_METHOD);
        
        Bus bus = BusFactory.getThreadDefaultBus();
        WebClient client = createWebClient("http://localhost:8080/customer", bus);
        
        Map<String, List<String>> header = (Map<String, List<String>>)requestContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        System.out.println("header: " + header);
        for (Map.Entry<String, List<String>> entry : header.entrySet()) {
            client.header(entry.getKey(), entry.getValue().toArray());
        }
        
        InputStream payload = new ByteArrayInputStream(postJson.getBytes());
        Long timeout = (Long) requestContext.get(RECEIVE_TIMEOUT);
        
        HTTPClientPolicy clientPolicy = WebClient.getConfig(client).getHttpConduit().getClient();
        if (timeout != null) {
            clientPolicy.setReceiveTimeout(timeout);
        }
        
        timeout = (Long) requestContext.get(CONNECTION_TIMEOUT);
        if (timeout != null) {
            clientPolicy.setConnectionTimeout(timeout);
        }
        
        javax.ws.rs.core.Response response = client.invoke(httpMethod, payload);
        responseContext.put("status-code", response.getStatus());
        responseContext.putAll(response.getMetadata());
        
        ArrayList contentTypes = (ArrayList)responseContext.get("content-type");
        String contentType = contentTypes != null ? (String)contentTypes.get(0):"application/octet-stream";
        System.out.println(responseContext);
        System.out.println(contentType);
        
        IOUtils.copy((InputStream)response.getEntity(), System.out);
    }

    private static WebClient createWebClient(String baseAddress, Bus bus) {
        JAXRSClientFactoryBean bean = new JAXRSClientFactoryBean();
        bean.setBus(bus);
        bean.setAddress(baseAddress);
        return bean.createWebClient();
    }

}
