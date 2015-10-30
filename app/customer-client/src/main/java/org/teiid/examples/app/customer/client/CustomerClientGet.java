package org.teiid.examples.app.customer.client;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

public class CustomerClientGet {

    public static void main(String[] args) throws IOException {

        WebClient wc = WebClient.create("http://localhost:8080/customer/getAll");
        wc.accept(MediaType.APPLICATION_JSON);
        Response resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println();
        
        resp = wc.reset().accept(MediaType.APPLICATION_XML).get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
    }

}
