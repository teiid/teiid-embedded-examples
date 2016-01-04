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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.security.auth.Subject;

import org.jboss.security.AuthenticationManager;
import org.jboss.security.SecurityConstants;
import org.jboss.security.SubjectFactory;
import org.picketbox.config.PicketBoxConfiguration;
import org.picketbox.factories.SecurityFactory;

public class EmbeddedSecuritySubjectFactory implements SubjectFactory {
    
    private String authConf;
    
    public EmbeddedSecuritySubjectFactory(String authConf) {
        this.authConf = authConf ;
    }

    @Override
    public Subject createSubject() {
        return createSubject(SecurityConstants.DEFAULT_APPLICATION_POLICY);
    }

    @Override
    public Subject createSubject(String securityDomain) {
        
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(authConf);
        
        if(null == in) {
            try {
                in = new FileInputStream(authConf);
            } catch (FileNotFoundException e) {
            }
        }
        
        if(null == in) {
            throw new IllegalArgumentException("invalid authConf: " + authConf);
        }
        
        SecurityFactory.prepare();  
        
        try {
            PicketBoxConfiguration config = new PicketBoxConfiguration();
            config.load(in);
            
            AuthenticationManager authManager = SecurityFactory.getAuthenticationManager(securityDomain);  
            
            if (authManager != null){
                Subject subject = new Subject();
                authManager.isValid(null, null, subject);
                return subject;
            } else {
                throw new IllegalArgumentException("Invalid Security settings in " + authConf);
            }
        } finally {
            SecurityFactory.release();
        }

    }

    public void setAuthConf(String authConf) {
        this.authConf = authConf;
    }

}
