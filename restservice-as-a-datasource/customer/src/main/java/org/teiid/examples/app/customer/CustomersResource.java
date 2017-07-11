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

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.teiid.examples.app.customer.CustomerStatus.HeapSize;

@Path("/customer")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class CustomersResource {
	
    CustomerData data = new CustomerData();
    
    @GET
    @Path("/customerList")
    @Produces({ MediaType.APPLICATION_XML })
	public String  getCustomers() {
        return data.getCustomers();
    }
    
    @GET
    @Path("/status")
    public CustomerStatus  size() {
        CustomerStatus status = new CustomerStatus();
        status.setSize(data.getCustomerList().size());
        HeapSize jvm = new HeapSize();
        jvm.setMaxMemory(Runtime.getRuntime().maxMemory());
        jvm.setFreeMemory(Runtime.getRuntime().freeMemory());
        jvm.setAllocatedMemory(Runtime.getRuntime().totalMemory());
        status.setHeap(jvm);
        return status;
    }
    
    @GET
    @Path("/getAll")
    public List<Customer>  getCustomerList() {
        return data.getCustomerList();
    }
    
    @GET
    @Path("/getByNumber/{customernumber}")
    public Customer getCustomerByNumber(@PathParam("customernumber") String customernumber) {
        return data.getCustomerByNumber(customernumber);
    }
    
    @GET
    @Path("/getByName/{customername}")
    public Customer  getCustomerByName(@PathParam("customername") String customername) {
        return data.getCustomerByName(customername);
    }
    
    @GET
    @Path("/getByCity/{city}")
    public Customer  getCustomerByCity(@PathParam("city") String city) {
        return data.getCustomerByCity(city);
    }
    
    @GET
    @Path("/getByCountry/{country}")
    public Customer  getCustomerByCountry(@PathParam("country") String country) {
        return data.getCustomerByCountry(country);
    }
    
    @DELETE
    @Path("/delete/{id}")
    public void removeCustomer(@PathParam("id") String id){
        data.removeById(id);
    }
}
