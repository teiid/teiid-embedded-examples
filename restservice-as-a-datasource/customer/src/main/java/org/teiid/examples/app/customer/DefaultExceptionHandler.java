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

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
		
		String status = "ERROR";
		if(e instanceof NotFoundException){
			status = "404";
		} else if(e instanceof InternalServerErrorException){
			status = "500";
		}
		
		String message = e.getMessage();
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String details = sw.toString();
		
		StringBuilder response = new StringBuilder("<error>");
        response.append("<code>" + status + "</code>");
        response.append("<message>" + message + "</message>");
        response.append("<details>" + details + "</details>");
        response.append("</error>");
        return Response.serverError().entity(response.toString()).type(MediaType.APPLICATION_XML).build();
	}

}
