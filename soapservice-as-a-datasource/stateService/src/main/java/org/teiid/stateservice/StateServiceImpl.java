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
package org.teiid.stateservice;

import org.teiid.stateservice.StateService;
import org.teiid.stateservice.jaxb.StateInfo;

import javax.jws.WebService;

@WebService(serviceName = "stateService", endpointInterface = "org.teiid.stateservice.StateService", targetNamespace = "http://www.teiid.org/stateService/")
public class StateServiceImpl implements StateService {
	
	StateData stateData = new StateData();
	
	public java.util.List<org.teiid.stateservice.jaxb.StateInfo> getAllStateInfo() {
		return stateData.getAll();
	}

	public org.teiid.stateservice.jaxb.StateInfo getStateInfo(java.lang.String stateCode) throws GetStateInfoFault_Exception {
		StateInfo info = stateData.getData(stateCode);
		if(null == info) {
			throw new GetStateInfoFault_Exception(stateCode + " is not a valid state abbreviation");
		}
		return info;
	}
}