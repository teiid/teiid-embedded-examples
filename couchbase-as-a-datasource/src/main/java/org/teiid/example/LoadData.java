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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.json.JsonValue;

/**
 * Used to load test data to Couchbase Server
 * @author kylin
 *
 */
public class LoadData {
    
    public static void load(String connectionString, String bucketName) {
        Cluster cluster = CouchbaseCluster.create(connectionString); 
        Bucket bucket = cluster.openBucket(bucketName);
        
//        bucket.upsert(JsonDocument.create("customer", formCustomer()));
//        bucket.upsert(JsonDocument.create("order", formOder()));
//        bucket.upsert(JsonDocument.create("nullValueJson", formNullValueJson()));
//        bucket.upsert(JsonDocument.create("dataTypeJson", formDataTypeJson()));
//        bucket.upsert(JsonDocument.create("nestedJson", nestedJson()));
        bucket.upsert(JsonDocument.create("nestedArray", nestedArray()));
//        bucket.upsert(JsonDocument.create("complexJson", complexJson()));

        bucket.bucketManager().createN1qlPrimaryIndex(true, false);
        
        bucket.close();
        cluster.disconnect();
    }

    public static void main(String[] args) throws IOException {
        load("10.66.192.120", "default");
    }
    
    static JsonObject formCustomer() {
        return JsonObject.create()
                .put("Name", "John Doe")
                .put("ID", "Customer_101")
                .put("type", "Customer")
                .put("SavedAddresses", JsonArray.from("123 Main St.", "456 1st Ave"));
    }
    
    static JsonObject formOder() {
        return JsonObject.create()
                .put("Name", "Air Ticket")
                .put("type", "Oder")
                .put("CustomerID", "Customer_101")
                .put("CreditCard", JsonObject.create().put("Type", "Visa").put("CardNumber", "4111 1111 1111 111").put("Expiry", "12/12").put("CVN", 123))
                .put("Items", JsonArray.from(JsonObject.create().put("ItemID", 89123).put("Quantity", 1), JsonObject.create().put("ItemID", 92312).put("Quantity", 5)));
    }
    
    static JsonObject formNullValueJson() {
        return JsonObject.create()
                .put("Name", "null value test")
                .putNull("attr_null")
                .put("attr_obj", JsonObject.create().putNull("attr_null"))
                .put("attr_array", JsonArray.create().addNull());
    }

    static JsonObject formDataTypeJson() {
        return JsonObject.create()
                .put("Name", "data type test")
                .put("attr_string", "This is String value")
                .put("attr_integer", Integer.MAX_VALUE)
                .put("attr_long", Long.MAX_VALUE)
                .put("attr_double", Double.MAX_VALUE)
                .put("attr_boolean", Boolean.TRUE)
                .put("attr_bigInteger", new BigInteger("fffffffffffff", 16))
                .put("attr_bigDecimal", new BigDecimal("1115.37"))
                .put("attr_jsonObject", JsonObject.create().put("key", "value"))
                .put("attr_jsonArray", formDataTypeArray())
                .putNull("attr_null");
    }
    
    static JsonArray formDataTypeArray() {
        return JsonArray.create()
                .add("This is String value")
                .add(Integer.MAX_VALUE)
                .add(Long.MAX_VALUE)
                .add(Double.MAX_VALUE)
                .add(Boolean.TRUE)
                .add(new BigInteger("fffffffffffff", 16))
                .add(new BigDecimal("1115.37"))
                .add(JsonObject.create().put("key", "value"))
                .add(JsonArray.create().add("array"))
                .addNull();
                
    }
    
    
    static JsonObject nestedJson() {
        return JsonObject.create()
                .put("Name", "Nested Json")
                .put("nestedJson", JsonObject.create()
                        .put("Dimension", 1)
                        .put("nestedJson", JsonObject.create()
                                .put("Dimension", 2)
                                .put("nestedJson", JsonObject.create()
                                        .put("Dimension", 3)
                                        .put("nestedJson", "value"))));
    }
    
    static JsonObject nestedArray() {
        return JsonObject.create()
                .put("Name", "Nested Array")
                .put("nestedArray", JsonArray.create()
                        .add("dimension 1")
                        .add(JsonArray.create()
                                .add("dimension 2")
                                .add(JsonArray.create()
                                        .add("dimension 3")
                                        .add(JsonArray.create()
                                                .add("dimension 4")))));
    }
    
    static JsonObject complexJson() {
        return JsonObject.create()
                .put("Name", "Complex Json")
                .put("attr_jsonObject", JsonObject.create()
                        .put("Name", "Nested Json")
                        .put("attr_jsonArray", JsonArray.create()
                                .add("Nested array")
                                .add(JsonObject.create().put("Name", "Nested Json"))))
                .put("attr_jsonArray", JsonArray.create()
                        .add("Nested array")
                        .add(JsonObject.create().put("Name", "Nested Json"))
                        .add(JsonObject.create().put("Name", "Nested Json").put("Dimension", 1)));
    }
    
    static JsonValue complexJsonNestedArray() {
        return JsonObject.create()
                .put("Name", "Complex Json")
                .put("attr_jsonObject", JsonObject.create()
                        .put("Name", "Nested Json")
                        .put("attr_jsonArray", JsonArray.create()
                                .add("Nested array")
                                .add(JsonObject.create().put("Name", "Nested Json"))));
    }
    

}
