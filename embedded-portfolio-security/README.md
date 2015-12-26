| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|H2, File |Intermediate |Teiid, Dynamic VDB, Native Queries, Data Federation, JDBC Translator, File Translator, TEXTTABLE |None |Demonstrates security authentication in Teiid Embedded |

## What's this

This example demonstrates how to implement security authentication in Teiid Embedded.

This example demonstrates how to expose multiple data sources(a relational data source, a text file-based data source) for data federation. Dynamic VDB [portfolio-vdb.xml](src/main/resources/portfolio-vdb.xml) be used to define view within DDL metadata.

The examples use `java:/accounts-ds` and `java:/marketdata-file` referenced with h2 database([customer-schema.sql](src/main/resources/data/customer-schema.sql)) and file data source([marketdata-price.txt](src/main/resources/data/marketdata-price.txt)) correspondingly, which both data source will be setup automatically while the examples start running.

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

To add security related dependency

~~~
<dependency>
    <groupId>org.picketbox</groupId>
    <artifactId>picketbox</artifactId>
    <version>${version.picketbox}</version>
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

Import source code to a IDE(Eclipse), run TeiidEmbeddedPortfolioSecurity as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/embedded-portfolio-security
$ mvn clean install
$ mvn exec:java
~~~

