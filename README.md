# fuel-agent
Fetches fuel prices from gas stations in Europe and stores them in a MySQL database. The Angular front-end provides visualization and statistics of the fuel prices.

## Setup for development
* Install and run MySQL on your localhost using default port, root user and no password
* Enable the spring profile "development" when starting the service

## Setup for production
* Install and run MySQL in your production environment
* Update the connection settings for your production environment in application.properties file
* Start the service without specifying a spring profile
