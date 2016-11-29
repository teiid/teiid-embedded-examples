| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|Weibo |Beginner |Teiid, Dynamic VDB, View, WS Translator, OAuth 1, JAAS |Create App in Twitter |Demonstrates using the WS Translator to interact with Twitter API |

## What's this

This example demonstrates 

* How to access Weibo data using Teiid, and convert the data into tabular form. Dynamic VDB [weibo-vdb.xml](src/main/resources/weibo-vdb.xml) be used to define View within DDL metadata.
* How to set security domain to resource adapter in embedded.


The examples use `java:/weiboDS` referenced with Web Service data source, which will be setup automatically while the examples start running.

## Prerequisites
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
    <artifactId>translator-ws</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>connector-ws</artifactId>
    <version>${version.teiid}</version> 
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedWeiboDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/socialmedia-integration/weibo-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~
