---
Name: soapservice-as-a-datasource 
Level: Beginner
Technologies: Teiid, Dynamic VDB, VIRTUAL PROCEDURE, XMLTABLE, SOAP Service, WS Translator 
Prerequisites: 'StateService' service
Description: Demonstrates using the WS Translator to call a generic soap service
---

## What's this

This example demonstrates:

* How to use the WS Translator to call a generic soap service. Dynamic VDB [webservice-vdb.xml](src/main/resources/webservice-vdb.xml) be used to define Procedure within DDL metadata.
* How to invoke WSDL based Procedures.

The examples use `java:/StateServiceWebSvcSource` referenced with generic soap service as a data source, which data source will be setup automatically while the examples start running.

## Prerequisites

Deploy StateService service `StateService.jar` to a running JBoss server(Assume JBoss EAP 6 run on localhost).

[stateService/README.md](stateService/README.md) have steps to build and deploy StateService service.

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
    <artifactId>translator-ws</artifactId>
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

Import source code to a IDE(Eclipse), run GenericSoapMain as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/soapservice-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~
