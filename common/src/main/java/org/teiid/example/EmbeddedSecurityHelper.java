package org.teiid.example;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import org.teiid.security.Credentials;
import org.teiid.security.GSSResult;
import org.teiid.security.SecurityHelper;

public class EmbeddedSecurityHelper implements SecurityHelper {

    @Override
    public Object associateSecurityContext(Object context) {
        System.out.println("associateSecurityContext");
        return new Object();
    }

    @Override
    public void clearSecurityContext() {
        System.out.println("clearSecurityContext");
    }

    @Override
    public Object getSecurityContext() {
        System.out.println("getSecurityContext");
        return new Object();
    }

    @Override
    public Subject getSubjectInContext(String securityDomain) {
        System.out.println("getSubjectInContext");
        return null;
    }

    @Override
    public Object authenticate(String securityDomain, String baseUserName, Credentials credentials, String applicationName) throws LoginException {
        System.out.println("authenticate");
        return new Object();
    }

    @Override
    public GSSResult negotiateGssLogin(String securityDomain, byte[] serviceTicket) throws LoginException {
        System.out.println("negotiateGssLogin");
        return null;
    }

}
