# PIS

**Table of contents:**
* [Project overview](#project-overview)
* [App setup](#app-setup)
* [Start project](#start-project)
  * [Tutorials](#tutorials)

## Project overview
This repository is for a microservices-based Parcel Tracking System. It allows clients to add and track shipments using a unique number. 
The system integrates with various carriers to locate the specific shipment. It features storing tracking history in a local database and periodically checks for updates on saved shipments. 
The system offers search functionality across all shipment attributes and enables printing of the tracking history, with files being stored in a separate resource.

## App setup

1. Add dependencies 
    ```
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
    ```

2. Add decorator on beans which will use config from config server:
`@RefreshScope`

3. Add these properties to your service's properties file:
 ```yaml
spring:
   application:
      name: TODO
   cloud:
      config:
         discovery:
            enabled: true
            service-id: CONFIG-SERVER
   config:
      import: "optional:configserver:"

server:
   port: TODO # used only for debug purposes as services discover themselves using service-discovery

eureka:
   client:
      serviceUrl:
         defaultZone: ${EUREKA_URI:http://localhost:8761/eureka}
   instance:
      preferIpAddress: true

management:
   endpoints:
      web:
         exposure:
            include: refresh
 ```
4. Create config file in `configs` in `config-server` directory

   - use `application.yml` for general configurations
   - create `{application name}.yml` file for service specific configuration
5. After changes make `POST` request for service which configs you want to refresh i.e.
   ```shell
   curl --location --request POST 'YOUR-SERVICE-URL/actuator/refresh'
   ```
   For example `http://localhost:8080/actuator/refresh`.

## Start project
1. Start service discovery
2. (Optionally) Start config server
3. Start your service and wait for registering

### Tutorials
- https://www.baeldung.com/spring-cloud-netflix-eureka
- https://spring.io/projects/spring-cloud
- https://youtu.be/9Jd4aY5-9S4