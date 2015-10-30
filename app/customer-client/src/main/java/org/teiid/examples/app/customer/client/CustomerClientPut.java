package org.teiid.examples.app.customer.client;

import static org.teiid.examples.app.customer.client.CustomerClient.getCustomer;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

public class CustomerClientPut {

    public static void main(String[] args) throws IOException {
                
        String putJson_1 = "{\"customernumber\":\"103\",\"customername\":\"kylin\",\"contactlastname\":\"Soong_test1\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
        String putJson_2 = "{\"customernumber\":\"103\",\"customername\":\"kylin\",\"contactlastname\":\"Soong_test2\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
        String putJson_3 = "{\"customernumber\":\"103\",\"customername\":\"kylin\",\"contactlastname\":\"Soong_test3\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
        String putJson_4 = "{\"customernumber\":\"103\",\"customername\":\"kylin\",\"contactlastname\":\"Soong_test4\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
        String putJson_5 = "{\"customernumber\":\"103\",\"customername\":\"kylin\",\"contactlastname\":\"Soong_test5\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
        String putJson_6 = "{\"customernumber\":\"103\",\"customername\":\"kylin\",\"contactlastname\":\"Soong_test6\",\"contactfirstname\":\"Kylin\",\"phone\":\"18611907049\",\"addressline1\":\"Beijing\",\"addressline2\":\"China Beijing\",\"city\":\"Beijing\",\"state\":\"Beijing\",\"postalcode\":\"100020\",\"country\":\"China\",\"salesrepemployeenumber\":\"123456\",\"creditlimit\":\"1200\"}";
        
        
        WebClient wc = WebClient.create("http://localhost:8080/customer/update");
        Response resp = wc.type(MediaType.APPLICATION_JSON).put(putJson_1);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        getCustomer("103");
        
        wc = WebClient.create("http://localhost:8080/customer/updateByNumber/103");
        resp = wc.type(MediaType.APPLICATION_JSON).put(putJson_2);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        getCustomer("103");
        
        wc = WebClient.create("http://localhost:8080/customer/updateByName/kylin");
        resp = wc.type(MediaType.APPLICATION_JSON).put(putJson_3);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        getCustomer("103");
        
        wc = WebClient.create("http://localhost:8080/customer/updateByCity?city=Beijing");
        resp = wc.type(MediaType.APPLICATION_JSON).put(putJson_4);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        getCustomer("103");
        
        wc = WebClient.create("http://localhost:8080/customer/updateByCountry?country=China");
        resp = wc.type(MediaType.APPLICATION_JSON).put(putJson_5);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        getCustomer("103");
        
        wc = WebClient.create("http://localhost:8080/customer/updateByNumCityCountry?customernumber=103&city=Beijing&country=China");
        resp = wc.type(MediaType.APPLICATION_JSON).put(putJson_6);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        getCustomer("103");
        
    }

}
