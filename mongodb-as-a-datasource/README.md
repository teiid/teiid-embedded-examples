---
Name: mongodb-as-a-datasource
Level: Beginner
Technologies: Teiid, Dynamic VDB, MongoDB Translator
Prerequisites: MongoDB Server installed, 'mydb' be created
Description: Demonstrates using the MongoDB Translator to access data in mongodb
---

## What's this

This example demonstrates using the MongoDB Translator to access data in mongodb. Dynamic VDB [mongodb-vdb.xml](src/main/resources/mongodb-vdb.xml) be used to define forien table within DDL metadata.

The examples use `java:/mongoDS` referenced to MongoDB data source, which will be setup automatically while the examples start running.

## Prerequisites

There are 3 prerequisites which is necessary before run example.

**1.** Install MongoDB

Refer to [mongoDB manual](http://docs.mongodb.org/manual/) to install MongoDB.


**2.** Create DBName

Refer to [mongoDB manual](http://docs.mongodb.org/manual/) to dbname 'mydb', In this example, we assume the employee document be insert under Employee connection as below:

~~~
db.Employee.insert({employee_id: '1', FirstName: 'Test1', LastName: 'Test1'});
db.Employee.insert({employee_id: '2', FirstName: 'Test2', LastName: 'Test2'});
db.Employee.insert({employee_id: '3', FirstName: 'Test3', LastName: 'Test3'});
~~~

**3.** Edit mongodb.properties

Edit [mongodb.properties](src/main/resources/mongodb.properties) make sure server.list and db.name point to mongo server which setuped above.

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
    <artifactId>translator-mongodb</artifactId>
    <version>${version.teiid}</version>
</dependency>	
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>translator-jdbc</artifactId>
    <version>${version.teiid}</version>
</dependency>
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>connector-mongodb</artifactId>
    <version>${version.teiid}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedMongoDBDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/mongodb-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~
