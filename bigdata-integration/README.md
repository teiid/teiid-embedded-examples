
## Prerequisites

This sections including the steps for setting up Prerequisites for all bigdata-integration examples.

### Hadoop Installation

This section including step by step procedures for installing 'Hadoop 1.2.1' to RHEL 6, and configuring a Single Node Setup.

#### Step.1 Prerequisites

~~~
$ uname -a
Linux kylin.xx.com 2.6.32-431.20.3.el6.x86_64 #1 SMP Fri Jun 6 18:30:54 EDT 2014 x86_64 x86_64 x86_64 GNU/Linux

$ java -version
java version "1.7.0_60"
Java(TM) SE Runtime Environment (build 1.7.0_60-b19)
Java HotSpot(TM) 64-Bit Server VM (build 24.60-b09, mixed mode)
~~~

#### Step.2 Download and Install

~~~
$ wget http://apache.mesi.com.ar/hadoop/common/hadoop-1.2.1/hadoop-1.2.1.tar.gz
$ tar -xvf hadoop-1.2.1.tar.gz
$ cd hadoop-1.2.1
~~~

#### Step.3 Configure

Edit 'conf/hadoop-env.sh', comment out JAVA_HOME, make sure it point to a valid Java Home:

~~~
export JAVA_HOME=/usr/java/jdk1.7.0_60
~~~

> NOTE: Hadoop 1.2.1 need Java 1.6 or higher

Edit 'conf/core-site.xml', add the following properties in <configuration>:

~~~
<property>
     <name>hadoop.tmp.dir</name>
      <value>/home/kylin/tmp/hadoop</value>
</property>
<property>
     <name>dfs.name.dir</name>
     <value>/home/kylin/tmp/hadoop/name</value>
</property>
<property>
     <name>fs.default.name</name>
     <value>hdfs://localhost:9000</value>
</property>
<property>
    <name>dfs.permissions</name>
    <value>false</value>
</property>
~~~

> NOTE: the property's value should match to your's setting.

Edit 'conf/hdfs-site.xml', add the following 2 property in <configuration>:

~~~
<property>
    <name>mapred.job.tracker</name>
    <value>localhost:9001</value>
</property>
~~~

Format a new distributed-filesystem via execute

~~~
hadoop-1.2.1/bin/hadoop namenode -format
~~~

#### Step.4 Start

Start all hadoop services via execute

~~~
$ ./bin/start-all.sh
~~~

> NOTE: there are 5 java processes which represent 5 services be started: `NameNode`, `SecondaryNameNode`, `DataNode`, `JobTracker`, `TaskTracker`. Execute 'jps -l' to check the java processes:

~~~
$ jps -l
4056 org.apache.hadoop.hdfs.server.namenode.NameNode
4271 org.apache.hadoop.hdfs.server.datanode.DataNode
4483 org.apache.hadoop.hdfs.server.namenode.SecondaryNameNode
4568 org.apache.hadoop.mapred.JobTracker
4796 org.apache.hadoop.mapred.TaskTracker
~~~

> NOTE: `NameNode`, `JobTracker`, `TaskTracker` has relevant Web Consoles for View and Monitor the serivces. Web Access URLs for Services:

~~~
http://localhost:50030/   for the Jobtracker
http://localhost:50070/   for the Namenode
http://localhost:50060/   for the Tasktracker
~~~

#### Step.5 Stop

Stop all hadoop services via execute

~~~
# bin/stop-all.sh
~~~

### Hive Installation

This section including step by step procedures for installing Apache Hive and set up HiveServer2.

#### Step.1 Prerequisites

Hadoop is the prerequisite, refer to above steps to install and start Hadoop.

#### Step.2 Install

~~~
$ tar -xvf apache-hive-1.2.1-bin.tar.gz
$ cd apache-hive-1.2.1-bin
~~~

#### Step.3 Configure

Create a 'hive-env.sh' under 'conf'

~~~
$ cd conf/
$ cp hive-env.sh.template hive-env.sh
$ vim hive-env.sh
~~~

comment out HADOOP_HOME and make sure point to a valid Hadoop home, for example:

~~~
HADOOP_HOME=/home/kylin/server/hadoop-1.2.1
~~~

Navigate to Hadoop Home, create '/tmp' and '/user/hive/warehouse' and chmod g+w in HDFS before running Hive:

~~~
$ ./bin/hadoop fs -mkdir /tmp
$ ./bin/hadoop fs -mkdir /user/hive/warehouse
$ ./bin/hadoop fs -chmod g+w /tmp
$ ./bin/hadoop fs -chmod g+w /user/hive/warehouse
$ ./bin/hadoop fs -chmod 777 /tmp/hive
~~~

> NOTE: Restart Hadoop services is needed, this for avoid 'java.io.IOException: Filesystem closed' in DFSClient check Open.

Create a 'hive-site.xml' file under conf folder

~~~
$ cd apache-hive-1.2.1-bin/conf/
$ touch hive-site.xml
~~~

Edit the 'hive-site.xml', add the following content:

~~~
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
    <property>
        <name>hive.server2.thrift.min.worker.threads</name>
        <value>5</value>
    </property>
    <property>
        <name>hive.server2.thrift.max.worker.threads</name>
        <value>500</value>
    </property>
    <property>
        <name>hive.server2.thrift.port</name>
        <value>10000</value>
    </property>
    <property>
        <name>hive.server2.thrift.bind.host</name>
        <value>0.0.0.0</value>
    </property>
</configuration>
~~~

> NOTE: there are other Optional properties, more refer to [Setting+Up+HiveServer2](https://cwiki.apache.org/confluence/display/Hive/Setting+Up+HiveServer2)

#### Step.4 Start HiveServer2

~~~
$ ./bin/hiveserver2
~~~

### Spark Installation

The following steps show how to install Apache Spark and start the Thrift JDBC/ODBC server.

#### Step.1 Install

~~~
$ tar -xvf $ tar -xvf spark-1.4.0-bin-hadoop2.4.tgz
$ cd spark-1.4.0-bin-hadoop2.4
~~~

#### Step.2 Start

~~~
$ ./sbin/start-thriftserver.sh
~~~
