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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.resource.ResourceException;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.jboss.jca.adapters.jdbc.local.LocalManagedConnectionFactory;
import org.jboss.jca.core.api.connectionmanager.pool.PoolConfiguration;
import org.jboss.jca.core.connectionmanager.notx.NoTxConnectionManagerImpl;
import org.jboss.jca.core.connectionmanager.pool.strategy.OnePool;
import org.jboss.logmanager.ConfigurationLocator;
import org.jboss.logmanager.Configurator;
import org.jboss.logmanager.LogContext;
import org.jboss.logmanager.PropertyConfigurator;
import org.teiid.logging.MessageLevel;

import com.arjuna.ats.arjuna.common.ObjectStoreEnvironmentBean;
import com.arjuna.ats.arjuna.common.arjPropertyManager;
import com.arjuna.common.internal.util.propertyservice.BeanPopulator;

/**
 * The <code>EmbeddedHelper</code> is a Helper class for running Teiid Embedded. 
 * 
 * The 'getTransactionManager()' method return a Narayana JTA TransactionManager which used to set to EmbeddedConfiguration:
 * 
 * <pre>
 *  EmbeddedServer server = new EmbeddedServer();
 *  ...
 *  EmbeddedConfiguration config = new EmbeddedConfiguration();
 *  config.setTransactionManager(EmbeddedHelper.getTransactionManager());
 *  server.start(config);
 * </pre>
 * 
 * The 'newDataSource' method return a ironjacamar WrapperDataSource which used to set EmbeddedServer's named connection factory:
 * 
 * <pre>
 *  EmbeddedServer server = new EmbeddedServer();
 *  ...
 *  DataSource ds = EmbeddedHelper.newDataSource("${driver}", "${url}", "${user}", "${password}");
 *  server.addConnectionFactory("name", ds);
 *  ...
 * </pre>
 * 
 *
 */
public class EmbeddedHelper {
	
	public static TransactionManager getTransactionManager() throws Exception {
		
		arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(UUID.randomUUID().toString());
		arjPropertyManager.getCoreEnvironmentBean().setSocketProcessIdPort(0);
		arjPropertyManager.getCoreEnvironmentBean().setSocketProcessIdMaxPorts(10);
		
		arjPropertyManager.getCoordinatorEnvironmentBean().setEnableStatistics(false);
		arjPropertyManager.getCoordinatorEnvironmentBean().setDefaultTimeout(300);
		arjPropertyManager.getCoordinatorEnvironmentBean().setTransactionStatusManagerEnable(false);
		arjPropertyManager.getCoordinatorEnvironmentBean().setTxReaperTimeout(120000);
		
		String storeDir = getStoreDir();
		
		arjPropertyManager.getObjectStoreEnvironmentBean().setObjectStoreDir(storeDir);
		BeanPopulator.getNamedInstance(ObjectStoreEnvironmentBean.class, "communicationStore").setObjectStoreDir(storeDir); //$NON-NLS-1$
		
		return com.arjuna.ats.jta.TransactionManager.transactionManager();
	}

	
	private static String getStoreDir() {
		String defDir = getSystemProperty("user.home") + File.separator + ".teiid/embedded/data"; //$NON-NLS-1$ //$NON-NLS-2$
		return getSystemProperty("teiid.embedded.txStoreDir", defDir); //$NON-NLS-1$
	}


	private static String getSystemProperty(final String name, final String value) {
		return AccessController.doPrivileged(new PrivilegedAction<String>(){

			@Override
			public String run() {
				return System.getProperty(name, value);
			}});
	}
	
	private static String getSystemProperty(final String name) {
		return AccessController.doPrivileged(new PrivilegedAction<String>(){

			@Override
			public String run() {
				return System.getProperty(name);
			}});
	}
	
