<artifact type="text/markdown" identifier="ecommerce-microservices-README" title="E-commerce Microservices README">

# E-commerce Microservices System

This is an educational project from the tutorial. I learned from the tutorial the implementation of a modular **E-commerce Microservices System** using Spring Boot and Spring Cloud. The system consists of several microservices, each responsible for specific business functions such as managing customers, orders, payments, products, notifications and more. The architecture uses Eureka for service discovery, Spring Cloud Config for centralised configuration and Kafka for communication between services.

## Features

- **Microservices**: Config Server, Discovery Server, Gateway, Customer Service, Product Service, Order Service, Payment Service, Notification Service.
- **Centralized Configuration**: Using Spring Cloud Config Server.
- **Service Discovery**: Powered by Eureka Server.
- **API Gateway**: Routing and load balancing with Spring Cloud Gateway.
- **Asynchronous Messaging**: Kafka-based communication between services.
- **Database Integration**: MongoDB and PostgreSQL for persistence.
- **Tracing & Monitoring**: Zipkin and Micrometer integration.

---

## Project Requirements

- **Java**: JDK 21
- **Maven**: 3.8+
- **Databases**:
    - PostgreSQL (Order and Payment services)
    - MongoDB (Customer and Notification services)
- **Kafka**: For asynchronous messaging.
- **Git Repository**: For configuration storage in Config Server.

---

## Dependencies

Each microservice has its own dependencies. Below are some key dependencies used across the system:

- **Spring Boot Starter Modules**:
    - `spring-boot-starter-web`
    - `spring-boot-starter-data-jpa`
    - `spring-boot-starter-data-mongodb`
    - `spring-boot-starter-validation`
    - `spring-boot-starter-actuator`
    - `spring-cloud-starter-config`
    - `spring-cloud-starter-netflix-eureka-client`
    - `spring-cloud-starter-gateway`

- **Messaging & Tracing**:
    - Apache Kafka
    - Micrometer Tracing with Brave
    - Zipkin Reporter

For a complete list of dependencies for each service, refer to their respective `pom.xml` files.

---

## Getting Started

### Prerequisites

1. Install Java JDK 21 and Maven.
2. Set up PostgreSQL and MongoDB databases.
3. Install Kafka and ensure it is running.
4. Configure Git repository for the Config Server.

### Configuration

Update the following environment variables in the `application.yml` or as system properties:

```
spring:
  application:
    name: config-server
  profiles:
    active: git
  cloud:
    config:
      server:
        git:
          uri: git@github.com:${USERNAME}/${GIT_REPO}
          default-label: main
          timeout: 5
          clone-on-start: true
          username: ${USERNAME}
          password: ${PASSWORD}

server:
  port: 8888
```

---

## How to Run the Application

### Step-by-Step Instructions

0. **Create Config Server Repository**:
    Add to config server repo a necessary *.yml files from each microservice =>TODO<=
1. **Start Config Server**:
   Navigate to the `config-server` directory and run: ```mvn spring-boot:run```

2. **Start Discovery Server**:
   Navigate to the `discovery` directory and run: ```mvn spring-boot:run```

3. **Start Gateway Service**:
   Navigate to the `gateway` directory and run: ```mvn spring-boot:run```

4. **Start Other Services**:
   Repeat the above command for each service (`customer`, `product`, `order`, `payment`, `notification`).

5. Access services via Gateway at `http://localhost:<gateway-port>`.

---

## Conclusion

This project demonstrates a robust implementation of a scalable microservices architecture for an e-commerce platform. It integrates modern tools like Spring Cloud, Kafka, Zipkin, and Micrometer to ensure scalability, reliability, and observability.

Feel free to explore each service's codebase for further insights into their implementation!

---
</artifact>

