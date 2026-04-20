package com.bajajfinserv.service;

import com.bajajfinserv.dto.WebhookGenerationResponse;
import com.bajajfinserv.entity.Department;
import com.bajajfinserv.entity.Employee;
import com.bajajfinserv.repository.DepartmentRepository;
import com.bajajfinserv.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebhookService {

    private final RestTemplate restTemplate;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ObjectMapper objectMapper;

    private static final String WEBHOOK_GENERATION_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String TEST_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

    @EventListener(ApplicationReadyEvent.class)
    public void generateWebhookAndSolveSQL() {
        try {
            log.info("Starting webhook generation and SQL problem solving on application startup");

            // Step 1: Generate webhook
            WebhookGenerationResponse webhookResponse = generateWebhook();
            if (webhookResponse == null) {
                log.error("Failed to generate webhook");
                return;
            }

            String webhookUrl = webhookResponse.getWebhook();
            String accessToken = webhookResponse.getAccessToken();

            log.info("Webhook generated successfully: {}", webhookUrl);

            // Step 2: Build the SQL query for submission
            String finalQuery = buildFinalQuery();

            // Step 3: Send the query to the required test webhook endpoint
            sendResultToWebhook(accessToken, finalQuery);

        } catch (Exception e) {
            log.error("Error in webhook generation and SQL solving: ", e);
        }
    }

    /**
     * Step 1: Generate webhook by sending POST request
     */
    private WebhookGenerationResponse generateWebhook() {
        try {
            Map<String, String> requestBody = new LinkedHashMap<>();
            requestBody.put("name", "John Doe");
            requestBody.put("regNo", "REG12347");
            requestBody.put("email", "john@example.com");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            log.info("Sending webhook generation request to: {}", WEBHOOK_GENERATION_URL);

            WebhookGenerationResponse response = restTemplate.postForObject(
                    WEBHOOK_GENERATION_URL,
                    request,
                    WebhookGenerationResponse.class
            );

            log.info("Webhook response received: {}", response);
            return response;

        } catch (Exception e) {
            log.error("Error generating webhook: ", e);
            return null;
        }
    }

    /**
     * Step 2: Build the SQL query that counts younger employees per department.
     */
    private String buildFinalQuery() {
        return "SELECT e.emp_id AS EMP_ID, " +
                "e.first_name AS FIRST_NAME, " +
                "e.last_name AS LAST_NAME, " +
                "d.department_name AS DEPARTMENT_NAME, " +
                "COUNT(e2.emp_id) AS YOUNGER_EMPLOYEES_COUNT " +
                "FROM employee e " +
                "JOIN department d ON e.department = d.department_id " +
                "LEFT JOIN employee e2 ON e.department = e2.department AND e2.dob > e.dob " +
                "GROUP BY e.emp_id, e.first_name, e.last_name, d.department_name " +
                "ORDER BY e.emp_id DESC";
    }

    /**
     * Step 3: Send the final SQL query to the test webhook URL.
     */
    private void sendResultToWebhook(String accessToken, String finalQuery) {
        try {
            log.info("Sending final query to test webhook: {}", TEST_WEBHOOK_URL);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);

            Map<String, String> payload = new LinkedHashMap<>();
            payload.put("finalQuery", finalQuery);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

            log.info("Sending POST request to test webhook with access token");
            restTemplate.postForObject(TEST_WEBHOOK_URL, request, String.class);

            log.info("Final query successfully sent to test webhook");

        } catch (Exception e) {
            log.error("Error sending final query to test webhook: ", e);
        }
    }

}

