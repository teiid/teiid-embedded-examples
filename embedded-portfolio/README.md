---
Name: embedded-portfolio 
Level: Intermediate
Technologies: Teiid, Dynamic VDB, Native Queries, Data Federation, JDBC Translator, File Translator, TEXTTABLE 
Prerequisites: None
Description: Demonstrates how to expose multiple data sources for data federation
---

## What's this

This example demonstrates how to expose multiple data sources(a relational data source, a text file-based data source) for data federation. Dynamic VDB [portfolio-vdb.xml](src/main/resources/portfolio-vdb.xml) be used to define view within DDL metadata.

The examples use `java:/accounts-ds` and `java:/marketdata-file` referenced with h2 database([customer-schema.sql](src/main/resources/data/customer-schema.sql)) and file data source([marketdata-price.txt](src/main/resources/data/marketdata-price.txt)) correspondingly, which both data source will be setup automatically while the examples start running.

## Prerequisites

None

## Run

Refer to [../README.md](../README.md) run section to run the example.
