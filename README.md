GlobalBooks Inc. - SOA & Microservices Project
This is a coursework project for the CCS3341 SOA & Microservices module. It demonstrates the refactoring of a monolithic e-commerce application ("GlobalBooks Inc.") into a distributed system using Service-Oriented Architecture and microservices principles.

The final application consists of five independently deployable services that communicate both synchronously (SOAP/REST) and asynchronously (messaging).
The system is composed of five core services, all running in Docker containers:

Technology Stack
Language: Java 17
Build Tool: Maven
Containerization: Docker & Docker Compose
Web Services: JAX-WS (for SOAP), Javalin (for REST)
Messaging: RabbitMQ
Testing: SoapUI, Postman
Follow these instructions to build and run the entire application on your local machine.

Prerequisites
Java (JDK 17)
Apache Maven
Docker Desktop

Installation & Running
Clone the repository (or download the source code).
Build each Java service using Maven. Navigate into each of the four service directories and run the appropriate build command.

Bash
# For CatalogService
cd catalog-service
mvn clean install

# For OrdersService, PaymentsService, and ShippingService
cd ../orders-service
mvn clean package
Repeat the mvn clean package command for the payments-service and shipping-service directories.

Start the entire application using Docker Compose. From the project's root directory, run:

Bash
docker-compose up -d --build
You can verify that all five containers are running with the docker ps command.

How to Test
1. CatalogService (SOAP)
Use SoapUI or a similar tool.

Create a new SOAP project using the WSDL URL: http://localhost:8080/catalog-service/CatalogService?wsdl

Send a request to the lookupPrice operation.

2. OrdersService (REST)
Use Postman or a similar tool.

Send a POST request to http://localhost:7070/orders.

Set the body to raw JSON with the following format:

JSON

{
    "customerId": "test-customer",
    "items": [
        {
            "isbn": "978-0134685991",
            "quantity": 1
        }
    ]
}
You should receive a 201 Created response.



3. End-to-End Messaging Test
Open two terminals and watch the logs for the consumer services:
End-to-End Messaging Test
Open two terminals and watch the logs for the consumer services:
docker-compose logs -f payments-service
docker-compose logs -f shipping-service

Send a POST request to the OrdersService as described above.

Observe the "Received instruction" messages appearing in both terminal logs, confirming the asynchronous workflow is successful.

BPEL Orchestration (Design)
The /bpel folder contains design artifacts (PlaceOrder.bpel, PlaceOrder.wsdl, deploy.xml) for a BPEL process to demonstrate service orchestration. 
Due to significant technical challenges with the Apache ODE engine, this component is not part of the final running application but serves as a design and troubleshooting reference.
