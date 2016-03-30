| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|HBase |Beginner |Teiid, Dynamic VDB, Foreign Table, HBase Translator |HBase Server, Phoenix Server |Demonstrates using the HBase Translator with Phoenix Data Source to access data in HBase |

## What's this

This example demonstrates using the HBase Translator with Phoenix Data Source to access data in HBase. Dynamic VDB [hbase-vdb.xml](src/main/resources/hbase-vdb.xml) be used to define Foreign Table within DDL metadata.

The examples use `java:/hbaseDS` referenced with Phoenix data source, which will be setup automatically while the examples start running.

## Prerequisites

There are 3 prerequisites which is necessary before run example.

**1.** Install Setup HBase

Using [HBase quickstart steps](http://hbase.apache.org/book.html#quickstart) to install a single-node, standalone instance of HBase, for example

~~~
$ tar -xvf hbase-0.98.8-hadoop2-bin.tar.gz
$ cd hbase-0.98.8-hadoop2/
~~~

**2.** Install Phoenix to HBase 

Download Phoenix 4.x from [Phoenix Downloads Page](http://phoenix.apache.org/download.html), install Phoenix via copying phoenix-core.jar to HBase lib directory, for example

~~~
$ tar -xvf phoenix-4.2.1-bin.tar.gz
$ cp phoenix-4.2.1-bin/phoenix-core-4.2.1.jar hbase-0.98.8-hadoop2/lib/
~~~

**3.** Start HBase 

Start HBase via start script, for example

~~~
$ cd hbase-0.98.8-hadoop2/
$ ./bin/start-hbase.sh
~~~

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
    <artifactId>translator-hbase</artifactId>
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

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedHBaseDataSource as Java Application.

> NOTE: This example don't support run via mvn exec commands, you need add Phoenix Client phoenix-[version]-client.jar to classpath.
