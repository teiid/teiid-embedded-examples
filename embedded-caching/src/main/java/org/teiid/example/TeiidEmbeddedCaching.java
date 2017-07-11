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
