---
Name: restservice-as-a-datasource 
Level: Beginner
Technologies: Teiid, Dynamic VDB,  Swagger Translator 
Prerequisites: customer service
Description: Demonstrates using the Swagger Translator to call a REST web services and transform the web service results into relational results
---

## What's this

This example demonstrates using the Swagger Translator to call a REST web services and transform the web service results into relational results. Dynamic VDB [swagger-vdb.xml](src/main/resources/swagger-vdb.xml) be used to define view within DDL metadata.

The examples use `java:/CustomerRESTWebSvcSource` referenced with Rest WebServiceas a data source, which data source will be setup automatically while the examples start running.

## Prerequisites

* Refer to [../app/customer-swagger/README.md](../app/customer-swagger/README.md) build and start customer service.
* http://petstore.swagger.io/

## Run

* Run Teiid Embedded SwaggerCustomer Example

This dependent on swagger translator, ws resource adapter and customer-swagger service.

Start customer-swagger service:

~~~
$ cd teiid-embedded-examples/app/customer-swagger/
$ mvn clean install
$ java -jar target/customer-swagger-swarm.jar
~~~

Run [TeiidEmbeddedSwaggerCustomer](src/main/java/org/teiid/example/TeiidEmbeddedSwaggerCustomer.java) as java Application.

* Run Run Teiid Embedded SwaggerPetStore Example

Refer to [../README.md](../README.md) run section to run the example.
