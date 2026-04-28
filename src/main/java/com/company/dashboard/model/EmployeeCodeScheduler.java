package com.company.dashboard.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.dashboard.model.Employee.EmployeeStatus;
import com.company.dashboard.repository.EmployeeRepository;
import com.company.dashboard.service.EmployeeCodeGeneratorService;

@Component
public class EmployeeCodeScheduler {

    private final EmployeeRepository employeeRepository;
    private final EmployeeCodeGeneratorService generatorService;

    public EmployeeCodeScheduler(EmployeeRepository employeeRepository,
                                 EmployeeCodeGeneratorService generatorService) {
        this.employeeRepository = employeeRepository;
        this.generatorService = generatorService;
    }

    /**
     * Runs every 30 seconds.
     * Generates employee codes ONLY for ACTIVE employees:
     *   - whose empId is still null (not yet generated)
     *   - who were activated at least 60 seconds ago
     *
     * This guarantees the 1-minute delay after activation is always respected.
     * By default, employees are created with status ONBOARDING and empId = null.
     * The empId is only assigned after activation + 1 minute.
     */
    @Scheduled(fixedDelay = 30000) // check every 30 seconds for precision
    @Transactional
    public void processEmployeeCodes() {

        // Only pick employees whose activatedAt was at least 60 seconds ago
        LocalDateTime cutoff = LocalDateTime.now().minusSeconds(60);

        List<Employee> employees = employeeRepository
                .findEligibleForCodeGeneration(EmployeeStatus.ACTIVE, cutoff);

        if (employees.isEmpty()) {
            return;
        }

        System.out.println("[EmployeeCodeScheduler] Found " + employees.size()
                + " employee(s) eligible for code generation (activated >= 1 min ago).");

        for (Employee employee : employees) {
            try {
                generatorService.generateAndSaveEmployeeCode(employee.getId());
                System.out.println("[EmployeeCodeScheduler] Generated empId for: "
                        + employee.getFullName() + " (activated at: " + employee.getActivatedAt() + ")");
            } catch (Exception e) {
                System.err.println("[EmployeeCodeScheduler] Failed for employee ID: "
                        + employee.getId() + " - " + e.getMessage());
            }
        }
    }
}
