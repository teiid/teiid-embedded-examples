---
Name: swagger-as-a-datasource 
Level: Beginner
Technologies: Teiid, Dynamic VDB,  Swagger Translator 
Prerequisites: customer service
Description: Demonstrates using the Swagger Translator to call a REST web services and transform the web service results into relational results
---

## What's this

This example demonstrates using the Swagger Translator to call a REST web services and transform the web service results into relational results. Dynamic VDB [swagger-vdb.xml](src/main/resources/swagger-vdb.xml) be used to define view within DDL metadata.

The examples use `java:/CustomerRESTWebSvcSource` referenced with Rest WebServiceas a data source, which data source will be setup automatically while the examples start running.

## Prerequisites

* Refer to [customer-swagger/README.md](customer-swagger/README.md) build and start customer service.
* http://petstore.swagger.io/

## Dependencies

To add Teiid runtime, admin

~~~
<dependency>
    <groupId>org.jboss.teiid</groupId>
    <artifactId>teiid-runtime</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.teiid</groupId>
    <artifactId>teiid-admin</artifactId>
    <version>${version.teiid}</version>
</dependency>
~~~

To add Translators and Resource Adapters

~~~
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>swagger-ws</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>connector-ws</artifactId>
    <version>${version.teiid}</version>
</dependency>
~~~


## Run
* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedSwaggerPetstore as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/restservice-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~

