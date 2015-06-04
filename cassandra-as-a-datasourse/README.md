---
Name: cassandra-as-a-datasourse 
Level: Beginner
Technologies: Teiid, Dynamic VDB, Cassandra Translator
Prerequisites: Cassandra Server installed, keyspace and table created
Description: Demonstrates using the Cassandra Translator to access table in Cassandra. 
---

## What's this

This example demonstrates using the Cassandra Translator to access table in Cassandra.  Dynamic VDB [cassandra-vdb.xml](src/kits/embedded/cassandra-as-a-datasourse/cassandra-vdb.xml) be used to define view within DDL metadata.

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

Edit [cassandra.properties](src/kits/embedded/cassandra-as-a-datasourse/cassandra.properties) make sure cassandra.address and cassandra.keybase point to cassandra server which setuped above.


## Run

Refer to [../README.md] run section to run the example.
