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
