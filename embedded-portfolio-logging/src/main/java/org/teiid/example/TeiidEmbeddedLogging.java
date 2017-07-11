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

package org.teiid.example;

import org.teiid.logging.LogManager;
import org.teiid.logging.MessageLevel;
import org.teiid.runtime.JBossLogger;

public class TeiidEmbeddedLogging {
    
    
    static {
        LogManager.setLogListener(new JBossLogger());
        
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
    }
    
    static final String CTX_EXAMPLE = "org.teiid.EXAMPLE";
	
	public static void main(String[] args) throws Exception {
	    
	    LogManager.log(MessageLevel.CRITICAL, CTX_EXAMPLE, "This is critical message");
	    LogManager.log(MessageLevel.ERROR, CTX_EXAMPLE, "This is error message");
	    LogManager.log(MessageLevel.WARNING, CTX_EXAMPLE, "This is warning message");
	    LogManager.log(MessageLevel.INFO, CTX_EXAMPLE, "This is info message");
	    LogManager.log(MessageLevel.DETAIL, CTX_EXAMPLE, "This is detail message");
	    LogManager.log(MessageLevel.TRACE, CTX_EXAMPLE, "This is trace message");
	    
	    LogManager.isMessageToBeRecorded(CTX_EXAMPLE, MessageLevel.CRITICAL);
	    LogManager.isMessageToBeRecorded(CTX_EXAMPLE, MessageLevel.ERROR);
	    LogManager.isMessageToBeRecorded(CTX_EXAMPLE, MessageLevel.WARNING);
	    LogManager.isMessageToBeRecorded(CTX_EXAMPLE, MessageLevel.INFO);
	    LogManager.isMessageToBeRecorded(CTX_EXAMPLE, MessageLevel.DETAIL);
	    LogManager.isMessageToBeRecorded(CTX_EXAMPLE, MessageLevel.TRACE);
	    
	    LogManager.logCritical(CTX_EXAMPLE, "Test critical message");
	    LogManager.logError(CTX_EXAMPLE, "Test error message");
	    LogManager.logWarning(CTX_EXAMPLE, "Test warning message");
	    LogManager.logInfo(CTX_EXAMPLE, "Test info message");
	    LogManager.logDetail(CTX_EXAMPLE, "Test detail message");
	    LogManager.logTrace(CTX_EXAMPLE, "Test trace message");
		
	}


}
