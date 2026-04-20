# Webhook SQL Solver - Spring Boot Application

## Overview
This Spring Boot application runs on startup, requests an access token, builds the SQL query for the employee-age problem, and submits that query to the fixed test webhook endpoint.

## Architecture
The application follows a startup-driven flow:

1. 'WebhookService' listens for `ApplicationReadyEvent`.
2. 'generateWebhook()' calls the webhook generation API and retrieves the `accessToken`.
3. 'buildFinalQuery()' constructs the SQL query.
4. 'sendResultToWebhook()' posts the query to the test webhook endpoint with the access token in the 'Authorization' header.
5. 'DataInitializer' loads sample departments and employees into the H2 database.

This keeps the flow fully automated and avoids exposing REST controller endpoints.

## Folder Structure
'''text
Bajaj Finserv/
pom.xml
README.md
mvnw.cmd
.mvn/
    src/
        main/
            java/
               com/bajajfinserv/
               WebhookSqlSolverApplication.java
               config/
                  DataInitializer.java
               dto/
                   WebhookGenerationResponse.java
               entity/
                   Department.java
                   Employee.java
                   Payment.java
               repository/
                  DepartmentRepository.java
                  EmployeeRepository.java
                   PaymentRepository.java
               service/
                   WebhookService.java
            resources/
                application.properties
'''

## Request Format
The submission must use this exact shape:

'''http
POST https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA
Authorization: <accessToken>
Content-Type: application/json

{
  "finalQuery": "YOUR_SQL_QUERY_HERE"
}
'''

## Problem Statement
Return, for every employee, the count of employees in the same department who are younger than that employee.

### Required Output Columns
1. EMP_ID
2. FIRST_NAME
3. LAST_NAME
4. DEPARTMENT_NAME
5. YOUNGER_EMPLOYEES_COUNT

The result is ordered by employee ID in descending order.

## Workflow
1. On startup, the application requests the webhook generation endpoint.
2. The response provides an `accessToken`.
3. The application builds the SQL query that solves the employee-age problem.
4. The query is sent to `https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA`.
5. The access token is passed directly in the `Authorization` header.
6. The JSON body contains only `finalQuery`.

## SQL Query Used
The application submits this SQL pattern:

'''sql
SELECT e.emp_id AS EMP_ID,
       e.first_name AS FIRST_NAME,
       e.last_name AS LAST_NAME,
       d.department_name AS DEPARTMENT_NAME,
       COUNT(e2.emp_id) AS YOUNGER_EMPLOYEES_COUNT
FROM employee e
JOIN department d ON e.department = d.department_id
LEFT JOIN employee e2 ON e.department = e2.department AND e2.dob > e.dob
GROUP BY e.emp_id, e.first_name, e.last_name, d.department_name
ORDER BY e.emp_id DESC;
'''

## Core Components
- 'WebhookService': requests the access token, builds the SQL query, and posts the payload to the test webhook.
- 'DataInitializer': seeds the H2 database with departments, employees, and payments.
- 'Department', 'Employee', 'Payment': JPA entities used by the solution.

## Technologies Used
1. Spring Boot 3.1.5
2. Spring Data JPA
3. H2 Database
4. Lombok
5. RestTemplate

## Database Configuration
1. Default database: H2 in-memory
2. Can be switched to MySQL by updating `application.properties`

### H2 Console
Access H2 console at `http://localhost:8080/h2-console` with:
1. URL: 'jdbc:h2:mem:testdb'
2. Username: `sa`
3. Password: empty

## Build and Run

### Prerequisites
1. Java 17 or higher
2. Maven 3.6+

### Build
'''bash
mvn clean install
'''

### Run
'''bash
mvn spring-boot:run
'''

## Example Logs
'''text
Starting webhook generation and SQL problem solving on application startup
Sending webhook generation request to: https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA
Webhook response received: WebhookGenerationResponse(...)
Sending final query to test webhook: https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA
Final query successfully sent to test webhook
'''

## Security Notes
1. The access token is sent as-is in the `Authorization` header.
2. The request body contains only the SQL query string under `finalQuery`.
3. No JWT generation is used in this flow.
4. Keep sensitive credentials out of source control in production deployments.


### Webhook request fails
1. Check network connectivity.
2. Verify the request format matches the required `Authorization` header and `finalQuery` body.

### Query submission fails
1. Confirm the SQL query matches the required output columns.
2. Verify the database has been initialized before the startup workflow runs.

## License
Internal - Bajaj Finserv
