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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;

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
@Api(value="/customer")
public class CustomersResource {
	
    CustomerData data = new CustomerData();
    
    @GET
    @Path("/customerList")
    @Produces({ MediaType.APPLICATION_XML })
    @ApiOperation(value = "get customer list", notes = "get customer list as xml")
    @ApiResponses({})
	public String  getCustomers() {
        return data.getCustomers();
    }
    
    @GET
    @Path("/status")
    @ApiOperation(value = "get customer status", notes = "get customer status as xml/json")
    @ApiResponses({})
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
    @ApiOperation(value = "get customers", notes = "get customers as xml/json")
    @ApiResponses({})
    public List<Customer>  getCustomerList() {
        return data.getCustomerList();
    }
    
    @GET
    @Path("/getByNumber/{customernumber}")
    @ApiOperation(value = "get customer by number", notes = "get customer by number as return xml/json")
    @ApiResponses({})
    public Customer getCustomerByNumber(@ApiParam(value = "customernumber", required = true) @PathParam("customernumber") String customernumber) {
        return data.getCustomerByNumber(customernumber);
    }
    
    @GET
    @Path("/getByName/{customername}")
    @ApiOperation(value = "get customer by name", notes = "get customer by name as return xml/json")
    @ApiResponses({})
    public Customer  getCustomerByName(@ApiParam(value = "customername", required = true) @PathParam("customername") String customername) {
        return data.getCustomerByName(customername);
    }
    
    @GET
    @Path("/getByCity/{city}")
    @ApiOperation(value = "get customer by city", notes = "get customer by city as return xml/json")
    @ApiResponses({})
    public Customer  getCustomerByCity(@ApiParam(value = "city", required = true) @PathParam("city") String city) {
        return data.getCustomerByCity(city);
    }
    
    @GET
    @Path("/getByCountry/{country}")
    @ApiOperation(value = "get customer by country", notes = "get customer by country as return xml/json")
    @ApiResponses({})
    public Customer  getCustomerByCountry(@ApiParam(value = "country", required = true) @PathParam("country") String country) {
        return data.getCustomerByCountry(country);
    }
    
    @DELETE
    @Path("/delete/{id}")
    @ApiOperation(value = "Delete customer by ID", notes = "Delete customer by ID")
    @ApiResponses({})
    public void removeCustomer(@ApiParam(value = "id", required = true) @PathParam("id") String id){
        data.removeById(id);
    }
}
