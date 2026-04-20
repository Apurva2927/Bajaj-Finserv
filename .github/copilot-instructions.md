# Project Setup Instructions

## Overview
Spring Boot application that generates webhook, solves SQL problem, and sends results via JWT.

## Completed Steps

- [x] Verify project requirements
- [x] Scaffold Spring Boot Maven project
- [x] Create project structure with all necessary components
- [x] Add Spring Boot dependencies
- [x] Configure application properties
- [x] Create entity classes (Department, Employee, Payment)
- [x] Create DTOs for API communication
- [x] Create repositories for database access
- [x] Create WebhookService with complete business logic
- [x] Create DataInitializer for test data
- [x] Add REST communication with WebClient/RestTemplate
- [x] Add JWT token generation
- [x] Document project in README.md

## Project Structure
```
Bajaj Finserv/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/bajajfinserv/
        │   ├── WebhookSqlSolverApplication.java
        │   ├── config/
        │   │   └── DataInitializer.java
        │   ├── dto/
        │   │   ├── WebhookGenerationResponse.java
        │   │   └── EmployeeYoungerCountDTO.java
        │   ├── entity/
        │   │   ├── Department.java
        │   │   ├── Employee.java
        │   │   └── Payment.java
        │   ├── repository/
        │   │   ├── DepartmentRepository.java
        │   │   ├── EmployeeRepository.java
        │   │   └── PaymentRepository.java
        │   └── service/
        │       └── WebhookService.java
        └── resources/
            └── application.properties
```

## Build Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build the project
```bash
cd c:\Users\ASUS\Desktop\Bajaj\ Finserv
mvn clean install
```

### Run the application
```bash
mvn spring-boot:run
```

## How It Works

1. **On Startup**: Application listens to `ApplicationReadyEvent`
2. **Generate Webhook**: POSTs to `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`
3. **Solve SQL Problem**: Calculates younger employees count per department
4. **Send Results**: Submits results to webhook URL with JWT token in Authorization header

## Key Features

- ✅ No controllers/endpoints exposed
- ✅ Automatic execution on startup
- ✅ JWT token-based authentication
- ✅ RestTemplate for REST calls
- ✅ H2 in-memory database with test data
- ✅ Complete error handling and logging

## Testing

The application will automatically:
1. Load test data (6 departments, 10 employees, 16 payments)
2. Connect to the webhook endpoint
3. Calculate the SQL query result
4. Send the result with JWT authentication

Monitor the console logs to see execution progress.

## Configuration

All configuration is in `src/main/resources/application.properties`:
- Database type and URL
- JPA/Hibernate settings
- Logging levels

## Troubleshooting

1. **Build fails**: Ensure Java 17+ and Maven 3.6+ are installed
2. **Webhook errors**: Check network connectivity
3. **JWT issues**: Verify JWT libraries in pom.xml

See README.md for detailed documentation.
