| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|Excel |excel-as-a-datasource |Teiid, Dynamic VDB, Foreign Table, Excel Translator |None |Demonstrates using Excel Translator query Excel file with JDBC |

## What's this

This example demonstrates using Excel Translator query Excel file with JDBC. Dynamic VDB [excel-vdb.xml](src/main/resources/excel-vdb.xml) be used to define view and Foreign Table within DDL metadata.

The examples use `java:/excel-file` referenced with a [excel data source](src/main/resources/data/otherholdings.xls), which data source will be setup automatically while the examples start running.

## Prerequisites

None

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
    <artifactId>translator-excel</artifactId>
    <version>${version.teiid}</version>
</dependency>		
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>connector-file</artifactId>
    <version>${version.teiid}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedExcelDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/excel-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~
