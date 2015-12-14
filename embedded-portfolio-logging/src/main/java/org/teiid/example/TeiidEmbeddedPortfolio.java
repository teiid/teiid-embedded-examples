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

import static org.teiid.example.JDBCUtils.execute;

import java.io.InputStreamReader;
import java.sql.Connection;

import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.teiid.logging.LogManager;
import org.teiid.resource.adapter.file.FileManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.runtime.JBossLogger;
import org.teiid.translator.file.FileExecutionFactory;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;

@SuppressWarnings("nls")
public class TeiidEmbeddedPortfolio {
    
    static {
        LogManager.setLogListener(new JBossLogger());
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
        
      //Optional
//      String propUrl = TeiidEmbeddedLogging.class.getClassLoader().getResource("logging.properties").toString();
//      System.setProperty("logging.configuration", propUrl);
    }
		
	public static void main(String[] args) throws Exception {
		
		DataSource ds = EmbeddedHelper.newDataSource("org.h2.Driver", "jdbc:h2:mem://localhost/~/account", "sa", "sa");
		RunScript.execute(ds.getConnection(), new InputStreamReader(TeiidEmbeddedPortfolio.class.getClassLoader().getResourceAsStream("data/customer-schema.sql")));
		
		EmbeddedServer server = new EmbeddedServer();
		
		H2ExecutionFactory executionFactory = new H2ExecutionFactory() ;
		executionFactory.setSupportsDirectQueryProcedure(true);
		executionFactory.start();
		server.addTranslator("translator-h2", executionFactory);
		
		server.addConnectionFactory("java:/accounts-ds", ds);
		
    	FileExecutionFactory fileExecutionFactory = new FileExecutionFactory();
    	fileExecutionFactory.start();
    	server.addTranslator("file", fileExecutionFactory);
    	
    	FileManagedConnectionFactory managedconnectionFactory = new FileManagedConnectionFactory();
		managedconnectionFactory.setParentDirectory("src/main/resources/data");
		server.addConnectionFactory("java:/marketdata-file", managedconnectionFactory.createConnectionFactory());
	
		EmbeddedConfiguration config = new EmbeddedConfiguration();
		config.setTransactionManager(EmbeddedHelper.getTransactionManager());	
		server.start(config);
    	
		server.deployVDB(TeiidEmbeddedPortfolio.class.getClassLoader().getResourceAsStream("portfolio-vdb.xml"));
		
		Connection c = server.getDriver().connect("jdbc:teiid:Portfolio", null);
		
		execute(c, "select * from Product", false);
		execute(c, "select * from StockPrices", false);
		execute(c, "select * from Stock", false);
		execute(c, "SELECT stock.* from (call MarketData.getTextFiles('*.txt')) f, TEXTTABLE(f.file COLUMNS symbol string, price bigdecimal HEADER) stock", false);
		execute(c, "select product.symbol, stock.price, company_name from product, (call MarketData.getTextFiles('*.txt')) f, TEXTTABLE(f.file COLUMNS symbol string, price bigdecimal HEADER) stock where product.symbol=stock.symbol", true); 
				
	}

}
