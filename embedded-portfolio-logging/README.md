---
Name: embedded-logging 
Level: Intermediate
Technologies: Teiid, JBoss LogManager, JBoss Logging, Log4j 
Prerequisites: None
Description: Demonstrates how to use JBoss LogManager with Teiid Embedded
---

## What's this

This example demonstrates [logging in Teiid Embedded](https://docs.jboss.org/author/display/TEIID/Logging+in+Teiid+Embedded).

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

To add logging related dependency

~~~
<dependency>
    <groupId>org.jboss.logmanager</groupId>
    <artifactId>jboss-logmanager</artifactId>
    <version>${version.jboss-logmanager}</version>
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

* Run [TeiidEmbeddedLogging](src/main/java/org/teiid/example/TeiidEmbeddedLogging.java) as Java Application will show whether the message is to be recorded or discarded, or where to send any recorded messages.
* Run [TeiidEmbeddedPortfolio](src/main/java/org/teiid/example/TeiidEmbeddedPortfolio.java) as Java Application will show JBoss Logmanager bridging JBoss logging.
