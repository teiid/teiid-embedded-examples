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

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class LoadDataSimple {
    
    public static void load(String connectionString, String bucketName) {
        
        Cluster cluster = CouchbaseCluster.create(connectionString); 
        Bucket bucket = cluster.openBucket(bucketName);
        
        for(int i = 1; i < 3 ; i ++) {
            bucket.upsert(JsonDocument.create("customer-" + i, formCustomer()));
            bucket.upsert(JsonDocument.create("order-" + i, formOder()));
        }

        bucket.bucketManager().createN1qlPrimaryIndex(true, false);
        
        bucket.close();
        cluster.disconnect();
    }

    public static void main(String[] args) {
        load("10.66.192.120", "test");
    }
    
    static JsonObject formCustomer() {
        return JsonObject.create()
                .put("Name", "John Doe")
                .put("ID", "Customer_12345")
                .put("type", "Customer")
                .put("SavedAddresses", JsonArray.from("123 Main St.", "456 1st Ave"));
    }
    
    static JsonObject formOder() {
        return JsonObject.create()
                .put("Name", "Air Ticket")
                .put("type", "Oder")
                .put("CustomerID", "Customer_12345")
                .put("CreditCard", JsonObject.create().put("Type", "Visa").put("CardNumber", "4111 1111 1111 111").put("Expiry", "12/12").put("CVN", 123))
                .put("Items", JsonArray.from(JsonObject.create().put("ItemID", 89123).put("Quantity", 1), JsonObject.create().put("ItemID", 92312).put("Quantity", 5)));
    }

}
