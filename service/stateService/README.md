## What's this

The StateService supply service for GetAllStateInfo and GetStateInfo via StateCode. The following have the steps for build, deploy.

## Build

Maven build commands:

~~~
mvn clean install
~~~

will generate deployment jar `StateService.jar`.

## Deploy

Deploy `StateService.jar` to a running JBoss server(Assume JBoss EAP 6 run on localhost).

Once deployed finised, StateService WSDL File can be viewed via [http://localhost:8080/StateService/stateService/StateServiceImpl?WSDL](http://localhost:8080/StateService/stateService/StateServiceImpl?WSDL)

