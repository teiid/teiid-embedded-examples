## What's this

The StateService supply service for GetAllStateInfo and GetStateInfo via StateCode. The following have the steps for build, deploy.

## Build and Start

Maven build commands:

~~~
mvn clean install
~~~

will generate runnable jar `stateService-swarm.jar`, start StateService via 

~~~
java -jar stateService-swarm.jar
~~~

Once started, StateService WSDL File can be viewed via [http://localhost:8080/stateService?wsdl](http://localhost:8080/stateService?wsdl)


