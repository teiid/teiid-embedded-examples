## Build

Maven build commands:

~~~
mvn clean install
~~~

will generate `customer-swagger-swarm.jar` and `customer-swagger.war` under target folder.

## Start

### Run runnable `customer-swagger-swarm.jar`

~~~
java -jar customer-swagger-swarm.jar
~~~

### Deploy to JBoss

Deploy `customer-swagger.war` to a running JBoss server(Assume JBoss EAP 6 run on localhost).


## Available API Test

| **Name** | **API** |
|:---------|:--------|
|getCustomers  |http://localhost:8080/customer/customerList  |
|getCustomerList |http://localhost:8080/customer/getAll |
|getCustomerByNumber |http://localhost:8080/customer/getByNumber/{customernumber} |
|getCustomerByName |http://localhost:8080/customer/getByName/{customername} |
|getCustomerByCity |http://localhost:8080/customer/getByCity/{city} |
|getCustomerByCountry |http://localhost:8080/customer/getByCountry/{country} |

