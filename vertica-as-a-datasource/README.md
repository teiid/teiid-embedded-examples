| **Datasource** | **Level** | **Technologies** | **Prerequisites** | **Description** |
|:---------|:----------|:-----------------|:------------------|:----------------|
|HP Vertica |Beginner |Teiid, Dynamic VDB, View, Vertica Translator |HP Vertica(7.x) Analytic Database Server, Vertica JDBC Driver |Demonstrates using the Vertica Translator to access data in Vertica Server |

## What's this

This example demonstrates using the Vertica Translator to access data in Vertica Server. Dynamic VDB [vertica-vdb.xml](src/main/resources/vertica-vdb.xml) be used to define View map to Vertica table within a DDL metadata.

The examples use `java:/verticaDS` referenced with Vertica data source, which will be setup automatically while the examples start running.

## Prerequisites

There are 4 prerequisites which is necessary before run example.

**1.** Install Vertica Server

With [Downloading and Starting the Virtual Machine Guide](http://my.vertica.com/docs/7.0.x/HTML/index.htm#Authoring/GettingStartedGuide/DownloadingAndStartingVM/DownloadingAndStartingVM.htm%3FTocPath%3DGetting Started Guide), HP Vertica is available as a Virtual Machine (VM) that is pre-installed on a 64-bit CentOS image and comes with a license for 500 GB of data storage. So we can download VM image for a quick install.

* [Install VMware Workstation on Fedora 20](http://www.linux.com/community/forums/cloud-management/how-to-install-vmware-1001-on-fedora-20-x86-64)
* [Virtual Machines Image .vmx Download](https://my.vertica.com/download-community-edition/)

**2.** Setup VMart Database

Use [Installing and Connecting to the VMart Example Database](http://my.vertica.com/docs/7.0.x/HTML/index.htm#Authoring/GettingStartedGuide/InstallingAndConnectingToVMart/InstallingandConnecting.htm%3FTocPath%3DGetting%20Started%20Guide|Installing%20and%20Connecting%20to%20the%20VMart%20Example%20Database) create database 'VMart', and load all test tables.

**3.** Download Vertica JDBC Driver

Vertica JDBC Driver can be download from [https://my.vertica.com/download-community-edition/](https://my.vertica.com/download-community-edition/).

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

## Run

* Run from Source code

Import source code to a IDE(Eclipse), run TeiidEmbeddedVerticaDataSource as Java Application.

> NOTE: This example don't support run via mvn exec commands, you need add Vertica JDBC Driver to classpath.

