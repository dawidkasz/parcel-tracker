# PIS

## Project overview
This repository is for a microservices-based Parcel Tracking System. It allows clients to add and track shipments using a unique number. 
The system integrates with various carriers to locate the specific shipment. It features storing tracking history in a local database and periodically checks for updates on saved shipments. 
The system offers search functionality across all shipment attributes and enables printing of the tracking history, with files being stored in a separate resource.

## Config client setup

1. Add dependencies 
    ```
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
    ```

2. Add decorator on beans which will use config from config server:
`@RefreshScope`

3. Add these properties to properties file:
    ```properties
    spring.config.import=configserver:http://localhost:8888
    management.endpoints.web.exposure.include=refresh
    ```
4. Create config file in `configs` in root directory

   - use `application.yml` for general configurations
   - create `{application name}.yml` file for service specific configuration
5. After changes make `POST` request for service which configs you want to refresh i.e.
   ```shell
   curl --location --request POST 'localhost:8080/actuator/refresh'
   ```