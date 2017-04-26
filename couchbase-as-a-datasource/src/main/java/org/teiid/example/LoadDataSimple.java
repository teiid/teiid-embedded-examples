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
