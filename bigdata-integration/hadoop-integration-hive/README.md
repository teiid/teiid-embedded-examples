---
Name: hadoop-integration-hive 
Level: Beginner
Technologies: Teiid, Dynamic VDB, View, Hive Translator
Prerequisites: Hadoop Services, HiveServer2
Description: Demonstrates using the Hive Translator with HiveServer2 JDBC Driver to access data in Hadoop HDFS
---

## What's this

This example demonstrates using the Hive Translator with HiveServer2 JDBC Driver to access data in Hadoop HDFS. Dynamic VDB [hive-vdb.xml](src/main/resources/hive-vdb.xml) be used to define View within DDL metadata.

The examples use `java:/hiveDS` referenced with HiveServer2 data source, which will be setup automatically while the examples start running.

## Prerequisites

**1.** Hadoop Services

Refer to [../README.md](../README.md) **Prerequisites** section to install Hadoop and start Hadoop Services


**2.** HiveServer2 

Refer to [../README.md](../README.md) **Prerequisites** section to install Hive and start HiveServer2

**3.** Load Sample Data

With above 2 setps use the following commands to load [hive-sample.txt](src/main/resources/hive-sample.txt):

~~~
$ ./bin/hive
hive> CREATE TABLE IF NOT EXISTS employee (eid int, name String, salary String, destination String);
hive> LOAD DATA LOCAL INPATH '/path/to/hive-sample.txt' OVERWRITE INTO TABLE employee;
~~~

**4.** Edit JDBC Parameters

Edit [hive.properties](src/main/resources/hive.properties) make sure it point to a running HiveServer2.

## Run

Refer to [../../README.md](../../README.md) **Run** section to run the example.
