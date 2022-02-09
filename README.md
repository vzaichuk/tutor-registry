### Requirements
Java 11 JDK and docker-compose are required for building and running the project.

### Run under docker compose
##### Build all projects at once. *Warn: Resource-intensive operation.*
```
./gradlew build
```
It builds all projects, including frontend code and packages services in corresponding archives.

##### Run all services. *Warn: Resource-intensive operation.*
```
docker-compose up
```
It creates containers and run all of them at once, including database configuration starter app.

##### Open up an application.
Just head to `http://localhost:8080` in your browser to open client application.



### Raw run.
##### Build all projects at once. *Warn: Resource-intensive operation.*
```
./gradlew build
```
It builds all projects, including frontend code and packages services in corresponding archives.

##### Prepare database instances.
Docker containers with database instances should be run before starting any service up. 
However we don't want to start up all containers we have in docker compose file.
Therefore all service configurations in docker compose file should be commented out except mysql-service, mongo-service, starter-service and client-service configurations.

##### Run database instances and client.
Now we can start up our database instances and client app:
```
docker-compose up
```

##### Setting up environment.
Before we can run any application we should setup our environment variables. Here's how we can do it:
```
source setup
```

##### Run all services. *Warn: Resource-intensive operation.*
Each service should be run on its own. Discovery service should be run first:
```
source setup
java -jar discovery/build/libs/discovery-1.0.war
```
Then any service can be run in any order, but particular order can be like following.
First, we run gateway with its own set of environment variables:
```
source setup-gateway
java -jar gateway/build/libs/gateway-1.0.war
```
This is because of service host names in route configuration. Then we can run rest of the services:
```
java -jar authentication/build/libs/authentication-1.0.war
java -jar account/build/libs/account-1.0.war
java -jar registration/build/libs/registration-1.0.war
java -jar notification/build/libs/notification-1.0.war
```
Don't forget about `source setup` for each service before running.

##### Open up an application.
Just head to `http://localhost:8080` in your browser to open client application.


#### Swagger
Swagger is available by address: `http://localhost:8080/swagger-ui/index.html`
