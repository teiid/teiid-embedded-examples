package org.teiid.examples.app.customer.client;

import static org.teiid.examples.app.customer.client.CustomerClient.formCustomer;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.teiid.examples.app.customer.Customer;

public class CustomerClientSimple {

    public static void main(String[] args) throws IOException {
                
        // add
        WebClient wc = WebClient.create("http://localhost:8080/customer/");
        Response resp = wc.post(formCustomer());
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        System.out.println();
        
        //search
        wc = WebClient.create("http://localhost:8080/customer/getByNumber/100");
        wc.accept(MediaType.APPLICATION_JSON);
        resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        System.out.println();
        
        //update
        wc = WebClient.create("http://localhost:8080/customer/update");
        Customer c = formCustomer();
        c.setCustomername("xxxxx");
        wc.path(c);
        wc.close();
        
        //search
        wc = WebClient.create("http://localhost:8080/customer/getByNumber/100");
        wc.accept(MediaType.APPLICATION_JSON);
        resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        System.out.println();
        
        //delete
        wc = WebClient.create("http://localhost:8080/customer/delete/100");
        resp = wc.delete();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        
        
    }

}
