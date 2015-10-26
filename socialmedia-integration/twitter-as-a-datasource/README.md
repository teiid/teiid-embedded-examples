---
Name: hbase-as-a-datasource 
Level: Beginner
Technologies: Teiid, Dynamic VDB, View, WS Translator, OAuth 1
Prerequisites: Create App in Twitter
Description: Demonstrates using the WS Translator with Phoenix Data Source to access data in HBase
---

## What's this

This example demonstrates access Twitter data using Teiid, and convert the data into tabular form. Dynamic VDB [twitter-vdb.xml](src/main/resources/twitter-vdb.xml) be used to define View within DDL metadata.

The examples use `java:/twitterDS` referenced with Web Service data source, which will be setup automatically while the examples start running.

## Prerequisites

There are 3 prerequisites which is necessary before run example.


## Run

Refer to [../../README.md](../../README.md) run section to run the example.

> NOTE: This exampe don't support run via mvn exec commands, you need add Phoenix Client phoenix-[version]-client.jar to classpath.
