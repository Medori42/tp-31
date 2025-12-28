# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0] - 2024-12-22

### Added
- Complete refactoring of TP31 RabbitMQ microservices project
- Messaging Producer Service with REST API for publishing user messages
- Messaging Consumer Service with database persistence
- RabbitMQ Publisher Service with topic exchange routing
- RabbitMQ Subscriber Service with message logging
- Comprehensive Javadoc documentation for all classes and methods
- Professional package structure (com.kiaa.messaging and com.kiaa.rabbitmq)
- Docker Compose configuration for infrastructure setup
- Comprehensive README with architecture diagrams and setup instructions
- Contributing guidelines
- Integration tests for all services

### Changed
- Renamed all folders to professional English names
- Updated package names from com.tp to com.kiaa
- Improved variable and parameter naming (English, descriptive)
- Reorganized method and field placement (constants first, then dependencies)
- Enhanced logging with SLF4J instead of System.out
- Updated Maven configurations with new groupId and developer information

### Fixed
- Fixed typo "recieved" to "received" in logging messages
- Fixed typo "succesfuly" to "successfully" in configuration

---

**Author**: KiAA Khalid  
**Version**: 1.0.0
