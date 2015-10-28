---
Name: odataservice-as-a-datasource 
Level: Beginner
Technologies: Teiid, Dynamic VDB, FOREIGN TABLE,  OData Translator 
Prerequisites: N/A
Description: Demonstrates using the WebService Connector connecting to an OData source and OData Translator to transform OData source to Teiid Foreign Table
---

## What's this

Demonstrates using the WebService Connector connecting to an OData source(http://services.odata.org/Northwind/Northwind.svc/) and using OData Translator to transform OData source to Teiid Foreign Table. Dynamic VDB [odataNorthwindservice-vdb.xml](src/main/resources/odataNorthwindservice-vdb.xml) be used to define Foreign Table to transform OData source.

The examples use `java:/ODataNorthwindDS` referenced with OData source(http://services.odata.org/Northwind/Northwind.svc/) as a  data source, which will be setup automatically while the examples start running.

## Prerequisites

N/A

## Run

Refer to [../README.md](../README.md) run section to run the example.
