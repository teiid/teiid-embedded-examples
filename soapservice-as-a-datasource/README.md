---
Name: soapservice-as-a-datasource 
Level: Beginner
Technologies: Teiid, Dynamic VDB, VIRTUAL PROCEDURE, XMLTABLE, SOAP Service, WS Translator 
Prerequisites: 'StateService' service
Description: Demonstrates using the WS Translator to call a generic soap service
---

## What's this

This example demonstrates using the WS Translator to call a generic soap service. Dynamic VDB [webservice-vdb.xml](src/main/resources/webservice-vdb.xml) be used to define Procedure within DDL metadata.

The examples use `java:/StateServiceWebSvcSource` referenced with generic soap service as a data source, which data source will be setup automatically while the examples start running.

## Prerequisites

Deploy StateService service `StateService.jar` to a running JBoss server(Assume JBoss EAP 6 run on localhost).

[../app/stateService/README.md](../app/stateService/README.md) have steps to build and deploy StateService service.

## Run

Refer to [../README.md](../README.md) run section to run the example.
