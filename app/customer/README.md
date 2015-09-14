## Build

Maven build commands:

~~~
mvn clean install
~~~

will generate `CustomerRESTWebSvc-swarm.jar` and `CustomerRESTWebSvc.war` under target folder.

## Start

### Run runnable `CustomerRESTWebSvc-swarm.jar`

~~~
java -jar CustomerRESTWebSvc-swarm.jar
~~~

### Deploy to JBoss

Deploy `CustomerRESTWebSvc.war` to a running JBoss server(Assume JBoss EAP 6 run on localhost).


> Note: Customer Service can be accessed via [http://localhost:8080/CustomerRESTWebSvc/MyRESTApplication/customerList](http://localhost:8080/CustomerRESTWebSvc/MyRESTApplication/customerList)

## Available API Test

| **Name** | **API** |
|:---------|:--------|
|getCustomers  |http://localhost:8080/customer/customerList  |
|getCustomerList |http://localhost:8080/customer/getAll |
|getCustomerByNumber |http://localhost:8080/customer/getByNumber/{customernumber} |
|getCustomerByName |http://localhost:8080/customer/getByName/{customername} |
|getCustomerByCity |http://localhost:8080/customer/getByCity/{city} |
|getCustomerByCountry |http://localhost:8080/customer/getByCountry/{country} |

