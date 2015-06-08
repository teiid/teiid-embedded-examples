## Teiid Examples

Teiid Examples show how to integrate multiple, heterogenous data stores and enterprise services in a light-weight, micro-service way.

## Build

* install JDK 1.7 or higher
* install maven 3 - http://maven.apache.org/download.html
* Create a github account and fork Teiid Examples

Enter the following:

~~~
$ git clone https://github.com/<yourname>/teiid-examples.git
$ cd teiid-examples
$ mvn clean install -s settings.xml
~~~

## Run

### Run from Source code

Import `teiid-examples` source code to a IDE(Eclipse), run each examples with corresponding Main methods.

### Run from mvn 

With above **Build** section steps, make sure examples build success, navigating to a example execute mvn exec commands, for example:

~~~
$ cd teiid-examples/embedded-portfolio
$ mvn exec:java
~~~

## Link

* [Teiid Website](http://teiid.org)
* [Teiid Documentation](http://www.jboss.org/teiid/docs)
* [Teiid Examples Documentation](https://docs.jboss.org/author/display/teiidexamples/Teiid+Examples)
* [Teiid JIRA](https://issues.jboss.org/browse/TEIID)
* [Teiid User Forum](https://community.jboss.org/en/teiid?view=discussions)
