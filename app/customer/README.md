## Deploy and Consume Customer Service

* Build

Maven build commands:

~~~
mvn clean install
~~~

will generate deployment war `CustomerRESTWebSvc.war`.

* Deploy

Deploy `CustomerRESTWebSvc.war` to a running JBoss server(Assume JBoss EAP 6 run on localhost).

* Consume

[http://localhost:8080/CustomerRESTWebSvc/MyRESTApplication/customerList](http://localhost:8080/CustomerRESTWebSvc/MyRESTApplication/customerList)



