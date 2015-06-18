---
Name: restservice-as-a-datasource 
Level: Beginner
Technologies: Teiid, Dynamic VDB, XMLTABLE, XMLPARSE,  WS Translator 
Prerequisites: 'customer' service
Description: Demonstrates using the WS Translator to call a REST web services and transform the web service results into relational results
---

## What's this

This example demonstrates using the WS Translator to call a REST web services and transform the web service results into relational results. Dynamic VDB [restwebservice-vdb.xml](src/main/resources/restwebservice-vdb.xml) be used to define view within DDL metadata.

The examples use `java:/CustomerRESTWebSvcSource` referenced with Rest WebServiceas a data source, which data source will be setup automatically while the examples start running.

## Prerequisites

Refer to [../app/customer/README.md](../app/customer/README.md) build and start customer service.

## Run

Refer to [../README.md](../README.md) run section to run the example.
