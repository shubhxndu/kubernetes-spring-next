package com.example.employeeapi.controller;

import com.example.employeeapi.model.Employee;
import com.example.employeeapi.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private static final DateTimeFormatter IST_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

    @Autowired
    private EmployeeService employeeService;

    private String getCurrentISTTimestamp() {
        return ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).format(IST_FORMATTER);
    }

    @GetMapping("/ping")
    public String ping() {
        logger.info("[{}] Received GET request to /ping", getCurrentISTTimestamp());
        String response = "pong ping pong";
        logger.info("[{}] Responding to /ping with: {}", getCurrentISTTimestamp(), response);
        return response;
    }

    @GetMapping("/")
    public String helloService() {
        logger.info("[{}] Received GET request to /", getCurrentISTTimestamp());
        String response = "hello";
        logger.info("[{}] Responding to / with: {}", getCurrentISTTimestamp(), response);
        return response;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("[{}] Received GET request to /employees", getCurrentISTTimestamp());
        List<Employee> employees = employeeService.getAllEmployees();
        logger.info("[{}] Responding to /employees with {} employees, Status: OK", getCurrentISTTimestamp(),
                employees.size());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/employees/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        logger.info("[{}] Received POST request to /addEmployee with employee data: {}", getCurrentISTTimestamp(),
                employee);
        try {
            Employee savedEmployee = employeeService.saveEmployee(employee);
            logger.info("[{}] Successfully added employee with ID: {}, Status: CREATED", getCurrentISTTimestamp(),
                    savedEmployee.getId());
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("[{}] Failed to add employee: {}, Status: BAD_REQUEST", getCurrentISTTimestamp(),
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        logger.info("[{}] Received DELETE request to /employees/{}", getCurrentISTTimestamp(), id);
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            logger.info("[{}] Successfully deleted employee with ID: {}, Status: OK", getCurrentISTTimestamp(), id);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } else {
            logger.warn("[{}] Employee with ID: {} not found, Status: NOT_FOUND", getCurrentISTTimestamp(), id);
            return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        logger.info("[{}] Received GET request to /employees/{}", getCurrentISTTimestamp(), id);
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            logger.info("[{}] Found employee with ID: {}, Status: OK", getCurrentISTTimestamp(), id);
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            logger.warn("[{}] Employee with ID: {} not found, Status: NOT_FOUND", getCurrentISTTimestamp(), id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}