# RabbitMQ Microservices - Spring Boot Messaging Architecture

A comprehensive demonstration of microservices messaging patterns using Spring Boot and RabbitMQ. This project showcases both simple and advanced message publishing/consuming patterns with proper JSON serialization and database persistence.

## Author

**KiAA Khalid** - Lead Developer

## Project Overview

This project consists of four independent microservices that demonstrate different RabbitMQ messaging patterns:

### 1. Messaging Producer Service
A REST API service that publishes user messages to RabbitMQ with direct exchange routing. Demonstrates the producer pattern with JSON serialization.

- **Port**: 8081
- **Package**: `com.kiaa.messaging.producer`
- **Key Features**:
  - REST endpoint for message publishing
  - JSON message serialization
  - Direct exchange routing
  - Comprehensive logging

### 2. Messaging Consumer Service
A consumer service that listens to RabbitMQ queues and persists user messages to a MySQL database. Demonstrates the consumer pattern with database integration.

- **Port**: 8080
- **Package**: `com.kiaa.messaging.consumer`
- **Key Features**:
  - RabbitMQ message consumption
  - MySQL database persistence
  - JPA/Hibernate integration
  - Automatic message deserialization

### 3. RabbitMQ Publisher Service
A simple REST API service for publishing custom messages to RabbitMQ using topic exchange routing.

- **Port**: 8123
- **Package**: `com.kiaa.rabbitmq.publisher`
- **Key Features**:
  - REST endpoint for message publishing
  - Topic exchange routing
  - Auto-generated message IDs and timestamps
  - JSON serialization

### 4. RabbitMQ Subscriber Service
A simple consumer service that listens to RabbitMQ queues and logs received messages.

- **Port**: 8124
- **Package**: `com.kiaa.rabbitmq.subscriber`
- **Key Features**:
  - RabbitMQ message consumption
  - Topic exchange routing
  - Comprehensive message logging
  - JSON deserialization

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring AMQP**: RabbitMQ integration
- **Spring Data JPA**: Database persistence (Consumer Service)
- **MySQL**: Database (Consumer Service)
- **Lombok**: Boilerplate code reduction
- **Jackson**: JSON serialization/deserialization
- **Maven**: Build and dependency management

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         RabbitMQ Server                         │
│  ┌──────────────────┐         ┌──────────────────┐            │
│  │  user.exchange   │         │ kiaa_message_    │            │
│  │  (Direct)        │         │ exchange (Topic) │            │
│  └────────┬─────────┘         └────────┬─────────┘            │
│           │                            │                       │
│           ▼                            ▼                       │
│  ┌──────────────────┐         ┌──────────────────┐            │
│  │  user.queue      │         │ kiaa_message_    │            │
│  │                  │         │ queue            │            │
│  └──────────────────┘         └──────────────────┘            │
└─────────────────────────────────────────────────────────────────┘
         ▲         │                    ▲         │
         │         │                    │         │
         │         ▼                    │         ▼
┌────────┴────┐  ┌────────────┐  ┌─────┴─────┐  ┌──────────────┐
│  Messaging  │  │ Messaging  │  │ RabbitMQ  │  │  RabbitMQ    │
│  Producer   │  │ Consumer   │  │ Publisher │  │  Subscriber  │
│  Service    │  │ Service    │  │ Service   │  │  Service     │
│  (8081)     │  │ (8080)     │  │ (8123)    │  │  (8124)      │
└─────────────┘  └──────┬─────┘  └───────────┘  └──────────────┘
                        │
                        ▼
                 ┌──────────────┐
                 │    MySQL     │
                 │   Database   │
                 └──────────────┘
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **RabbitMQ Server** (running on localhost:5672)
- **MySQL Server** (for Messaging Consumer Service)

## Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/kiaaKhalid/tp31-ACE.git
cd tp31-ACE
```

### 2. Start RabbitMQ Server

```bash
# Using Docker
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# Or install locally and start the service
# Access RabbitMQ Management UI at http://localhost:15672
# Default credentials: guest/guest
```

### 3. Setup MySQL Database (for Messaging Consumer Service)

```bash
# Create database
mysql -u root -p
CREATE DATABASE testdb;
```

Update `messaging-consumer-service/src/main/resources/application.yml` with your MySQL credentials if needed.

### 4. Build All Services

```bash
# Build all services
cd messaging-producer-service && mvn clean install && cd ..
cd messaging-consumer-service && mvn clean install && cd ..
cd rabbitmq-publisher-service && mvn clean install && cd ..
cd rabbitmq-subscriber-service && mvn clean install && cd ..
```

## Running the Services

### Start Services Individually

```bash
# Terminal 1 - Messaging Producer Service
cd messaging-producer-service
mvn spring-boot:run

