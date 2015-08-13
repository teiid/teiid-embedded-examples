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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.teiid.example.util.JDBCUtils.close;

import java.io.File;
import java.sql.Connection;
import java.util.logging.Level;

import javax.sql.DataSource;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.TransactionManager;

import org.junit.Test;
import org.teiid.logging.LogConstants;
import org.teiid.logging.LogManager;
import org.teiid.logging.MessageLevel;

public class TestEmbeddedHelper {
    
    @Test
    public void commitTransactionManager() throws Exception {
        TransactionManager tm = EmbeddedHelper.getTransactionManager();
        tm.begin();
        tm.commit();
    }
    
    @Test
    public void rollbackTransaction() throws Exception {
        TransactionManager tm = EmbeddedHelper.getTransactionManager();
        tm.begin();
        tm.commit();
    }
    
    @Test(expected = RollbackException.class)
    public void setRollbackOnly() throws Exception {
        TransactionManager tm = EmbeddedHelper.getTransactionManager();
        tm.begin();
        tm.setRollbackOnly();
        tm.commit();
    }
    
    @Test
    public void transactionStatus() throws Exception {
        TransactionManager tm = EmbeddedHelper.getTransactionManager();
        tm.begin();
        assertEquals(Status.STATUS_ACTIVE, tm.getTransaction().getStatus());
        tm.setRollbackOnly();
        assertEquals(Status.STATUS_MARKED_ROLLBACK, tm.getTransaction().getStatus());
        tm.rollback();
    }

    @Test(expected=RollbackException.class)
    public void transactionTimeout() throws Exception{
        TransactionManager tm = EmbeddedHelper.getTransactionManager();
        tm.setTransactionTimeout(3);
        tm.begin();
        Thread.sleep(1000 * 5);
        tm.commit();
    }
    
    @Test
    public void testDataSource() throws Exception {
        DataSource ds = EmbeddedHelper.newDataSource("org.h2.Driver", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
        Connection conn = ds.getConnection();
        assertNotNull(conn);
        close(conn);
    }
    
    @Test
    public void testDataSourceWithProperties() throws Exception {
        DataSource ds = EmbeddedHelper.newDataSource("org.h2.Driver", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa", "foo=1;bar=2;boo=3");
        Connection conn = ds.getConnection();
        assertNotNull(conn);
        close(conn);
    }
    
    @Test
    public void testLogger() {
        EmbeddedHelper.enableLogger(Level.INFO);
        assertFalse(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.DETAIL));
    	assertFalse(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.TRACE));
    	assertFalse(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.INFO));
    	assertTrue(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.WARNING));
    	assertTrue(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.ERROR));
    }
    
    @Test
    public void testLoggerManager(){
    	EmbeddedHelper.configureLogManager("INFO", "teiid.log");
    	assertFalse(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.DETAIL));
    	assertFalse(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.TRACE));
    	assertFalse(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.INFO));
    	assertTrue(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.WARNING));
    	assertTrue(LogManager.isMessageToBeRecorded(LogConstants.CTX_COMMANDLOGGING, MessageLevel.ERROR));
    	
    	new File("teiid.log").deleteOnExit();
    }

}
