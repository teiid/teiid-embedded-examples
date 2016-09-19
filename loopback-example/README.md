| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|N/A |Beginner |Teiid, VDB, LoopBack Translator |N/A |Demonstrates using the loopback Translator return random matrix data |

## What's this

This example demonstrates using the loopback Translator return random matrix data. VDB [loopback-vdb.xml](src/main/resources/loopback-vdb.xml) be used to define forien table within DDL metadata.

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

To add Translators

~~~
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>translator-loopback</artifactId>
    <version>${version.teiid}</version>
</dependency>		
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run LoopbackExample as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/loopback-example
$ mvn clean install
$ mvn exec:java
~~~
