---
Name: odataservice-as-a-datasource 
Level: Beginner
Technologies: Teiid, Dynamic VDB, FOREIGN TABLE,  OData4 Translator 
Prerequisites: N/A
Description: Demonstrates using the WebService Connector connecting to an OData4 source and OData4 Translator to transform OData source to Teiid Foreign Table
---

## What's this

Demonstrates using the WebService Connector connecting to an OData source(http://services.odata.org/Northwind/Northwind.svc/) and using OData Translator to transform OData source to Teiid Foreign Table. Dynamic VDB [odataNorthwindservice-vdb.xml](src/main/resources/odataNorthwindservice-vdb.xml) be used to define Foreign Table to transform OData source.

The examples use `java:/ODataNorthwindDS` referenced with OData source(http://services.odata.org/Northwind/Northwind.svc/) as a  data source, which will be setup automatically while the examples start running.

## Prerequisites

N/A

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
    <artifactId>translator-odata4</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>translator-jdbc</artifactId>
    <version>${version.teiid}</version>
</dependency>		
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

Import source code to a IDE(Eclipse), run TeiidEmbeddedOData4ServiceDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/odata4service-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~
