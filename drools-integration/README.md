---
Name: drools-integration
Level: Beginner
Technologies: Teiid, Dynamic VDB, User Defined Function, VIRTUAL FUNCTION, Drools API
Prerequisites: N/A
Description: Demonstrates using Teiid User Defined Function trigger and fire business rules
---

## What's this

This example demonstrates using a Teiid JDBC Client fire business rules via Teiid [User Defined Function](https://docs.jboss.org/author/display/TEIID/Support+for+User-Defined+Functions+(Non-Pushdown)).

[HelloWorld.drl](src/main/resources/org/drools/examples/helloworld/HelloWorld.drl) is the business file used in the example.

Dynamic VDB [drools-vdb.xml](src/main/resources/drools-vdb.xml) used to define VIRTUAL FUNCTION within a `DroolsModel`.


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

To add drools dependencies

~~~
<dependency>
    <groupId>org.kie</groupId>
    <artifactId>kie-api</artifactId>
    <version>${version.drools}</version>
</dependency>
<dependency>
    <groupId>org.drools</groupId>
    <artifactId>drools-compiler</artifactId>
    <version>${version.drools}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedDroolsIntegration as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/drools-integration
$ mvn clean install
$ mvn exec:java
~~~
