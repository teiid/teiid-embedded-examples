---
Name: embedded-caching 
Level: Intermediate
Technologies: Teiid, Dynamic VDB, Native Queries, JDBC Translator, Results Caching, Materialized Views, Translator Results Caching 
Prerequisites: None
Description: Demonstrates how Results Caching, Materialized Views works in Teiid
---

## What's this

This example have 4 sub-example:

* Teiid Results Caching Example - a comparison example(native query, query without cache, query with cache) show how Results Caching improve thousands of performance
* Teiid External Materialization Example - a example show how External Materialization works
* Teiid Internal Materialization Example - a example show how Internal Materialization works
* Teiid Translator Results Caching Example - a example show how Translators can contribute cache entries into the result set cache via the use of the CacheDirective object

For Results Caching Example, there is a `PERFTEST` table have 100MB date exist in H2 database, there are 3 categories' query, each execute 10 times, the comparison results will show Results Caching improve thousands of performance. Dynamic VDB [rsCaching-h2-vdb.xml](src/main/resources/rsCaching-h2-vdb.xml) be used to define view within DDL metadata.

Materialization Examples show how External/Internal Materialization works, [mat-h2-vdb.xml](src/main/resources/mat-h2-vdb.xml), [mat-inter-h2-vdb.xml](src/main/resources/mat-inter-h2-vdb.xml) are dynamic VDB used to define Materialization views. 

## Prerequisites

None

## Run

Refer to [../README.md](../README.md) run section to run the example.
