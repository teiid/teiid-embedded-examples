| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|H2, File |Intermediate |Teiid, Dynamic VDB, Native Queries, Data Federation, JDBC Translator, File Translator, TEXTTABLE |None |Demonstrates how to expose multiple data sources for data federation |

## What's this

This example is the same as "embedded-portfolio" example, except this demonstrates exposing teiid embedded
using sockets, so that a client application running in another VM can connect.
  
This example will start teiid embedded as the "Server" and then start a "Client" thread that will connect to 
the server and issue the same queries issued in the "embedded-portfolio" example.


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
    <artifactId>translator-file</artifactId>
    <version>${version.teiid}</version>
</dependency>	
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>connector-file</artifactId>
    <version>${version.teiid}</version>
</dependency>		

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

Import source code to a IDE(Eclipse), run TeiidEmbeddedPortfolio as Java Application.

* Run from mvn 

~~~
$ cd teiid-embedded-examples/embedded-portfolio-sockets
$ mvn clean install
$ mvn exec:java
~~~

