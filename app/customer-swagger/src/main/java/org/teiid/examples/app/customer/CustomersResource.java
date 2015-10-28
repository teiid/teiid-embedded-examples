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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.teiid.examples.app.customer.CustomerStatus.HeapSize;

@Path("/customer")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Api(value="/customer")
public class CustomersResource {
	
    CustomerData data = new CustomerData();
    
    @Path("/{customernumber}")
    public Customer getMemberSubresource(@PathParam("customernumber") String customernumber) {
        return data.getCustomerByNumber(customernumber);
    }
    
    @GET
    @Path("/customerList")
    @Produces({ MediaType.APPLICATION_XML })
    @ApiOperation(value = "get customer list", notes = "get customer list as xml")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
	public String  getCustomers() {
        return data.getCustomers();
    }
    
    @GET
    @Path("/getAll")
    @ApiOperation(value = "get customers", notes = "get customers as xml/json")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public List<Customer>  getCustomerList() {
        return data.getCustomerList();
    }
    
    @GET
    @Path("/getByNumber/{customernumber}")
    @ApiOperation(value = "get customer by number", notes = "get customer by number as return xml/json")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public Customer getCustomerByNumber(@ApiParam(value = "customernumber", required = true) @PathParam("customernumber") String customernumber) {
        return data.getCustomerByNumber(customernumber);
    }
    
    @GET
    @Path("/getByName/{customername}")
    @ApiOperation(value = "get customer by name", notes = "get customer by name as return xml/json")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public Customer  getCustomerByName(@ApiParam(value = "customername", required = true) @PathParam("customername") String customername) {
        return data.getCustomerByName(customername);
    }
    
    @GET
    @Path("/getByCity")
    @ApiOperation(value = "get customer by city", notes = "get customer by city as return xml/json")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public Customer  getCustomerByCity(@ApiParam(value = "city", required = true) @QueryParam("city") String city) {
        return data.getCustomerByCity(city);
    }
    
    @GET
    @Path("/getByCountry")
    @ApiOperation(value = "get customer by country", notes = "get customer by country as return xml/json")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public Customer  getCustomerByCountry(@ApiParam(value = "country", required = true) @QueryParam("country") String country) {
        return data.getCustomerByCountry(country);
    }
    
    @GET
    @Path("/status")
    @ApiOperation(value = "get customer status", notes = "get customer status as xml/json")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
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
    
    @DELETE
    @Path("/delete/{id}")
    @ApiOperation(value = "Delete customer by ID", notes = "Delete customer by ID")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public void removeCustomer(@ApiParam(value = "id", required = true) @PathParam("id") String id){
        data.removeById(id);
    }
    
    @POST
    @Consumes("application/xml")
    @ApiOperation(value = "Add a Customer",response = Customer.class)
    @ApiResponses({ @ApiResponse(code = 400, message = "Invalid Customer") })
    public Response addCustomer(@ApiParam(value = "Customer Object need be passed", required = true)Customer customer) {
        data.addCustomer(customer);
        return Response.ok().entity("Add Customer Success").build();
    }
    
    @PUT
    @Path("/update")
    @ApiOperation(value = "Update an existing customer")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Customer supplied") })
    public Response updateCustomer(@ApiParam(value = "Customer object that needs to be passed", required = true) Customer c){
        Customer customer = data.getCustomerByNumber(c.getCustomernumber());
        customer.update(customer);
        return Response.ok().entity("Update Customer Success").build();
    }
    
}
