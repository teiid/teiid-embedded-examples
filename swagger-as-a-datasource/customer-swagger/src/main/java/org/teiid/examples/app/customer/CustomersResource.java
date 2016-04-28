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
    public Customer getCustomerByCountry(@ApiParam(value = "country", required = true) @QueryParam("country") String country) {
        return data.getCustomerByCountry(country);
    }
    
    @GET
    @Path("/getByNumCityCountry")
    @ApiOperation(value = "get customer by Number, City, Country", notes = "get customer by Number, City, Country as return xml/json")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public Customer getByNumCityCountry(@ApiParam(value = "customernumber", required = true) @QueryParam("customernumber") String customernumber, @ApiParam(value = "city", required = true) @QueryParam("city") String city, @ApiParam(value = "country", required = true) @QueryParam("country") String country) {
        return data.getCustomerByNumCityCountry(customernumber, city, country);
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
    
    @DELETE
    @Path("/deleteByNumber/{customernumber}")
    @ApiOperation(value = "Delete customer by Number", notes = "Delete customer by Number")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public void removeCustomerByNumber(@ApiParam(value = "customernumber", required = true) @PathParam("customernumber") String customernumber){
        data.removeByCustomernumber(customernumber);
    }
    
    @DELETE
    @Path("/deleteByName/{customername}")
    @ApiOperation(value = "Delete customer by Name", notes = "Delete customer by Name")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public void removeCustomerByName(@ApiParam(value = "customername", required = true) @PathParam("customername") String customername){
        data.removeByCustomername(customername);
    }
    
    @DELETE
    @Path("/deleteByCity")
    @ApiOperation(value = "Delete customer by City", notes = "Delete customer by City")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public void removeCustomerByCity(@ApiParam(value = "city", required = true) @QueryParam("city") String city){
        data.removeByCity(city);
    }
    
    @DELETE
    @Path("/deleteByCountry")
    @ApiOperation(value = "Delete customer by Country", notes = "Delete customer by Country")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public void removeCustomerByCountry(@ApiParam(value = "country", required = true) @QueryParam("country") String country){
        data.removeByCountry(country);
    }
    
    @DELETE
    @Path("/deleteByNumCityCountry")
    @ApiOperation(value = "Delete customer by Number, Name, Country", notes = "Delete customer by Number, Name, Country")
    @ApiResponses({@ApiResponse(code = 404, message = "Customer not found")})
    public void removeCustomerByNumCityCountry(@ApiParam(value = "customernumber", required = true) @QueryParam("customernumber") String customernumber, @ApiParam(value = "city", required = true) @QueryParam("city") String city, @ApiParam(value = "country", required = true) @QueryParam("country") String country){
        data.removeByNumCityCountry(customernumber, city, country);
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Add a Customer")
    @ApiResponses({ @ApiResponse(code = 400, message = "Invalid Customer") })
    public Response addCustomer(@ApiParam(value = "Customer Object need be passed", required = true)Customer customer) {
        data.addCustomer(customer);
        return Response.ok().entity(new Success("Add Customer Success")).build();
    }
    
    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Add a Customer")
    @ApiResponses({ @ApiResponse(code = 400, message = "Invalid Customer") })
    public Response addOneCustomer(@ApiParam(value = "Customer Object need be passed", required = true)Customer customer) {
        data.addCustomer(customer);
        return Response.ok().entity(new Success("Add Customer Success")).build();
    }
    
    @POST
    @Path("/addList")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Add Customer List")
    @ApiResponses({ @ApiResponse(code = 400, message = "Invalid Customer") })
    public Response addCustomerList(@ApiParam(value = "Customer Object need be passed", required = true)List<Customer> list) {
        for(Customer customer : list) {
            data.addCustomer(customer);
        }
        return Response.ok().entity(new Success("Add Customer List Success")).build();
    }
    
    @PUT
    @Path("/update")
    @ApiOperation(value = "Update an existing customer")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Customer supplied") })
    public Response updateCustomer(@ApiParam(value = "Customer object that needs to be passed", required = true) Customer c){
        data.removeByCustomernumber(c.getCustomernumber());
        data.addCustomer(c);
        return Response.ok().entity(new Success("Update Customer Success")).build();
    }
    
    @PUT
    @Path("/updateByNumber/{customernumber}")
    @ApiOperation(value = "Update an existing customer")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Customer supplied") })
    public Response updateCustomerByNumber(@ApiParam(value = "customernumber", required = true) @PathParam("customernumber") String customernumber, @ApiParam(value = "Customer object that needs to be passed", required = true) Customer c){
        data.removeByCustomernumber(customernumber);
        data.addCustomer(c);
        return Response.ok().entity(new Success("Update Customer Success")).build();
    }
    
    @PUT
    @Path("/updateByName/{customername}")
    @ApiOperation(value = "Update an existing customer")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Customer supplied") })
    public Response updateCustomerByName(@ApiParam(value = "customername", required = true) @PathParam("customername") String customername, @ApiParam(value = "Customer object that needs to be passed", required = true) Customer c){
        data.removeByCustomername(customername);
        data.addCustomer(c);
        return Response.ok().entity(new Success("Update Customer Success")).build();
    }
    
    @PUT
    @Path("/updateByCity")
    @ApiOperation(value = "Update an existing customer")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Customer supplied") })
    public Response updateCustomerByCity(@ApiParam(value = "city", required = true) @QueryParam("city") String city, @ApiParam(value = "Customer object that needs to be passed", required = true) Customer c){
        data.removeByCity(city);
        data.addCustomer(c);
        return Response.ok().entity(new Success("Update Customer Success")).build();
    }
    
    @PUT
    @Path("/updateByCountry")
    @ApiOperation(value = "Update an existing customer")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Customer supplied") })
    public Response updateCustomerByCountry(@ApiParam(value = "country", required = true) @QueryParam("country") String country, @ApiParam(value = "Customer object that needs to be passed", required = true) Customer c){
        data.removeByCountry(country);
        data.addCustomer(c);
        return Response.ok().entity(new Success("Update Customer Success")).build();
    }
    
    @PUT
    @Path("/updateByNumCityCountry")
    @ApiOperation(value = "Update an existing customer")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Customer supplied") })
    public Response updateCustomerByNumCityCountry(@ApiParam(value = "customernumber", required = true) @QueryParam("customernumber") String customernumber, @ApiParam(value = "city", required = true) @QueryParam("city") String city, @ApiParam(value = "country", required = true) @QueryParam("country") String country, @ApiParam(value = "Customer object that needs to be passed", required = true) Customer c){
        data.removeByNumCityCountry(customernumber, city, country);
        data.addCustomer(c);
        return Response.ok().entity(new Success("Update Customer Success")).build();
    }
    
}
