# fuel-agent
Fetches fuel prices from gas stations in Europe and stores them in a MySQL database. The Angular front-end provides visualization and statistics of the fuel prices.

## Setup for development
* Install and run MySQL on your localhost using default port, root user and no password
* Enable the spring profile "development" when starting the service
* compile with Maven:
```
mvn clean package
```

## Setup for production
* Install and run MySQL in your production environment
* Update the connection settings for your production environment i.e. with an application.properties file in the classpath
* Start the service without specifying a spring profile:
```
java -jar fuel-agent-0.0.1-SNAPSHOT.jar
```
