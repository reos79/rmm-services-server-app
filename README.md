# RMM REST API

## Table of Contents  
**[Introduction](#introduction)**<br>
**[Prerequisites](#prerequisites)**<br>
**[Configuration](#configuration)**<br>
**[How to run the application](#how-to-run-the-application)**<br>
**[Services](#services)**<br>
**[Database admin web console](#database-admin-web-console)**<br>

## Introduction
A Remote Monitoring and Management (RMM) company provides services that monitor and manage devices through this REST API.

## Prerequisites
- JDK 11 
- Maven 3.6.x or later

## Configuration

### Database
The application uses in memory H2 database for simplicity. There is no need to install it.
Initially it comes loaded with the following data:

The script with the initial data can be found in 
``rmm-services-server-app/src/main/resources/data.sql``

|Username|Password| Customer id|
|--------|--------| -----------|
|user1   |pwd1    | 1          |
|user2   |pwd2    | 2          |
|user3   |pwd3    | 3          |
|user4   |pwd4    | 4          |
|user5   |pwd5    | 5          |
|user6   |pwd6    | 6          |

### Property file
The application configuration can be found in
``rmm-services-server-app/src/main/resources/application.properties``

There are three main properties 

|Property      |Default value| Description                          |
|--------------|-------------| -------------------------------------|
|jwt.secret    |mySecretKey  | The secret use in the JWT token      |
|jwt.expiration|600000       | The expiration time of the JWT token |
|device.price  |4            | The device unit price                |



## How to run the application
Make sure the ``port 8080`` is free.

Get the code ``git clone https://github.com/reos79/rmm-services-server-app.git``

Execute the command ``mvn clean package spring-boot:run``

## Services
The services run on the current machine on port 8080
``http://localhost:8080``

This is a Postman collection that may help to call the services
``RMM.postman_collection.json``

#### Login
Allows to get a JWT Token for authorization, this token must be used in the authorization header of the other services.

##### Endpoint
``POST /authenticate``
##### Request Body
```
{
    "username" : "user1",
    "password" : "pwd1"  
}
```
##### Important Request Headers
Content-Type:application/json
##### Response Body
```
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTE3NjYwNCwiZXhwIjoxNjQxMTc3MjA0fQ.JFvslwgI-tGDcMHM94yujADhtviaZMRax4hDOfHfsqKdHjh5idPObGRsQPq3B0mGLt9i4hAEnLiWL0ZL7bhfRg"
}
```

#### Devices
Finds all customer devices.

##### Endpoint
``GET customers/{customerId}/devices``
##### Important Request Headers
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTA5MTk2NSwiZXhwIjoxNjQxMDkyNTY1fQ.Jx4Xnur7t-JLItt2_NDi4i2EVW7vGNeSihlmVu5fegZ37Aoxa-Isom_bGSBWJ7Wgv3llozys9yw4b96Pus9xmQ
##### Response Body
```
[
    {
        "id": 1,
        "name": "Device1",
        "type": "MAC"
    }
]
```

#### Device
Finds a customer devices by id.

##### Endpoint
``GET customers/{customerId}/devices/{deviceId}``
##### Important Request Headers
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTA5MTk2NSwiZXhwIjoxNjQxMDkyNTY1fQ.Jx4Xnur7t-JLItt2_NDi4i2EVW7vGNeSihlmVu5fegZ37Aoxa-Isom_bGSBWJ7Wgv3llozys9yw4b96Pus9xmQ
##### Response Body
```
{
    "id": 1,
    "name": "Device1",
    "type": "MAC"
}
```
#### Create device
Allows to create a device.

##### Endpoint
``POST /customers/{customerId}/devices``
##### Request Body
```
{
    "name": "Device5",
    "type": "WINDOWS_SERVER"
}
```
##### Important Request Headers
Content-Type:application/json
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTA5MTk2NSwiZXhwIjoxNjQxMDkyNTY1fQ.Jx4Xnur7t-JLItt2_NDi4i2EVW7vGNeSihlmVu5fegZ37Aoxa-Isom_bGSBWJ7Wgv3llozys9yw4b96Pus9xmQ
##### Response Body
```
{
    "id": 6,
    "name": "Device5",
    "type": "WINDOWS_SERVER"
}
```
#### Update device
Allows to update a device.

##### Endpoint
``PUT /customers/{customerId}/devices/{deviceId}``
##### Request Body
```
{
    "name": "Device2",
    "type": "MAC"
}
```
##### Important Request Headers
Content-Type:application/json
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTA5MTk2NSwiZXhwIjoxNjQxMDkyNTY1fQ.Jx4Xnur7t-JLItt2_NDi4i2EVW7vGNeSihlmVu5fegZ37Aoxa-Isom_bGSBWJ7Wgv3llozys9yw4b96Pus9xmQ
##### Response Body
```
{
    "id": 2,
    "name": "Device2",
    "type": "MAC"
}
```
#### Delete device
Allows to delete a device.

##### Endpoint
``DELETE /customers/{customerId}/devices/{deviceId}``

##### Important Request Headers
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTA5MTk2NSwiZXhwIjoxNjQxMDkyNTY1fQ.Jx4Xnur7t-JLItt2_NDi4i2EVW7vGNeSihlmVu5fegZ37Aoxa-Isom_bGSBWJ7Wgv3llozys9yw4b96Pus9xmQ

#### List Services
Finds all customer services.

##### Endpoint
``GET customers/{customerId}/services``
##### Important Request Headers
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTA5MTk2NSwiZXhwIjoxNjQxMDkyNTY1fQ.Jx4Xnur7t-JLItt2_NDi4i2EVW7vGNeSihlmVu5fegZ37Aoxa-Isom_bGSBWJ7Wgv3llozys9yw4b96Pus9xmQ
##### Response Body
```
[
    {
        "id": 1,
        "name": "Antivirus"
    }
]
```
#### Add service
Allows to add a service to a customer.

##### Endpoint
``POST /customers/{customerId}/services/{serviceId}``

##### Important Request Headers
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTA5MTk2NSwiZXhwIjoxNjQxMDkyNTY1fQ.Jx4Xnur7t-JLItt2_NDi4i2EVW7vGNeSihlmVu5fegZ37Aoxa-Isom_bGSBWJ7Wgv3llozys9yw4b96Pus9xmQ

#### Remove service
Allows to remove a service from a customer.

##### Endpoint
``DELETE /customers/{customerId}/services/{serviceId}``

##### Important Request Headers
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTA5MTk2NSwiZXhwIjoxNjQxMDkyNTY1fQ.Jx4Xnur7t-JLItt2_NDi4i2EVW7vGNeSihlmVu5fegZ37Aoxa-Isom_bGSBWJ7Wgv3llozys9yw4b96Pus9xmQ

#### Proforma invoice
Gets the proforma invoice.

##### Endpoint
``GET customers/{customerId}/proformaInvoice``
##### Important Request Headers
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoidXNlcjEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXIiOjEsImlhdCI6MTY0MTA5MTk2NSwiZXhwIjoxNjQxMDkyNTY1fQ.Jx4Xnur7t-JLItt2_NDi4i2EVW7vGNeSihlmVu5fegZ37Aoxa-Isom_bGSBWJ7Wgv3llozys9yw4b96Pus9xmQ
##### Response Body
```
{
    "detail": [
        {
            "elementName": "Devices cost",
            "total": 20.0
        },
        {
            "elementName": "Antivirus cost",
            "total": 31.0
        },
        {
            "elementName": "Teamviewer cost",
            "total": 5.0
        },
        {
            "elementName": "Cloudberry cost",
            "total": 15.0
        }
    ],
    "total": 71.0
}
```

## Database admin web console
In order to manage the H2 database the admin web console has been exposed in the URL

```
http://{servername}:{port}/h2-console
```
Data access values

|Property    |Default value   |
|------------|----------------|
|Driver	Class|org.h2.Driver   |
|JDBC URL    |jdbc:h2:mem:rmm |
|User Name   |sa              |
|Password    |password        |
