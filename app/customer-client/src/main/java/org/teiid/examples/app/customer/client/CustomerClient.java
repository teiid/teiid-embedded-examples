package org.teiid.examples.app.customer.client;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.teiid.examples.app.customer.Customer;

public class CustomerClient {
    
    private static String urlStem = "http://localhost:8080/customer/";

    public static void main(String[] args) throws IOException {
        
        add();
        
        update();

        search();
        
        delete();
    }

    protected static void add() throws IOException {

        printCustomersSize();
        
        Customer customer = formCustomer();
        
        WebClient wc = WebClient.create(urlStem);
        Response resp = wc.post(customer);
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        printCustomersSize();
        
    }
    
    private static void update() throws IOException {

        getCustomer("100");
        
        WebClient wc = WebClient.create(urlStem);
        wc.path("100");
        wc.path("customernumber").type("text/plain");
        Response resp = wc.put("1000");
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        getCustomer("1000");
        
        resp = wc.reset().path("1000").put(formCustomer());
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        System.out.println("\n");
        
        getCustomer("100");
        
    }

    /**
     * The following GET api be invoked:
     *  
     *   '/customer/{customernumber}'
     *   '/customer/getByNumber/{customernumber}'
     *   '/customer/getByName/{customername}' 
     *   '/customer/getByCity'
     *   '/customer/getByCountry'
     *   '/customer/customerList'
     *   '/customer/getAll'
     *   '/customer/status'
     * 
     * @throws IOException
     */
    private static void search() throws IOException {

        System.out.println("Subsource Query: '/customer/{customernumber}'");
        WebClient wc = WebClient.create(urlStem);
        String customernumber = "100";
        wc.path(customernumber);
        Customer customer = wc.get(Customer.class);
        System.out.println(customer);
        wc.close();
        
        String url = "http://localhost:8080/customer/getByNumber/100";
        System.out.println("\nQuery with PathParam: '/customer/getByNumber/{customernumber}'");
        wc = WebClient.create(url);
        Response resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        url = "http://localhost:8080/customer/getByName/kylin";
        System.out.println("\n\nQuery with PathParam: '/customer/getByName/{customername}'");
        wc = WebClient.create(url);
        resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        url = "http://localhost:8080/customer/getByCity";
        System.out.println("\n\nQuery with QueryParam: '/customer/getByCity'");
        wc = WebClient.create(url);
        wc.query("city", "Beijing");
        resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        url = "http://localhost:8080/customer/getByCountry";
        System.out.println("\n\nQuery with QueryParam: '/customer/getByCountry'");
        wc = WebClient.create(url);
        wc.query("country", "China");
        resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        url = "http://localhost:8080/customer/customerList";
        System.out.println("\n\nQuery all with xml return: '/customer/customerList'");
        wc = WebClient.create(url);
        resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        url = "http://localhost:8080/customer/getAll";
        System.out.println("\n\nQuery all with json return: '/customer/getAll'");
        wc = WebClient.create(url);
        wc.accept(MediaType.APPLICATION_JSON);
        resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        url = "http://localhost:8080/customer/status";
        System.out.println("\n\nGet status with json return: '/customer/status'");
        wc = WebClient.create(url);
        wc.accept(MediaType.APPLICATION_JSON);
        resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        
        System.out.println("\n");
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
    
    protected static Customer getCustomer(String customernumber){
        WebClient wc = WebClient.create(urlStem);
        wc.path(customernumber);
        Customer customer = wc.get(Customer.class);
        wc.close();
        System.out.println(customer + "\n");
        return customer;
    }
    
    protected static void printCustomersSize() throws IOException {

        String url = "http://localhost:8080/customer/status";
        WebClient wc = WebClient.create(url);
        wc.accept(MediaType.APPLICATION_JSON);
        Response resp = wc.get();
        IOUtils.copy((InputStream) resp.getEntity(), System.out);
        wc.close();
        System.out.println("\n");
    }
    
    protected static Customer formCustomer() {
        
        Customer customer = new Customer();
        customer.setCustomernumber("100");
        customer.setCustomername("kylin");
        customer.setContactfirstname("Kylin");
        customer.setContactlastname("Soong");
        customer.setPhone("18611907049");
        customer.setAddressline1("Beijing");
        customer.setAddressline2("China Beijing");
        customer.setCity("Beijing");
        customer.setState("Beijing");
        customer.setPostalcode("100020");
        customer.setCountry("China");
        customer.setSalesrepemployeenumber("123456");
        customer.setCreditlimit("1200");
        return customer;
    }

}
