| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|Mongodb |Beginner |Teiid, VDB, Couchbase Translator |Couchbase Server installed, |Demonstrates using the Couchbase Translator to access data in Couchbase Server |

## What's this

This example demonstrates using the Couchbase Translator to access data in Couchbase Server. VDB [couchbase-vdb.xml](src/main/resources/couchbase-vdb.xml) be used to define foreign table within DDL metadata.

The examples use `java:/couchbaseDS` referenced to Couchbase data source, which will be setup automatically while the examples start running.

## Prerequisites

There are 3 prerequisites which is necessary before run example.

**1.** Install Couchbase

Refer to [Couchbase manual](https://developer.couchbase.com/documentation/server/4.6/getting-started/start-here.html) to install Couchbase.

**2.** Edit mongodb.properties

Edit [couchbase.properties](src/main/resources/couchbase.properties) make sure properties point to couchbase server which setuped above.

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
    <artifactId>translator-couchbase</artifactId>
    <version>${version.teiid}</version>
</dependency>	
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>translator-jdbc</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>connector-couchbase</artifactId>
    <version>${version.teiid}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedCouchbaseDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/couchbase-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~
