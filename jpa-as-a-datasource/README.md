| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|ldap Server |Beginner |Teiid, Dynamic VDB, Ldap Translator |OpenLDAP Server installed, Group 'HR' be created |Demonstrates using the ldap Translator to access data in OpenLDAP Server |

## What's this

This example demonstrates using the ldap Translator to access data in OpenLDAP Server. Dynamic VDB [ldap-vdb.xml](src/main/resources/ldap-vdb.xml) be used to define forien table within DDL metadata.

The examples use `java:/ldapDS` referenced to Ldap data source, which will be setup automatically while the examples start running.

## Prerequisites

There are 3 prerequisites which is necessary before run example.

**1.** Install OpenLDAP Server

Refer to [OpenLDAP Admin Guide](http://www.openldap.org/doc/admin24/guide.html) to install OpenLDAP Server.

**2.** Create Group and User

Refer to [OpenLDAP Admin Guide](http://www.openldap.org/doc/admin24/guide.html) to create Group `HR` and 3 users(`hr1`, `hr2`, `hr3`) under it.

**3.** Edit ldap.properties

Edit [ldap.properties](src/main/resources/ldap.properties) make sure ldap.url, ldap.adminUserDN and ldap.adminUserPassword point to LDAP Server which setuped above.

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
    <artifactId>translator-ldap</artifactId>
    <version>${version.teiid}</version>
</dependency>		
<dependency>
    <groupId>org.jboss.teiid.connectors</groupId>
    <artifactId>connector-ldap</artifactId>
    <version>${version.teiid}</version>
</dependency>
~~~

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedLDAPDataSource as Java Application.

* Run from mvn

~~~
$ cd teiid-embedded-examples/ldap-as-a-datasource
$ mvn clean install
$ mvn exec:java
~~~
