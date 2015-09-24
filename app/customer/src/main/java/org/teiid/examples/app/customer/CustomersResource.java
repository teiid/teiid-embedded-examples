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
