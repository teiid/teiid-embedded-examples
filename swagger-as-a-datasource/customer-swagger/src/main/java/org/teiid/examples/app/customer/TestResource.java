package org.teiid.examples.app.customer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.teiid.examples.app.customer.CustomerStatus.HeapSize;

@Path("/test")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Api(value="/test")
public class TestResource {
    
    @GET
    @Path("/ping")
    @ApiOperation(value = "ping", notes = "ping")
    @ApiResponses({@ApiResponse(code = 404, message = "ping")})
    public Success ping() {
        return new Success("ping success");
    }
    
    @GET
    @Path("/testTypes")
    @ApiOperation(value = "testTypes", notes = "testTypes")
    @ApiResponses({@ApiResponse(code = 404, message = "testTypes")})
    public Success testTypes(@ApiParam(value = "a", required = true) @QueryParam("a") int a,
                             @ApiParam(value = "b", required = true) @QueryParam("b") long b,
                             @ApiParam(value = "c", required = true) @QueryParam("c") float c,
                             @ApiParam(value = "d", required = true) @QueryParam("d") double d,
                             @ApiParam(value = "e", required = true) @QueryParam("e") String e,
                             @ApiParam(value = "f", required = true) @QueryParam("f") byte f,
                             @ApiParam(value = "g", required = true) @QueryParam("g") byte[] g,
                             @ApiParam(value = "h", required = true) @QueryParam("h") boolean h,
                             @ApiParam(value = "i", required = true) @QueryParam("i") Date i,
                             @ApiParam(value = "j", required = true) @QueryParam("j") Time j) {
        return new Success("success");
    }
    

    @GET
    @Path("/testReturnTypes")
    @ApiOperation(value = "testReturnTypes", notes = "testReturnTypes")
    @ApiResponses({@ApiResponse(code = 404, message = "testReturnTypes")})
    public Types testReturnTypes() {
        return Types.sampleTypes();
    }
    
    @GET
    @Path("/testModelFacade")
    @ApiOperation(value = "testModelFacade", notes = "testModelFacade")
    @ApiResponses({@ApiResponse(code = 404, message = "testModelFacade")})
    public ModelFacade testModelFacade() {
        
        CustomerData data = new CustomerData();
        ModelFacade facade = new ModelFacade();
        facade.setCustomer(data.getCustomerByNumber("114"));
        
        CustomerStatus status = new CustomerStatus();
        status.setSize(data.getCustomerList().size());
        HeapSize jvm = new HeapSize();
        jvm.setMaxMemory(Runtime.getRuntime().maxMemory());
        jvm.setFreeMemory(Runtime.getRuntime().freeMemory());
        jvm.setAllocatedMemory(Runtime.getRuntime().totalMemory());
        status.setHeap(jvm);
        facade.setCustomerStatus(status);
        
        facade.setSuccess(new Success("testModelFacade"));
        
        facade.setTypes(Types.sampleTypes());
        
        List<Success> list = new ArrayList<Success>();
        for(int i = 0 ; i < 3 ; i ++) {
            list.add(new Success("list-" + i));
        }
        facade.setSuccesslist(list);
        
        Set<Success> set = new HashSet<Success>();
        for(int i = 0 ; i < 3 ; i ++) {
            set.add(new Success("set-" + i));
        }
        facade.setSuccessset(set);
        
        return facade;
    }
    
    @GET
    @Path("/testTimeTypes")
    @ApiOperation(value = "testTimeTypes", notes = "testTimeTypes")
    @ApiResponses({@ApiResponse(code = 404, message = "testTimeTypes")})
    public TimeTypes testTimeTypes() {
        return TimeTypes.create();
    }
    

}
