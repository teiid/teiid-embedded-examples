| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|H2, File |Intermediate |Teiid, VDB, Data Federation, JDBC Translator, File Translator, TEXTTABLE, JDBC Extensions |None |Demonstrates how to implement JDBC Extenstions in JDBC Operations|

## What's this

This example demonstrates a series of JDBC Extensions in Teiid:

* **Non-blocking Statement Execution** - blocking Statement Execution use a Callback execute JDBC asychronously when usind embedded/local connections, more details refer to https://teiid.gitbooks.io/documents/content/client-dev/Non-blocking_Statement_Execution.html[Teiid doc].


> NOTE: VDB [portfolio-vdb.xml](src/main/resources/portfolio-vdb.xml) be used in example. `java:/accounts-ds` and `java:/marketdata-file` referenced with h2 database([customer-schema.sql](src/main/resources/data/customer-schema.sql)) and file data source([marketdata-price.txt](src/main/resources/data/marketdata-price.txt)) correspondingly, which both data source will be setup automatically while the examples running.

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

Import source code to a IDE(Eclipse), run TeiidEmbeddedPortfolioJDBCExtensions as Java Application.

* Run from mvn 

~~~
$ cd teiid-embedded-examples/embedded-portfolio-jdbc-extensions
$ mvn package exec:java
~~~

