| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|SOAP Service |Beginner |Teiid, Dynamic VDB, VIRTUAL PROCEDURE, XMLTABLE, SOAP Service, WS Translator |'StateService' service |Demonstrates using the WS Translator to call a generic soap service |

## What's this

This example demonstrates:

* How to use the WS Translator to call a generic soap service. Dynamic VDB [webservice-vdb.xml](src/main/resources/webservice-vdb.xml) be used to define Procedure within DDL metadata.
* How to invoke WSDL based Procedures.

The examples use `java:/StateServiceWebSvcSource` referenced with generic soap service as a data source, which data source will be setup automatically while the examples start running.

## Prerequisites

Refer to [stateService/README.md](stateService/README.md) to build and start StateService service.

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

To add other dependency

~~~
<dependency>
    <groupId>org.apache.ws.xmlschema</groupId>
    <artifactId>xmlschema-core</artifactId>
    <version>2.0.2</version>
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
