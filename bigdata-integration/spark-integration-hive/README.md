---
Name: spark-integration-hive 
Level: Beginner
Technologies: Teiid, Dynamic VDB, View, Hive Translator
Prerequisites: Spark Thrift JDBC/ODBC server, HiveServer2
Description: Demonstrates using the Hive Translator with HiveServer2 JDBC Driver to access Spark data
---

## What's this

This example demonstrates using the Hive Translator with HiveServer2 JDBC Driver to access the Spark data. Dynamic VDB [spark-vdb.xml](src/main/resources/spark-vdb.xml) be used to define View within DDL metadata.

The examples use `java:/sparkDS` referenced with HiveServer2 data source, which will be setup automatically while the examples start running.

## Prerequisites

Refer to [../README.md](../README.md) **Prerequisites** section to install Spark and start Spark Thrift JDBC/ODBC server.

Edit [hive.properties](src/main/resources/hive.properties) make sure it point to a running HiveServer2.

## Run

Refer to [../../README.md](../../README.md) **Run** section to run the example.
