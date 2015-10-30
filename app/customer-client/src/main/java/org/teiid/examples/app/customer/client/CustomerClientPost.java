package org.teiid.examples.app.customer.client;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

public class CustomerClientPost {

    public static void main(String[] args) throws IOException {
        
        String postJson = "{\"customernumber\":\"98\",\"customername\":\"kylin\",\"contactlastname\":\"Soong\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
        String postXml  = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><customer><addressline1>Beijing</addressline1><addressline2>China Beijing</addressline2><city>Beijing</city><contactfirstname>Kylin</contactfirstname><contactlastname>Soong</contactlastname><country>China</country><creditlimit>1200</creditlimit><customername>kylin</customername><customernumber>99</customernumber><phone>18611907049</phone><postalcode>100020</postalcode><salesrepemployeenumber>123456</salesrepemployeenumber><state>Beijing</state></customer>";
        
        String postJson_1 = "{\"customernumber\":\"100\",\"customername\":\"kylin\",\"contactlastname\":\"Soong\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
        String postXml_1  = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><customer><addressline1>Beijing</addressline1><addressline2>China Beijing</addressline2><city>Beijing</city><contactfirstname>Kylin</contactfirstname><contactlastname>Soong</contactlastname><country>China</country><creditlimit>1200</creditlimit><customername>kylin</customername><customernumber>101</customernumber><phone>18611907049</phone><postalcode>100020</postalcode><salesrepemployeenumber>123456</salesrepemployeenumber><state>Beijing</state></customer>";
        
        String postJsonList = "[{\"customernumber\":\"91\",\"customername\":\"kylin\",\"contactlastname\":\"Soong\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}, {\"customernumber\":\"92\",\"customername\":\"kylin\",\"contactlastname\":\"Soong\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}]";
        String postXmlList = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><collection><customer><addressline1>Beijing</addressline1><addressline2>China Beijing</addressline2><city>Beijing</city><contactfirstname>Kylin</contactfirstname><contactlastname>Soong</contactlastname><country>China</country><creditlimit>1200</creditlimit><customername>kylin</customername><customernumber>93</customernumber><phone>18611907049</phone><postalcode>100020</postalcode><salesrepemployeenumber>123456</salesrepemployeenumber><state>Beijing</state></customer><customer><addressline1>Beijing</addressline1><addressline2>China Beijing</addressline2><city>Beijing</city><contactfirstname>Kylin</contactfirstname><contactlastname>Soong</contactlastname><country>China</country><creditlimit>1200</creditlimit><customername>kylin</customername><customernumber>94</customernumber><phone>18611907049</phone><postalcode>100020</postalcode><salesrepemployeenumber>123456</salesrepemployeenumber><state>Beijing</state></customer></collection>";
        
        WebClient wc = WebClient.create("http://localhost:8080/customer/");
        Response resp = wc.type(MediaType.APPLICATION_JSON).post(postJson);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        resp = wc.reset().type(MediaType.APPLICATION_XML).post(postXml);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        CustomerClient.printCustomersSize();
        
        wc = WebClient.create("http://localhost:8080/customer/add");
        resp = wc.type(MediaType.APPLICATION_JSON).post(postJson_1);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        resp = wc.reset().type(MediaType.APPLICATION_XML).post(postXml_1);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        CustomerClient.printCustomersSize();
        
        wc = WebClient.create("http://localhost:8080/customer/addList");
        resp = wc.type(MediaType.APPLICATION_JSON).post(postJsonList);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        resp = wc.reset().type(MediaType.APPLICATION_XML).post(postXmlList);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        CustomerClient.printCustomersSize();
    }

}
