package org.teiid.example;

import javax.resource.ResourceException;

import org.jboss.jca.core.api.connectionmanager.pool.PoolConfiguration;
import org.jboss.jca.core.connectionmanager.notx.NoTxConnectionManagerImpl;
import org.jboss.jca.core.connectionmanager.pool.strategy.PoolBySubject;
import org.teiid.resource.spi.BasicManagedConnectionFactory;

public class EmbeddedHelper {
    
    public static Object createConnectionFactory(String authConf, String securityDomain, BasicManagedConnectionFactory mcf) throws ResourceException {

        NoTxConnectionManagerImpl cm = new NoTxConnectionManagerImpl();

        if(authConf != null && securityDomain != null) {
            cm.setSecurityDomain(securityDomain);
            cm.setSubjectFactory(new EmbeddedSecuritySubjectFactory(authConf));
        }

        PoolBySubject pool = new PoolBySubject(mcf, new PoolConfiguration(), false);
        pool.setConnectionListenerFactory(cm);
        cm.setPool(pool);

        return mcf.createConnectionFactory(cm);

    }

}
