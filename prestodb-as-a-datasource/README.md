| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|PrestoDB |Beginner |Teiid, VDB, View, prestodb Translator |PrestoDB Server, PrestoDB JDBC Driver |Demonstrates using the prestodb Translator to access data in prestodb Server |

## What's this

This example demonstrates using the prestodb Translator to access data in prestodb Server. VDB [presto-vdb.xml](src/main/resources/presto-vdb.xml) be used to define View map to Vertica table within a DDL metadata.

The examples use `java:/prestoDS` referenced with prestodb data source, which will be setup automatically while the examples start running.

## Prerequisites

1. Refer to [Deploying Presto](https://prestodb.io/docs/current/installation/deployment.html) to download and set up Presto server.
2. Refer to [Connectors](https://prestodb.io/docs/current/connector.html) to configure catalog.
3. Start Presto server

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
    <artifactId>translator-jdbc</artifactId>
    <version>${version.teiid}</version>
</dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>translator-prestodb</artifactId>
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

Tp add Presto JDBC Driver

~~~
<dependency>
    <groupId>com.facebook.presto</groupId>
    <artifactId>presto-jdbc</artifactId>
    <version>${version.presto}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedPrestoDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/prestodb-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~


