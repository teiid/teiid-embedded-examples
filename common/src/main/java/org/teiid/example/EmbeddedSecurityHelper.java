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

import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import org.jboss.security.AuthenticationManager;
import org.jboss.security.SecurityContext;
import org.jboss.security.SecurityContextAssociation;
import org.jboss.security.SecurityContextFactory;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.SubjectInfo;
import org.picketbox.config.PicketBoxConfiguration;
import org.picketbox.factories.SecurityFactory;
import org.teiid.security.Credentials;
import org.teiid.security.GSSResult;
import org.teiid.security.SecurityHelper;

public class EmbeddedSecurityHelper implements SecurityHelper {

    @Override
    public SecurityContext associateSecurityContext(Object newContext) {
    	
    	SecurityContext context = getSecurityContext();
    	
    	if (newContext != context){
    		final SecurityContext sc = (SecurityContext) newContext;
    		AccessController.doPrivileged(new PrivilegedAction<SecurityContext>(){
				@Override
				public SecurityContext run() {
					SecurityContextAssociation.setSecurityContext(sc);
					return null;
				}});
    	}
    	
        return context;
    }

    @Override
    public void clearSecurityContext() {
    	
    	AccessController.doPrivileged(new PrivilegedAction<SecurityContext>(){
			@Override
			public SecurityContext run() {
				SecurityContextAssociation.clearSecurityContext();
				return null;
			}});
    }

    @Override
    public SecurityContext getSecurityContext() {
    	
        return AccessController.doPrivileged(new PrivilegedAction<SecurityContext>(){
			@Override
			public SecurityContext run() {
				return SecurityContextAssociation.getSecurityContext(); 
			}});
    }

    @Override
    public Subject getSubjectInContext(String securityDomain) {
        
    	SecurityContext sc = getSecurityContext();
    	if (sc != null && sc.getSecurityDomain().equals(securityDomain)) {
			return getSubjectInContext(sc);
		}
        return null;
    }
    
    @Override
	public Subject getSubjectInContext(Object context) {
    	if (!(context instanceof SecurityContext)) {
			return null;
		}
		SecurityContext sc = (SecurityContext)context;
		SubjectInfo si = sc.getSubjectInfo();
		Subject subject = si.getAuthenticatedSubject();
		return subject;
	}

    @Override
    public SecurityContext authenticate(String securityDomain, String baseUserName, Credentials credentials, String applicationName) throws LoginException {
        
    	SecurityFactory.prepare();  
    	
    	try {
			PicketBoxConfiguration config = new PicketBoxConfiguration();
			config.load(this.getClass().getClassLoader().getResourceAsStream("picketbox/authentication.conf"));
			
			AuthenticationManager authManager = SecurityFactory.getAuthenticationManager(securityDomain);  
			
			if (authManager != null){
				final Principal userPrincipal = new SimplePrincipal(baseUserName);
			    final Subject subject = new Subject();
			    final String credString = credentials==null?null:new String(credentials.getCredentialsAsCharArray());
			    final String domain = securityDomain;
			    boolean isValid = authManager.isValid(userPrincipal, credString, subject);
			    if (isValid) {
			    	SecurityContext securityContext = AccessController.doPrivileged(new PrivilegedAction<SecurityContext>(){
						@Override
						public SecurityContext run() {
							SecurityContext sc;
							try {
								sc = SecurityContextFactory.createSecurityContext(userPrincipal, credString, subject, domain);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
							return sc;
						}});
			    	return securityContext;
			    }
			}
		} finally {
			SecurityFactory.release();
		}

        throw new LoginException("The username " +  baseUserName + " and/or password could not be authenticated by security domain " + securityDomain + ".");
    } 

    @Override
    public GSSResult negotiateGssLogin(String securityDomain, byte[] serviceTicket) throws LoginException {
        return null;
    }

	

}
