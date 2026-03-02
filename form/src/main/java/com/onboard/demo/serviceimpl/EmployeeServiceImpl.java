package com.onboard.demo.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.dashboard.response.EmployeeWithOnboardingDTO;
import com.company.dashboard.service.*;
import com.onboard.demo.model.Employee;
import com.onboard.demo.model.Employee.EmployeeStatus;
import com.onboard.demo.repository.EmployeeRepository;
import com.onboard.demo.service.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmailService mailService;
    private final OnboardingTokenService onboardingTokenService;
    private final EmployeeCodeService employeeCodeService;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            EmailService mailService,
            OnboardingTokenService onboardingTokenService,
            EmployeeCodeService employeeCodeService) {
        this.employeeRepository = employeeRepository;
        this.mailService = mailService;
        this.onboardingTokenService = onboardingTokenService;
        this.employeeCodeService = employeeCodeService;
    }

    @Override
    public Employee createEmployee(Employee employee) {

        employee.setStatus(EmployeeStatus.ONBOARDING);

        Employee saved = employeeRepository.save(employee);

        String token = onboardingTokenService.generateToken(saved.getId());
        saved.setOnboardingToken(token);

        mailService.sendOnboardingMail(
                saved.getEmail(),
                saved.getFullName(),
                token
        );

        return saved;
    }

    @Override
    public String verifyToken(String token) {

        Employee employee = employeeRepository
                .findByOnboardingToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (employee.getStatus() != EmployeeStatus.ONBOARDING) {
            throw new RuntimeException("Onboarding already submitted");
        }

        employee.setStatus(EmployeeStatus.UNDER_REVIEW);
        employee.setOnboardingToken(null);

        employeeRepository.save(employee);

        return "Onboarding submitted successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeWithOnboardingDTO getCompleteEmployee(Long id) {

        Employee employee = employeeRepository.findFullEmployee(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return EmployeeWithOnboardingDTO.from(
                employee,
                employee.getOnboardingForm()
        );
    }

    @Override
    public Employee patchEmployee(Long id, Map<String, Object> updates) {

        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        updates.forEach((field, value) -> {
            switch (field) {
                case "fullName" -> existing.setFullName((String) value);
                case "dept" -> existing.setDept((String) value);
                case "role" -> existing.setRole((String) value);
                case "entity" -> existing.setEntity((String) value);
                case "dateOfBirth" -> existing.setDateOfBirth(LocalDate.parse((String) value));
                case "dateOfOnboarding" -> existing.setDateOfOnboarding(LocalDate.parse((String) value));
                case "email" -> existing.setEmail((String) value);
                case "phone" -> existing.setPhone((String) value);
                case "status" -> existing.setStatus(EmployeeStatus.valueOf((String) value));
            }
        });

        return employeeRepository.save(existing);
    }

    @Override
    public Employee activateEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getStatus() != EmployeeStatus.UNDER_REVIEW) {
            throw new RuntimeException("Employee must be UNDER_REVIEW to activate");
        }

        employee.setStatus(EmployeeStatus.ACTIVE);
        employee.setActivatedAt(LocalDateTime.now());

        return employeeRepository.save(employee);
    }

    @Override
    public void deactivateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setStatus(EmployeeStatus.INACTIVE);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Async
    @Override
    public void generateEmployeeCodeAfterDelayAsync(Long employeeId) {

        try { Thread.sleep(60000); } 
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getEmpId() == null) {
            String code = generateEmployeeCode(employee);
            employee.setEmpId(code);
            employeeRepository.saveAndFlush(employee);
        }
    }

    @Override
    public void generateEmployeeCodeAfterDelay(Long employeeId) {
        generateEmployeeCodeAfterDelayAsync(employeeId);
    }

    @Override
    public String generateEmployeeCode(Employee employee) {
        return employeeCodeService.generateEmployeeCode(employee);
    }

    @Override
    public Employee updateEmployeeStatusToActive(Long id) {
        return activateEmployee(id);
    }
}
