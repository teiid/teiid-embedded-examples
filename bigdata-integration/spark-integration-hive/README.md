| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|Spark, Hive |Beginner |Teiid, Dynamic VDB, View, Hive Translator |Spark Thrift JDBC/ODBC server, HiveServer2 |Demonstrates using the Hive Translator with HiveServer2 JDBC Driver to access Spark data |

## What's this

This example demonstrates using the Hive Translator with HiveServer2 JDBC Driver to access the Spark data. Dynamic VDB [spark-vdb.xml](src/main/resources/spark-vdb.xml) be used to define View within DDL metadata.

The examples use `java:/sparkDS` referenced with HiveServer2 data source, which will be setup automatically while the examples start running.

## Prerequisites

Refer to [../README.md](../README.md) **Prerequisites** section to install Spark and start Spark Thrift JDBC/ODBC server.

Edit [hive.properties](src/main/resources/hive.properties) make sure it point to a running HiveServer2.

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
    <artifactId>translator-hive</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>translator-jdbc</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.narayana.jta</groupId>
    <artifactId>narayana-jta</artifactId>
    <version>${version.narayana}</version>
</dependency>
<dependency>
    <groupId>org.jboss.ironjacamar</groupId>
    <artifactId>ironjacamar-jdbc</artifactId>
    <version>${version.ironjacamar}</version>
</dependency>
<dependency>
    <groupId>org.jboss.ironjacamar</groupId>
    <artifactId>ironjacamar-core-api</artifactId>
    <version>${version.ironjacamar}</version>
</dependency>
<dependency>
    <groupId>org.jboss.ironjacamar</groupId>
    <artifactId>ironjacamar-core-impl</artifactId>
    <version>${version.ironjacamar}</version>
</dependency>
~~~

To add thirdpart dependency

~~~
<dependency>
    <groupId>org.spark-project.hive</groupId>
    <artifactId>hive-jdbc</artifactId>
    <version>${version.spark-project.hive}</version>
</dependency>		
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-core</artifactId>
    <version>${version.apache.hadoop}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedSparkDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/bigdata-integration/spark-integration-hive
$ mvn clean install
$ mvn exec:java
~~~
