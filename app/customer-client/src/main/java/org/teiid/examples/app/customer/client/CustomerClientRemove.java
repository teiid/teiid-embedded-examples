package org.teiid.examples.app.customer.client;

import static org.teiid.examples.app.customer.client.CustomerClient.add;
import static org.teiid.examples.app.customer.client.CustomerClient.printCustomersSize;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

public class CustomerClientRemove {

    public static void main(String[] args) throws IOException {

        add();
        
        delete();
        
        add();
        
        deleteByNumber();
        
        add();
        
        deleteByName();
        
        add();
        
        deleteByCity();
        
        add();
        
        deleteByCountry();
        
        add();
        
        deleteByNumCityCountry();
    }
    
    private static void deleteByNumCityCountry() throws IOException {

        System.out.println("Remove Customer via: '/customer/deleteByNumCityCountry'\n");
        
        printCustomersSize();
        
        String url = "http://localhost:8080/customer/deleteByNumCityCountry?customernumber=100&city=Beijing&country=China";
        WebClient wc = WebClient.create(url);
        Response resp = wc.delete();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        printCustomersSize(); 
    }

    private static void deleteByCountry() throws IOException {

        System.out.println("Remove Customer via: '/customer/deleteByCountry'\n");
        
        printCustomersSize();
        
        String url = "http://localhost:8080/customer/deleteByCountry?country=China";
        WebClient wc = WebClient.create(url);
        Response resp = wc.delete();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        printCustomersSize(); 
    }

    private static void deleteByCity() throws IOException {

        System.out.println("Remove Customer via: '/customer/deleteByCity'\n");
        
        printCustomersSize();
        
        String url = "http://localhost:8080/customer/deleteByCity?city=Beijing";
        WebClient wc = WebClient.create(url);
        Response resp = wc.delete();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        printCustomersSize(); 
    }

    private static void deleteByName() throws IOException {

        System.out.println("Remove Customer via: '/customer/deleteByName/{customername}'\n");
        
        printCustomersSize();
        
        String url = "http://localhost:8080/customer/deleteByName/kylin";
        WebClient wc = WebClient.create(url);
        Response resp = wc.delete();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        printCustomersSize();   
    }

    private static void deleteByNumber() throws IOException {

        System.out.println("Remove Customer via: '/customer/deleteByNumber/{customernumber}'\n");
        
        printCustomersSize();
        
        String url = "http://localhost:8080/customer/deleteByNumber/100";
        WebClient wc = WebClient.create(url);
        Response resp = wc.delete();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        printCustomersSize();
    }

    private static void delete() throws IOException {
        
        System.out.println("Remove Customer via: '/customer/delete/{id}'\n");
        
        printCustomersSize();
        
        String url = "http://localhost:8080/customer/delete/100";
        WebClient wc = WebClient.create(url);
        Response resp = wc.delete();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        printCustomersSize();
    }

}
