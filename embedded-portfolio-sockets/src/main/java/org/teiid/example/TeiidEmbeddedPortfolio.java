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

import static org.teiid.example.JDBCUtils.execute;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.teiid.resource.adapter.file.FileManagedConnectionFactory;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.file.FileExecutionFactory;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;
import org.teiid.transport.SocketConfiguration;
import org.teiid.transport.WireProtocol;


/**
 * This example is same as "embedded-portfolio" example, except this demonstrates exposing teiid embedded
 * using sockets, so that a client application running in another VM can connect.
 * 
 * This example will start teiid embedded as the "Server" and then start a "Client" thread that will connect to 
 * the server and issue the same queries issued in the "embedded-portfolio" example.
 */
@SuppressWarnings("nls")
public class TeiidEmbeddedPortfolio {
	
	
	public static void main(String[] args) throws Exception {
		Server s = new Server();
		new Thread(s).start();

		Thread.sleep(10000);
		
		Client c = new Client();
		new Thread(c).start();

	}


}

final class Server implements Runnable {

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()  {
		
		try {
			SocketConfiguration s = new SocketConfiguration();
			s.setBindAddress("localhost");
			s.setPortNumber(31000);
			s.setProtocol(WireProtocol.teiid);
			
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
			config.addTransport(s);	
			server.start(config);
	    	
			server.deployVDB(TeiidEmbeddedPortfolio.class.getClassLoader().getResourceAsStream("portfolio-vdb.xml"));
			
			
			System.out.println("****************************");
			System.out.println("Embedded Started with socket port 31000");
			System.out.println("****************************");
	
			
		} catch (Throwable t) {
			t.printStackTrace();
			return;
		}
	}
	
}

final class Client implements Runnable {

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			
			System.out.println("****************************");
			System.out.println("Client connecting via sockets to Embedded");
			System.out.println("****************************");


			Class.forName("org.teiid.jdbc.TeiidDriver").newInstance();
			Connection c = DriverManager.getConnection("jdbc:teiid:Portfolio@mm://localhost:31000");
			
			execute(c, "select * from Product", false);
			execute(c, "select * from StockPrices", false);
			execute(c, "select * from Stock", false);
			execute(c, "SELECT stock.* from (call MarketData.getTextFiles('*.txt')) f, TEXTTABLE(f.file COLUMNS symbol string, price bigdecimal HEADER) stock", false);
			execute(c, "select product.symbol, stock.price, company_name from product, (call MarketData.getTextFiles('*.txt')) f, TEXTTABLE(f.file COLUMNS symbol string, price bigdecimal HEADER) stock where product.symbol=stock.symbol", true); 
		}catch(Throwable t) {
			t.printStackTrace();
		}
	}
	
}


