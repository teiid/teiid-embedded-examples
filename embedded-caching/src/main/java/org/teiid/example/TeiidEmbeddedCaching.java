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

import static org.teiid.example.H2PERFTESTClient.MB;

import java.util.logging.Level;

@SuppressWarnings("nls")
public class TeiidEmbeddedCaching {
    
    public static void main(String[] args) throws Exception {
        
        EmbeddedHelper.enableLogger(Level.OFF);
        
        H2PERFTESTClient.insert(MB);
        
        println("Teiid Results Caching Example - a comparison example(native query, query without cache, query with cache) show how Results Caching improve thousands of performance");
        
        ResultsCachingExample.main(args);
        
        println("Teiid External Materialization Example - a example show how External Materialization works");
        
        ExternalMaterializationExample.main(args);
        
        println("Teiid Internal Materialization Example - a example show how Internal Materialization works");
        
        InternalMaterializationExample.main(args);
        
        println("Teiid Translator Results Caching Example - a example show how Translators can contribute cache entries into the result set cache via the use of the CacheDirective object");
   
        TranslatorResultsCachingExample.main(args);
    }
    
    static void println(String msg) {
        System.out.println(msg);
    }
    
    static void prompt(String msg){
        System.out.println("\n\t" + msg + "\n");
    }
    
    @SuppressWarnings("static-access")
    static void sleep(String msg, long time){
        prompt(msg);
        try {
            Thread.currentThread().sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
	
}
