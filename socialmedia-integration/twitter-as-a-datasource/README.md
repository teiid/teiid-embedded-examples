| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|Twitter |Beginner |Teiid, Dynamic VDB, View, WS Translator, OAuth 1, JAAS |Create App in Twitter |Demonstrates using the WS Translator to interact with Twitter API |

## What's this

This example demonstrates 

* How to access Twitter data using Teiid, and convert the data into tabular form. Dynamic VDB [twitter-vdb.xml](src/main/resources/twitter-vdb.xml) be used to define View within DDL metadata.
* How to set security domain to resource adapter in embedded.


The examples use `java:/twitterDS` referenced with Web Service data source, which will be setup automatically while the examples start running.

## Prerequisites

There are 3 prerequisites which is necessary before run example.

1) Login to https://apps.twitter.com/ with your Twitter Account, create a Twitter Apps.

2) Security setting up

Twitter use OAuth authentication, run teiid-oauth-util.sh utility:

~~~
$ ./bin/teiid-oauth-util.sh
~~~

This will generate security options which used in JBoss Server, convert it to suitable embedded, below is a example:

~~~
    <application-policy name = "teiid-security-twitter"> 
       <authentication>
          <login-module code = "org.teiid.jboss.oauth.OAuth10LoginModule" flag = "required">  
              <module-option name="consumer-key">nHhsL5Dm5Zi77eqWDDcQ</module-option>
              <module-option name="consumer-secret">VovoE7R5RJOI9GSAXTx25yppfDJ6y5SUKPy2vlrVCDM</module-option>
              <module-option name="access-key">209490245-cUtcom3nEnV53mYZfhWegZpnm90nmPds0icguwzL</module-option>
              <module-option name="access-secret">WKIYjXmplrdssy382iJnoNzd0vVDCa13nCWywKzKrE</module-option>
          </login-module> 
       </authentication> 
    </application-policy>  
~~~

[authentication.conf](src/main/resources/picketbox/authentication.conf) is a example configuration which used in this example.

3) set java proxcy

> NOTE: this section only for running example in China.

Set http proxcy before run [TeiidEmbeddedTwitterDataSource](src/main/java/org/teiid/example/TeiidEmbeddedTwitterDataSource.java).

~~~
System.setProperty("http.proxyHost", "xxxx");
System.setProperty("http.proxyPort", "xxxx");
~~~

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

To add thirdpart dependencies

~~~
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
<dependency>
    <groupId>org.picketbox</groupId>
    <artifactId>picketbox</artifactId>
    <version>${version.picketbox}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedTwitterDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/socialmedia-integration/twitter-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~
