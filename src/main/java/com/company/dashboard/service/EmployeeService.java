package com.company.dashboard.service;

import java.util.List;
import java.util.Map;
import com.company.dashboard.model.Employee;
import com.company.dashboard.response.EmployeeWithOnboardingDTO;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    Employee patchEmployee(Long id, Map<String, Object> updates);

    Employee updateEmployeeStatusToActive(Long id);

    Employee getEmployee(Long id);

    void generateEmployeeCodeAfterDelayAsync(Long employeeId);

    List<Employee> getAllEmployees();

    void deactivateEmployee(Long id);

    String generateEmployeeCode(Employee employee);

    void generateEmployeeCodeAfterDelay(Long employeeId);

    Employee activateEmployee(Long id);

    String verifyToken(String token);

    EmployeeWithOnboardingDTO getCompleteEmployee(Long id);
}