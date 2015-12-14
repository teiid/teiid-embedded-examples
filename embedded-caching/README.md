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

## Dependencies

To add Teiid runtime, admin

~~~
<dependency>
    <groupId>org.jboss.teiid</groupId>
    <artifactId>teiid-runtime</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.teiid</groupId>
    <artifactId>teiid-admin</artifactId>
    <version>${version.teiid}</version>
</dependency>
~~~

To add Translators and Resource Adapters

~~~
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>translator-jdbc</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.narayana.jta</groupId>
    <artifactId>narayana-jta</artifactId>
    <version>${version.narayana}</version>
</dependency>
<dependency>
    <groupId>org.jboss.ironjacamar</groupId>
    <artifactId>ironjacamar-jdbc</artifactId>
    <version>${version.ironjacamar}</version>
</dependency>
<dependency>
    <groupId>org.jboss.ironjacamar</groupId>
    <artifactId>ironjacamar-core-api</artifactId>
    <version>${version.ironjacamar}</version>
</dependency>
<dependency>
    <groupId>org.jboss.ironjacamar</groupId>
    <artifactId>ironjacamar-core-impl</artifactId>
    <version>${version.ironjacamar}</version>
</dependency>
~~~

To add thirdpart dependency

~~~
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>${version.com.h2database}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedCaching as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/embedded-caching
$ mvn clean install
$ mvn exec:java
~~~

