package com.company.dashboard.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.company.dashboard.model.Employee;
import com.company.dashboard.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeActivationService {

    private final EmployeeRepository employeeRepository;

    public EmployeeActivationService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void activateEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getStatus() != Employee.EmployeeStatus.ACTIVE) {
            employee.setStatus(Employee.EmployeeStatus.ACTIVE);
            employee.setActivatedAt(LocalDateTime.now());
        }
        employeeRepository.save(employee);
    }
}
