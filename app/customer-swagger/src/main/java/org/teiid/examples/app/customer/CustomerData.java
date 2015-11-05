/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.teiid.examples.app.customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CustomerData {
    
    private static final String XMLFILE = "customerList.xml";
    
    static List<Customer> list;
    
    static {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(CustomerData.class.getClassLoader().getResourceAsStream(XMLFILE));
            
            list = new ArrayList<>();
            
            NodeList nodeList = document.getDocumentElement().getChildNodes(); 
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node instanceof Element){
                    Customer customer = new Customer();
                    NodeList childNodes = node.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++){
                        Node cNode = childNodes.item(j);
                        switch (cNode.getNodeName()) {
                        case "customernumber" :
                            customer.setCustomernumber(cNode.getTextContent());
                            break;
                        case "customername" :
                            customer.setCustomername(cNode.getTextContent());
                            break;
                        case "contactlastname" :
                            customer.setContactlastname(cNode.getTextContent());
                            break;
                        case "contactfirstname" :
                            customer.setContactfirstname(cNode.getTextContent());
                            break;
                        case "phone" :
                            customer.setPhone(cNode.getTextContent());
                            break;
                        case "addressline1" :
                            customer.setAddressline1(cNode.getTextContent());
                            break;
                        case "addressline2" :
                            customer.setAddressline2(cNode.getTextContent());
                            break;
                        case "city" :
                            customer.setCity(cNode.getTextContent());
                            break;
                        case "state" :
                            customer.setState(cNode.getTextContent());
                            break;
                        case "postalcode" :
                            customer.setPostalcode(cNode.getTextContent());
                            break;
                        case "country" :
                            customer.setCountry(cNode.getTextContent());
                            break;
                        case "salesrepemployeenumber" :
                            customer.setSalesrepemployeenumber(cNode.getTextContent());
                            break;
                        case "creditlimit" :
                            customer.setCreditlimit(cNode.getTextContent());
                            break;
                        }
                    }
                    list.add(customer);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Create Sample Data Error", e);
        } 
    }
    
    public String getCustomers() {
        
        StringBuffer fileContents = new StringBuffer();
        
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(XMLFILE);
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        try {
            while ((line = input.readLine()) != null){
                fileContents.append(line);
                fileContents.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                input.close();
            } catch (IOException e) {
            }
        }
        
        return fileContents.toString();
    }
    
    protected List<Customer> getCustomerList(){
        
        System.out.println("get customer list");
        
        return list;
    }
    
    protected Customer getCustomerByNumber(String customernumber) {
        
        System.out.println("get customer by customernumber: " + customernumber);
        
        Customer customer = null;
        for(Customer c : list) {
            if(c.getCustomernumber().equals(customernumber)){
                customer = c;
                break;
            }
        }
        if(null != customer) {
            return customer;
        } else {
            throw new RuntimeException("Can not find Customer via Customer Number " + customernumber);
        }
    }
    
    protected Customer getCustomerByName(String customername) {
        
        System.out.println("get customer by customername: " + customername);
        
        Customer customer = null;
        for(Customer c : list) {
            if(c.getCustomername().equals(customername)){
                customer = c;
                break;
            }
        }
        if(null != customer) {
            return customer;
        } else {
            throw new RuntimeException("Can not find Customer via Customer Name " + customername);
        }
    }
    
    protected Customer getCustomerByCity(String city) {
        
        System.out.println("get customer by city: " + city);
        
        Customer customer = null;
        for(Customer c : list) {
            if(c.getCity().equals(city)){
                customer = c;
                break;
            }
        }
        if(null != customer) {
            return customer;
        } else {
            throw new RuntimeException("Can not find Customer via City " + city);
        }
    }
    
    protected Customer getCustomerByCountry(String country) {
        
        System.out.println("get customer by country: " + country);
        
        Customer customer = null;
        for(Customer c : list) {
            if(c.getCountry().equals(country)){
                customer = c;
                break;
            }
        }
        if(null != customer) {
            return customer;
        } else {
            throw new RuntimeException("Can not find Customer via Country " + country);
        }
    }
    
    protected Customer getCustomerByNumCityCountry(String customernumber, String city, String country) {
        
        System.out.println("get customer by customernumber: " + customernumber + ", city: " + city + ", country: " + country);
        
        Customer customer = null;
        for(Customer c : list) {
            if(c.getCustomernumber().equals(customernumber) && c.getCountry().equals(country) && c.getCity().equals(city)){
                customer = c;
                break;
            }
        }
        if(null != customer) {
            return customer;
        } else {
            throw new RuntimeException("Can not find Customer via City " + city);
        }
    }
    
    protected void removeById(String id) {
        
        System.out.println("remove by id: " + id);
        
        int index = -1;
        for(int i = 0 ; i < list.size() ; i++) {
            Customer c = list.get(i);
            if(c.getCustomernumber().equals(id)) {
                index = i;
                break;
            }
        }
        
        if(index != -1) {
            list.remove(index);
        }
    }
    
    protected void removeByCustomernumber(String customernumber) {
        
        System.out.println("remove by customernumber: " + customernumber);
        
        int index = -1;
        for(int i = 0 ; i < list.size() ; i++) {
            Customer c = list.get(i);
            if(c.getCustomernumber().equals(customernumber)) {
                index = i;
                break;
            }
        }
        
        if(index != -1) {
            list.remove(index);
        }
    }
    
    protected void removeByCustomername(String customername) {
        
        System.out.println("remove by customername: " + customername);
        
        int index = -1;
        for(int i = 0 ; i < list.size() ; i++) {
            Customer c = list.get(i);
            if(c.getCustomername().equals(customername)) {
                index = i;
                break;
            }
        }
        
        if(index != -1) {
            list.remove(index);
        }
    }
    
    protected void removeByCity(String city) {
        
        System.out.println("remove by city: " + city);
        
        int index = -1;
        for(int i = 0 ; i < list.size() ; i++) {
            Customer c = list.get(i);
            if(c.getCity().equals(city)) {
                index = i;
                break;
            }
        }
        
        if(index != -1) {
            list.remove(index);
        }
    }
    
    protected void removeByCountry(String country) {
        
        System.out.println("remove by country: " + country);
        
        int index = -1;
        for(int i = 0 ; i < list.size() ; i++) {
            Customer c = list.get(i);
            if(c.getCountry().equals(country)) {
                index = i;
                break;
            }
        }
        
        if(index != -1) {
            list.remove(index);
        }
    }
    
    protected void removeByNumCityCountry(String customernumber, String city, String country) {
        
        System.out.println("remove by customernumber: " + customernumber + ", city: " + city + ", country: " + country);

        int index = -1;
        for(int i = 0 ; i < list.size() ; i++) {
            Customer c = list.get(i);
            if(c.getCustomernumber().equals(customernumber) && c.getCity().equals(city) && c.getCountry().equals(country)) {
                index = i;
                break;
            }
        }
        
        if(index != -1) {
            list.remove(index);
        }
    }
    
    protected void addCustomer(Customer customer){
        
        System.out.println("add customer: " + customer);
        
        if(!isExist(customer.getCustomernumber())){
            list.add(customer);
        }
    }
    
    private boolean isExist(String customernumber) {
        boolean exist = false;
        for(Customer c : list) {
            if(c.getCustomernumber().equals(customernumber)){
                exist = true;
                break;
            }
        }
        return exist;
    }  

}
