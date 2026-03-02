package com.company.dashboard.service;

import java.time.Instant;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.company.dashboard.model.Employee;

@Service
public class EmployeeCodeService {

    private final TaskScheduler taskScheduler;
    private final EmployeeCodeGeneratorService generatorService;

    public EmployeeCodeService(TaskScheduler taskScheduler,
                               EmployeeCodeGeneratorService generatorService) {
        this.taskScheduler = taskScheduler;
        this.generatorService = generatorService;
    }

    public void scheduleEmployeeCode(Employee employee) {

        taskScheduler.schedule(
                () -> generatorService.generateAndSaveEmployeeCode(employee.getId()),
                Instant.now().plusSeconds(60)
        );

        System.out.println("Employee ID generation scheduled.");
    }
}