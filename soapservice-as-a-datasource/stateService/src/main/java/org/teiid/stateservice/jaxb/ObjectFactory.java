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
package org.teiid.stateservice.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.teiid.stateservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.teiid.stateservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllStateInfo }
     * 
     */
    public GetAllStateInfo createGetAllStateInfo() {
        return new GetAllStateInfo();
    }

    /**
     * Create an instance of {@link StateInfo }
     * 
     */
    public StateInfo createStateInfo() {
        return new StateInfo();
    }

    /**
     * Create an instance of {@link GetAllStateInfoResponse }
     * 
     */
    public GetAllStateInfoResponse createGetAllStateInfoResponse() {
        return new GetAllStateInfoResponse();
    }

    /**
     * Create an instance of {@link GetStateInfo }
     * 
     */
    public GetStateInfo createGetStateInfo() {
        return new GetStateInfo();
    }

    /**
     * Create an instance of {@link GetStateInfoFault }
     * 
     */
    public GetStateInfoFault createGetStateInfoFault() {
        return new GetStateInfoFault();
    }

    /**
     * Create an instance of {@link GetStateInfoResponse }
     * 
     */
    public GetStateInfoResponse createGetStateInfoResponse() {
        return new GetStateInfoResponse();
    }

}
