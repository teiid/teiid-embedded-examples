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
$ mvn clean install -P release -s settings.xml
~~~

you can find the `teiid-${version}-embedded-dist.zip` under `build/target`.

## Run

### Run from dist

Unzip dist zip file, navigate into the `examples` folder, start example with start up scripts(run.sh/run.bat).

~~~~
$ unzip teiid-${version}-embedded-dist.zip -d teiid-examples
$ cd teiid-examples/examples
~~~~

### Run from Source code

Import `teiid-examples` source code to a IDE(Eclipse), run each examples with corresponding Main methods.

### Run from mvn 

Navigating to a examples execute mvn exec commands.

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
