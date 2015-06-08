---
Name: ldap-as-a-datasource
Level: Beginner
Technologies: Teiid, Dynamic VDB, Ldap Translator
Prerequisites: OpenLDAP Server installed, Group 'HR' be created
Description: Demonstrates using the ldap Translator to access data in OpenLDAP Server
---

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


## Run

Refer to [../README.md](../README.md) run section to run the example.
