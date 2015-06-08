---
Name: hbase-as-a-datasource 
Level: Beginner
Technologies: Teiid, Dynamic VDB, Forrign Table, HBase Translator
Prerequisites: HBase Server, Phoenix Server
Description: Demonstrates using the HBase Translator with Phoenix Data Source to access data in HBase
---

## What's this

This example demonstrates using the HBase Translator with Phoenix Data Source to access data in HBase. Dynamic VDB [hbase-vdb.xml](src/main/resources/hbase-vdb.xml) be used to define Forrign Table within DDL metadata.

The examples use `java:/hbaseDS` referenced with Phoenix data source, which will be setup automatically while the examples start running.

## Prerequisites

There are 4 prerequisites which is necessary before run example.

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

**4.** Install Phoenix Client

Either Copy phoenix-[version]-client.jar to .../optional/hbase, or add phoenix-[version]-client.jar to classpath, for example

~~~
$ cp phoenix-4.2.1-bin/phoenix-4.2.1-client.jar .../optional/hbase
~~~

## Run

Refer to [../README.md] run section to run the example.