	private static void setSystemProperty(final String key, final String value) {
        if (System.getSecurityManager() == null) {
            System.setProperty(key, value);
        } else {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    System.setProperty(key, value);
                    return null;
                }
            });
        }
    }
	
	public static DataSource newDataSource(String driverClass, String connURL, String user, String password) throws ResourceException{

		LocalManagedConnectionFactory mcf = new LocalManagedConnectionFactory();
		
		mcf.setDriverClass(driverClass);
		mcf.setConnectionURL(connURL);
		mcf.setUserName(user);
		mcf.setPassword(password);
		
		NoTxConnectionManagerImpl cm = new NoTxConnectionManagerImpl();
		OnePool pool = new OnePool(mcf, new PoolConfiguration(), false);
		pool.setConnectionListenerFactory(cm);
		cm.setPool(pool);
		
		return (DataSource) mcf.createConnectionFactory(cm);
	}
	
	public static DataSource newDataSource(String driverClass, String connURL, String user, String password, String connectionProperties) throws ResourceException{

		LocalManagedConnectionFactory mcf = new LocalManagedConnectionFactory();
		
		mcf.setDriverClass(driverClass);
		mcf.setConnectionURL(connURL);
		mcf.setUserName(user);
		mcf.setPassword(password);
		mcf.setConnectionProperties(connectionProperties);
		
		NoTxConnectionManagerImpl cm = new NoTxConnectionManagerImpl();
		OnePool pool = new OnePool(mcf, new PoolConfiguration(), false);
		pool.setConnectionListenerFactory(cm);
		cm.setPool(pool);
		
		return (DataSource) mcf.createConnectionFactory(cm);
	}
	
	/**
	 * Configure JBoss LogManager via a configuration properties.
	 * 
	 * @param config - properties file path
	 */
	public static void configureLogManager(String config) {
		
		if (getSystemProperty("java.util.logging.manager") == null) { //$NON-NLS-1$ 
			try {
				setSystemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager"); //$NON-NLS-1$ //$NON-NLS-2$
				setSystemProperty("logging.configuration", config); //$NON-NLS-1$ 
				setSystemProperty("org.jboss.logmanager.configurationLocator", TeiidLoggerConfigurationLocator.class.getName()); //$NON-NLS-1$ 
				LogManager.getLogManager();
			} catch (SecurityException e) {
                System.err.println("ERROR: Could not configure LogManager"); //$NON-NLS-1$ 
            } catch (Throwable ignored) {
            }
		}
	}
	
	/**
	 * Configure JBoss LogManager programmtically
	 * 
	 * @param logLevel 
	 * @param logFile
	 */
	public static void configureLogManager(String logLevel, String logFile) {
		if (getSystemProperty("java.util.logging.manager") == null) { //$NON-NLS-1$ 
			try {
				setSystemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager"); //$NON-NLS-1$ //$NON-NLS-2$
				setSystemProperty("org.jboss.logmanager.configurationLocator", TeiidLoggerConfigurationLocator.class.getName()); //$NON-NLS-1$ 
				
				final LogManager logManager = LogManager.getLogManager();
                if (logManager instanceof org.jboss.logmanager.LogManager) {
                	if (LogContext.getSystemLogContext().getAttachment("", Configurator.ATTACHMENT_KEY) == null){ //$NON-NLS-1$ 
                		final PropertyConfigurator configurator = new PropertyConfigurator();
                		final Configurator appearing = LogContext.getSystemLogContext().getLogger("").attachIfAbsent(Configurator.ATTACHMENT_KEY, configurator);
                		if (appearing == null) {
                            configurator.configure(createLogManagerConfig(logLevel, logFile));
                        }
                	}
                }
			} catch (SecurityException e) {
                System.err.println("ERROR: Could not configure LogManager"); //$NON-NLS-1$
            } catch (Throwable ignored) {
            	ignored.printStackTrace();
            }
		}
	}
	
	private static Properties createLogManagerConfig(String logLevel, String logFile) {
		
		final Properties properties = new Properties();
        // Root log level
        properties.setProperty("logger.level", logLevel.toUpperCase(Locale.ENGLISH)); //$NON-NLS-1$ 
        properties.setProperty("logger.handlers", "FILE,CONSOLE"); //$NON-NLS-1$ //$NON-NLS-2$
        
        properties.setProperty("handler.CONSOLE", "org.jboss.logmanager.handlers.ConsoleHandler"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.CONSOLE.level", logLevel.toUpperCase(Locale.ENGLISH)); //$NON-NLS-1$ 
        properties.setProperty("handler.CONSOLE.formatter", "COLOR-PATTERN"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.CONSOLE.properties", "autoFlush,target,enabled"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.CONSOLE.autoFlush", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.CONSOLE.target", "SYSTEM_OUT"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.CONSOLE.enabled", "true"); //$NON-NLS-1$ //$NON-NLS-2$

        // Configure the handler
        properties.setProperty("handler.FILE", "org.jboss.logmanager.handlers.PeriodicRotatingFileHandler"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.FILE.level", logLevel.toUpperCase(Locale.ENGLISH)); //$NON-NLS-1$
        properties.setProperty("handler.FILE.formatter", "PATTERN"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.FILE.properties", "autoFlush,append,fileName,enabled"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.FILE.constructorProperties", "fileName,append"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.FILE.append", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.FILE.autoFlush", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.FILE.enabled", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.FILE.suffix", ".yyyy-MM-dd"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("handler.FILE.fileName", logFile); //$NON-NLS-1$ 
        
        // Configure the formatter
        properties.setProperty("formatter.PATTERN", "org.jboss.logmanager.formatters.PatternFormatter"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("formatter.PATTERN.pattern", "%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("formatter.PATTERN.properties", "pattern"); //$NON-NLS-1$ //$NON-NLS-2$
        
        properties.setProperty("formatter.COLOR-PATTERN", "org.jboss.logmanager.formatters.PatternFormatter"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("formatter.COLOR-PATTERN.properties", "pattern"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("formatter.COLOR-PATTERN.pattern", "%K{level}%d{HH\\:mm\\:ss,SSS} %-5p [%c] (%t) %s%e%n"); //$NON-NLS-1$ //$NON-NLS-2$
        
        return properties;
	}
	
	public static void enableLogger(Level level) {
        enableLogger(level, "org.teiid"); //$NON-NLS-1$
    }
    
	/**
     * This method supply function for adjusting Teiid logging while running the Embedded mode.
     * 
     * For example, with parameters names is 'org.teiid.COMMAND_LOG' and level is 'FINEST' Teiid
     * will show command logs in console.
     * 
     * @param formatter
     *          The Formatter used in ConsoleHandler 
     * @param level
     *          The logger level used in Logger
     * @param names
     *          The logger's name
     */
    public static void enableLogger(Level level, String... names){
        enableLogger(new TeiidLoggerFormatter(), Level.SEVERE, level, names);
    }
    
    public static void enableLogger(Formatter formatter, Level rootLevel, Level level, String... names){
        
        Logger rootLogger = Logger.getLogger("");
        for(Handler handler : rootLogger.getHandlers()){
            handler.setFormatter(formatter);
            handler.setLevel(rootLevel);
        }
        
        for(String name : names) {
            Logger logger = Logger.getLogger(name);
            logger.setLevel(level);
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(level);
            handler.setFormatter(formatter);
            logger.addHandler(handler);
            logger.setUseParentHandlers(false);
        }
        
        org.teiid.logging.LogManager.isMessageToBeRecorded("org.teiid", MessageLevel.INFO);
    }
    
    public static class TeiidLoggerFormatter extends Formatter {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm SSS"); //$NON-NLS-1$ 

        public String format(LogRecord record) {
            StringBuffer sb = new StringBuffer();
            sb.append(format.format(new Date(record.getMillis())) + " "); //$NON-NLS-1$ 
            sb.append(getLevelString(record.getLevel()) + " "); //$NON-NLS-1$ 
            sb.append("[" + record.getLoggerName() + "] ("); //$NON-NLS-1$ //$NON-NLS-2$  
            sb.append(Thread.currentThread().getName() + ") "); //$NON-NLS-1$ 
            sb.append(record.getMessage() + "\n"); //$NON-NLS-1$ 
            return sb.toString();
        }

        private String getLevelString(Level level) {
            String name = level.toString();
            int size = name.length();
            for(int i = size; i < 7 ; i ++){
                name += " ";
            }
            return name;
        }
    }
    
    public static final class TeiidLoggerConfigurationLocator implements ConfigurationLocator {

		@Override
		public InputStream findConfiguration() throws IOException {
			final String propLoc = System.getProperty("logging.configuration"); //$NON-NLS-1$ 
	        if (propLoc != null) try {
	            return new FileInputStream(propLoc);
	        } catch (IOException e) {
	            System.err.printf("Unable to read the logging configuration from '%s' (%s)%n", propLoc, e); //$NON-NLS-1$ 
	        }
	        
	        return null; 
		}
	}


}
