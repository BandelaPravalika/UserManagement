package com.company.dashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.dashboard.model.Employee;
import com.company.dashboard.model.EmployeeCodeSequence;
import com.company.dashboard.repository.EmployeeCodeSequenceRepository;
import com.company.dashboard.repository.EmployeeRepository;

@Service
public class EmployeeCodeGeneratorService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeCodeSequenceRepository sequenceRepository;

    public EmployeeCodeGeneratorService(EmployeeRepository employeeRepository,
                                        EmployeeCodeSequenceRepository sequenceRepository) {
        this.employeeRepository = employeeRepository;
        this.sequenceRepository = sequenceRepository;
    }

    @Transactional
    public void generateAndSaveEmployeeCode(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Prevent duplicate generation
        if (employee.getEmpId() != null) {
            System.out.println("Employee ID already exists.");
            return;
        }

        String entity = employee.getEntity().toUpperCase();
        String role = employee.getRole().toUpperCase();
        String month = String.format("%02d",
                employee.getDateOfOnboarding().getMonthValue());
        String year = String.valueOf(
                employee.getDateOfOnboarding().getYear()).substring(2);

        EmployeeCodeSequence sequence = sequenceRepository
                .findByEntityAndMonthAndYearAndRole(entity, month, year, role)
                .orElseGet(() -> {
                    EmployeeCodeSequence newSeq = new EmployeeCodeSequence();
                    newSeq.setEntity(entity);
                    newSeq.setMonth(month);
                    newSeq.setYear(year);
                    newSeq.setRole(role);
                    newSeq.setLastSequence(0L);
                    return sequenceRepository.save(newSeq);
                });

        Long nextSeq = sequence.getLastSequence() + 1;
        sequence.setLastSequence(nextSeq);
        sequenceRepository.save(sequence);

        String empId = entity + month + year + role +
                String.format("%05d", nextSeq);
        employee.setEmpId(empId);
        employeeRepository.save(employee);
        employeeRepository.flush();
        Employee check = employeeRepository.findById(employeeId).get();
        System.out.println("After Save EmpID = " + check.getEmpId());

        
        System.out.println("EMPLOYEE ID SAVED: " + empId);
    }
}