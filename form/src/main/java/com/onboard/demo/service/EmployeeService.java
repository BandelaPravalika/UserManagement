package com.onboard.demo.service;

import java.util.List;
import java.util.Map;

import com.onboard.demo.model.Employee;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    Employee patchEmployee(Long id, Map<String, Object> updates);

    Employee updateEmployeeStatusToActive(Long id);

    Employee activateEmployee(Long id);

    Employee getEmployee(Long id);

    List<Employee> getAllEmployees();

    void deactivateEmployee(Long id);

    String verifyToken(String token);

    EmployeeWithOnboardingDTO getCompleteEmployee(Long id);

    void generateEmployeeCodeAfterDelayAsync(Long employeeId);

    void generateEmployeeCodeAfterDelay(Long employeeId);

    String generateEmployeeCode(Employee employee);
}
