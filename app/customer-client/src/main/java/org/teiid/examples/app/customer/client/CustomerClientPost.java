package org.teiid.examples.app.customer.client;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

public class CustomerClientPost {

    public static void main(String[] args) throws IOException {
        
        String postJson = "{\"customernumber\":\"100\",\"customername\":\"kylin\",\"contactlastname\":\"Soong\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
        String postXml  = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><customer><addressline1>Beijing</addressline1><addressline2>China Beijing</addressline2><city>Beijing</city><contactfirstname>Kylin</contactfirstname><contactlastname>Soong</contactlastname><country>China</country><creditlimit>1200</creditlimit><customername>kylin</customername><customernumber>101</customernumber><phone>18611907049</phone><postalcode>100020</postalcode><salesrepemployeenumber>123456</salesrepemployeenumber><state>Beijing</state></customer>";
        
        WebClient wc = WebClient.create("http://localhost:8080/customer/");
        Response resp = wc.type(MediaType.APPLICATION_JSON).post(postJson);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        resp = wc.reset().type(MediaType.APPLICATION_XML).post(postXml);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        CustomerClient.printCustomersSize();
    }

}
