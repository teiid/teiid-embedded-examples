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
