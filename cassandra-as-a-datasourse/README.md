| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|Cassandra |Beginner |Teiid, Dynamic VDB, Cassandra Translator |Cassandra Server installed, keyspace and table created |Demonstrates using the Cassandra Translator to access table in Cassandra. |

## What's this

This example demonstrates using the Cassandra Translator to access table in Cassandra. Dynamic VDB [cassandra-vdb.xml](src/main/resources/cassandra-vdb.xml) be used to define view within DDL metadata.

The examples use `java:/demoCassandra` referenced to Cassandra data source, which will be setup automatically while the examples start running.

## Prerequisites

There are 3 prerequisites which is necessary before run example.

**1.** Install Cassandra Server

Download one of stable version from [cassandra dist page](http://archive.apache.org/dist/cassandra/), Installing via unzip, for example

~~~
tar -xvf apache-cassandra-2.0.10-bin.tar.gz
~~~

**2.** Create keyspace and table 

Create 'demo' keyspace and 'users' table as Cassandra [wiki page](http://wiki.apache.org/cassandra/GettingStarted) 

**3.** Edit cassandra.properties

Edit [cassandra.properties](src/main/resources/cassandra.properties) make sure cassandra.address and cassandra.keybase point to cassandra server which setuped above.

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
    <artifactId>translator-cassandra</artifactId>
    <version>${version.teiid}</version>
</dependency>		
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>connector-cassandra</artifactId>
    <version>${version.teiid}</version>
</dependency>
~~~

To add thirdpart dependencies

~~~
<dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty</artifactId>
    <version>${version.io.netty}</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>${version.slf4j-api}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedCassandraDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/cassandra-as-a-datasourse
$ mvn clean install
$ mvn exec:java
~~~