# Terminal 2 - Messaging Consumer Service
cd messaging-consumer-service
mvn spring-boot:run

# Terminal 3 - RabbitMQ Publisher Service
cd rabbitmq-publisher-service
mvn spring-boot:run

# Terminal 4 - RabbitMQ Subscriber Service
cd rabbitmq-subscriber-service
mvn spring-boot:run
```

## Testing the Services

### Test Messaging Producer/Consumer Flow

```bash
# Send a user message
curl -X POST http://localhost:8081/api/produce \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user123",
    "userName": "John Doe"
  }'

# Check the consumer service logs to see the message being received and persisted
# Check MySQL database to verify the message was saved
```

### Test RabbitMQ Publisher/Subscriber Flow

```bash
# Publish a custom message
curl -X POST http://localhost:8123/publish \
  -H "Content-Type: application/json" \
  -d '{
    "messageContent": "Hello from RabbitMQ!"
  }'

# Check the subscriber service logs to see the message being received
```

## Configuration

### Messaging Producer Service
- **Port**: 8081
- **RabbitMQ Exchange**: user.exchange
- **Routing Key**: user.routingkey

### Messaging Consumer Service
- **Port**: 8080
- **RabbitMQ Queue**: user.queue
- **Database**: MySQL (testdb)

### RabbitMQ Publisher Service
- **Port**: 8123
- **RabbitMQ Exchange**: kiaa_message_exchange
- **Routing Key**: message.routing.key

### RabbitMQ Subscriber Service
- **Port**: 8124
- **RabbitMQ Queue**: kiaa_message_queue

## Project Structure

```
.
├── messaging-producer-service/
│   ├── src/main/java/com/kiaa/messaging/producer/
│   │   ├── MessagingProducerApplication.java
│   │   ├── controller/ProducerController.java
│   │   ├── service/ProducerService.java
│   │   ├── config/RabbitMQConfig.java
│   │   └── domain/UserMessage.java
│   └── pom.xml
│
├── messaging-consumer-service/
│   ├── src/main/java/com/kiaa/messaging/consumer/
│   │   ├── MessagingConsumerApplication.java
│   │   ├── service/ConsumerService.java
│   │   ├── repository/UserMessageRepository.java
│   │   ├── config/RabbitMQConfig.java
│   │   └── domain/UserMessage.java
│   └── pom.xml
│
├── rabbitmq-publisher-service/
│   ├── src/main/java/com/kiaa/rabbitmq/publisher/
│   │   ├── RabbitMQPublisherApplication.java
│   │   ├── controller/MessagePublisherController.java
│   │   ├── config/RabbitMQConfiguration.java
│   │   └── model/CustomMessage.java
│   └── pom.xml
│
├── rabbitmq-subscriber-service/
│   ├── src/main/java/com/kiaa/rabbitmq/subscriber/
│   │   ├── RabbitMQSubscriberApplication.java
│   │   ├── listener/MessageListenerComponent.java
│   │   ├── config/RabbitMQConfiguration.java
│   │   └── model/CustomMessage.java
│   └── pom.xml
│
└── README.md
```

## Key Features

### Comprehensive Javadoc
All classes, methods, and fields are documented with detailed Javadoc comments explaining their purpose, parameters, and return values.

### Professional Code Quality
- Descriptive variable and method names
- Proper field organization (constants first, then dependencies)
- Consistent naming conventions
- Comprehensive error handling and logging

### Modern Spring Boot Practices
- Constructor-based dependency injection
- Configuration externalization
- JSON message serialization
- RESTful API design

### Database Integration
- JPA/Hibernate for ORM
- Spring Data repositories
- Automatic schema generation

## Development

### Running Tests

```bash
# Run tests for each service
cd messaging-producer-service && mvn test
cd messaging-consumer-service && mvn test
cd rabbitmq-publisher-service && mvn test
cd rabbitmq-subscriber-service && mvn test
```

### Building JAR Files

```bash
# Build executable JARs
mvn clean package

# Run the JAR
java -jar target/messaging-producer-service-1.0.0.jar
```

## Troubleshooting

### RabbitMQ Connection Issues
- Ensure RabbitMQ server is running on localhost:5672
- Check RabbitMQ credentials (default: guest/guest)
- Verify firewall settings

### MySQL Connection Issues
- Ensure MySQL server is running
- Verify database credentials in application.yml
- Check if testdb database exists

### Port Conflicts
- Ensure ports 8080, 8081, 8123, and 8124 are available
- Modify server.port in application properties if needed

## License

This project is developed for educational purposes.

## Contact

**KiAA Khalid**  
Email: kiaa.khalid@example.com

---

**Version**: 1.0.0  
**Last Updated**: December 2024
