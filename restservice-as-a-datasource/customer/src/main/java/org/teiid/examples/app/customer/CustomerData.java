/*
 * Copyright Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags and
 * the COPYRIGHT.txt file distributed with this work.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        return list;
    }
    
    protected Customer getCustomerByNumber(String customernumber) {
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
            throw new RuntimeException("Can not find Customer via Country( " + country);
        }
    }
    
    protected void removeById(String id) {
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

    public static void main(String[] args){
        
        CustomerData data = new CustomerData();
        
        System.out.println(data.getCustomerByNumber("112"));
        
        System.out.println(data.getCustomerByName("Signal Gift Stores"));
        
        System.out.println(data.getCustomerByCity("Las Vegas"));
        
        System.out.println(data.getCustomerByCountry("USA"));

        List<Customer> list = data.getCustomerList();
        
        System.out.println(list.size());
        
        

    }

    

}
