package com.bajajfinserv.config;

import com.bajajfinserv.entity.Department;
import com.bajajfinserv.entity.Employee;
import com.bajajfinserv.entity.Payment;
import com.bajajfinserv.repository.DepartmentRepository;
import com.bajajfinserv.repository.EmployeeRepository;
import com.bajajfinserv.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final PaymentRepository paymentRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeData() {
        try {
            log.info("Initializing database with test data");

            // Initialize Departments
            List<Department> departments = new ArrayList<>();
            departments.add(new Department(1, "HR"));
            departments.add(new Department(2, "Finance"));
            departments.add(new Department(3, "Engineering"));
            departments.add(new Department(4, "Sales"));
            departments.add(new Department(5, "Marketing"));
            departments.add(new Department(6, "IT"));

            departmentRepository.saveAll(departments);
            log.info("Departments initialized");

            // Initialize Employees
            List<Employee> employees = new ArrayList<>();
            employees.add(new Employee(1, "John", "Williams", LocalDate.of(1980, 5, 15), "Male", 3));
            employees.add(new Employee(2, "Sarah", "Johnson", LocalDate.of(1990, 7, 20), "Female", 2));
            employees.add(new Employee(3, "Michael", "Smith", LocalDate.of(1985, 2, 10), "Male", 3));
            employees.add(new Employee(4, "Emily", "Brown", LocalDate.of(1992, 11, 30), "Female", 4));
            employees.add(new Employee(5, "David", "Jones", LocalDate.of(1988, 9, 5), "Male", 5));
            employees.add(new Employee(6, "Olivia", "Davis", LocalDate.of(1995, 4, 12), "Female", 1));
            employees.add(new Employee(7, "James", "Wilson", LocalDate.of(1983, 3, 25), "Male", 6));
            employees.add(new Employee(8, "Sophia", "Anderson", LocalDate.of(1991, 8, 17), "Female", 4));
            employees.add(new Employee(9, "Liam", "Miller", LocalDate.of(1979, 12, 1), "Male", 1));
            employees.add(new Employee(10, "Emma", "Taylor", LocalDate.of(1993, 6, 28), "Female", 5));

            employeeRepository.saveAll(employees);
            log.info("Employees initialized");

            // Initialize Payments
            List<Payment> payments = new ArrayList<>();
            payments.add(new Payment(1, 2, new BigDecimal("65784.00"), LocalDateTime.of(2025, 1, 1, 13, 44, 12)));
            payments.add(new Payment(2, 4, new BigDecimal("62736.00"), LocalDateTime.of(2025, 1, 6, 18, 36, 37)));
            payments.add(new Payment(3, 1, new BigDecimal("69437.00"), LocalDateTime.of(2025, 1, 1, 10, 19, 21)));
            payments.add(new Payment(4, 3, new BigDecimal("67183.00"), LocalDateTime.of(2025, 1, 2, 17, 21, 57)));
            payments.add(new Payment(5, 2, new BigDecimal("66273.00"), LocalDateTime.of(2025, 2, 1, 11, 49, 15)));
            payments.add(new Payment(6, 5, new BigDecimal("71475.00"), LocalDateTime.of(2025, 1, 1, 7, 24, 14)));
            payments.add(new Payment(7, 1, new BigDecimal("70837.00"), LocalDateTime.of(2025, 2, 3, 19, 11, 31)));
            payments.add(new Payment(8, 6, new BigDecimal("69628.00"), LocalDateTime.of(2025, 1, 2, 10, 41, 15)));
            payments.add(new Payment(9, 4, new BigDecimal("71876.00"), LocalDateTime.of(2025, 2, 1, 12, 16, 47)));
            payments.add(new Payment(10, 3, new BigDecimal("70098.00"), LocalDateTime.of(2025, 2, 3, 10, 11, 17)));
            payments.add(new Payment(11, 6, new BigDecimal("67827.00"), LocalDateTime.of(2025, 2, 2, 19, 21, 27)));
            payments.add(new Payment(12, 5, new BigDecimal("69871.00"), LocalDateTime.of(2025, 2, 5, 17, 54, 17)));
            payments.add(new Payment(13, 2, new BigDecimal("72984.00"), LocalDateTime.of(2025, 3, 5, 9, 37, 35)));
            payments.add(new Payment(14, 1, new BigDecimal("67982.00"), LocalDateTime.of(2025, 3, 1, 6, 9, 51)));
            payments.add(new Payment(15, 6, new BigDecimal("70198.00"), LocalDateTime.of(2025, 3, 2, 10, 34, 35)));
            payments.add(new Payment(16, 4, new BigDecimal("74998.00"), LocalDateTime.of(2025, 3, 2, 9, 27, 26)));

            paymentRepository.saveAll(payments);
            log.info("Payments initialized");

            log.info("Database initialization completed successfully");

        } catch (Exception e) {
            log.error("Error initializing database: ", e);
        }
    }

}
