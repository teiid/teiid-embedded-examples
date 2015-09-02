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

import static org.teiid.example.util.JDBCUtils.execute;
import static org.teiid.example.util.IOUtils.findFile;
import static org.teiid.example.util.IOUtils.findFilePath;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.h2.tools.Server;
import org.teiid.resource.adapter.file.FileManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.file.FileExecutionFactory;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;


/**
 * This example is show security authentication in Teiid Embedded
 */
@SuppressWarnings("nls")
public class TeiidEmbeddedPortfolioSecurity {
	
	private static Server h2Server = null;
	
	public static void main(String[] args) throws Exception {
	    
	    EmbeddedHelper.enableLogger(Level.ALL);
				
		// setup accounts database (if you already have external database this is not needed)
		// for schema take look at "data/customer-schema.sql" file.
		startH2Server();
		
		DataSource ds = EmbeddedHelper.newDataSource("org.h2.Driver", "jdbc:h2:mem://localhost/~/account", "sa", "sa");
		initSamplesData(ds);
		
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
		managedconnectionFactory.setParentDirectory(findFilePath("data"));
		server.addConnectionFactory("java:/marketdata-file", managedconnectionFactory.createConnectionFactory());
	
		EmbeddedConfiguration config = new EmbeddedConfiguration();
		config.setTransactionManager(EmbeddedHelper.getTransactionManager());
		config.setSecurityHelper(EmbeddedHelper.getSecurityHelper());
		server.start(config);
    	
		server.deployVDB(new FileInputStream(findFile("portfolio-vdb.xml")));
		
		Properties info = new Properties();
		info.put("user", "testUser");
		info.put("password", "password1!");
		Connection c = server.getDriver().connect("jdbc:teiid:Portfolio;version=1", info);
		
		System.out.println(c);
		
		execute(c, "select * from Product", false);
//		execute(c, "select * from StockPrices", false);
//		execute(c, "select * from Stock", false);
//		execute(c, "SELECT stock.* from (call MarketData.getTextFiles('*.txt')) f, TEXTTABLE(f.file COLUMNS symbol string, price bigdecimal HEADER) stock", false);
//		execute(c, "select product.symbol, stock.price, company_name from product, (call MarketData.getTextFiles('*.txt')) f, TEXTTABLE(f.file COLUMNS symbol string, price bigdecimal HEADER) stock where product.symbol=stock.symbol", true); 
				
		stopH2Server();
	}

	private static void stopH2Server() {
		h2Server.stop();
	}

	private static void initSamplesData(DataSource ds) throws FileNotFoundException, SQLException {
		RunScript.execute(ds.getConnection(), new FileReader(findFile("customer-schema.sql")));
	}


	private static void startH2Server() throws SQLException {
		h2Server = Server.createTcpServer().start();	
	}	

}
