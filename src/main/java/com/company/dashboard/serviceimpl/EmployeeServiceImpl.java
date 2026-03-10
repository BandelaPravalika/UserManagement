package com.company.dashboard.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.dashboard.model.Employee;
import com.company.dashboard.model.Employee.EmployeeStatus;
import com.company.dashboard.model.EmployeeForm;
import com.company.dashboard.repository.EmployeeRepository;
import com.company.dashboard.response.EmployeeWithOnboardingDTO;
import com.company.dashboard.service.EmailService;
import com.company.dashboard.service.EmployeeCodeService;
import com.company.dashboard.service.EmployeeService;
import com.company.dashboard.service.OnboardingTokenService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmailService mailService;
    private final OnboardingTokenService onboardingTokenService;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final EmployeeCodeService employeeCodeService;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            EmailService mailService,
            OnboardingTokenService onboardingTokenService,
            ThreadPoolTaskScheduler taskScheduler,
            EmployeeCodeService employeeCodeService) {

        this.employeeRepository = employeeRepository;
        this.mailService = mailService;
        this.onboardingTokenService = onboardingTokenService;
        this.taskScheduler = taskScheduler;
        this.employeeCodeService = employeeCodeService;
    }

    // ---------------- CREATE EMPLOYEE ----------------
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

    // ---------------- GET COMPLETE EMPLOYEE ----------------
    @Override
    @Transactional(readOnly = true)
    public EmployeeWithOnboardingDTO getCompleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        EmployeeForm form = employee.getOnboardingForm();

        if (form != null) {
            Hibernate.initialize(form.getEducations());
            Hibernate.initialize(form.getCertifications());
            Hibernate.initialize(form.getInternships());
            Hibernate.initialize(form.getExperiences());
            Hibernate.initialize(form.getIdentityProofs());
        }

        return EmployeeWithOnboardingDTO.from(employee, form);
    }

    // ---------------- PATCH ----------------
    @Override
    public Employee patchEmployee(Long id, Map<String, Object> updates) {

        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        updates.forEach((field, value) -> {
            switch (field) {
                case "fullName":
                    existing.setFullName((String) value);
                    break;
                case "dept":
                    existing.setDept((String) value);
                    break;
                case "role":
                    existing.setRole((String) value);
                    break;
                case "entity":
                    existing.setEntity((String) value);
                    break;
                case "dateOfBirth":
                    existing.setDateOfBirth(LocalDate.parse((String) value));
                    break;
                case "dateOfInterview":
                    existing.setDateOfInterview(value != null ? LocalDate.parse((String) value) : null);
                    break;
                case "dateOfOnboarding":
                    existing.setDateOfOnboarding(LocalDate.parse((String) value));
                    break;
                case "email":
                    existing.setEmail((String) value);
                    break;
                case "phone":
                    existing.setPhone((String) value);
                    break;
                case "status":
                    existing.setStatus(EmployeeStatus.valueOf((String) value));
                    break;
                case "onboardingToken":
                    existing.setOnboardingToken((String) value);
                    break;
                case "activatedAt":
                    existing.setActivatedAt(
                            value != null ? LocalDate.parse((String) value).atStartOfDay() : null
                    );
                    break;
            }
        });

        return employeeRepository.save(existing);
    }

    // ---------------- UPDATE STATUS TO ACTIVE ----------------
    @Override
    public Employee updateEmployeeStatusToActive(Long id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setStatus(EmployeeStatus.ACTIVE);
        return employeeRepository.save(existing);
    }

    // ---------------- GET SINGLE ----------------
    @Override
    @Transactional(readOnly = true)
    public Employee getEmployee(Long id) {
        return employeeRepository.findFullEmployee(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // ---------------- GET ALL ----------------
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ---------------- DEACTIVATE ----------------
    @Override
    public void deactivateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setStatus(EmployeeStatus.INACTIVE);
        employeeRepository.save(employee);
    }

    // ---------------- ACTIVATE ----------------
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

    // ---------------- EMPLOYEE CODE GENERATION ----------------
    @Override
    public String generateEmployeeCode(Employee employee) {

        if (employee.getEmpId() != null) {
            return "Employee ID already exists";
        }

        // Schedule async generation
        employeeCodeService.scheduleEmployeeCode(employee);

        return "Employee ID generation scheduled successfully";
    }

    @Override
    public void generateEmployeeCodeAfterDelay(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getEmpId() == null) {
            String code = generateEmployeeCode(employee);
            employee.setEmpId(code);
            employeeRepository.save(employee);
        }
    }

    @Async
    @Override
    public void generateEmployeeCodeAfterDelayAsync(Long employeeId) {

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        generateEmployeeCodeAfterDelay(employeeId);
    }

    // ---------------- VERIFY TOKEN ----------------
    @Override
    public String verifyToken(String token) {

        Employee employee = employeeRepository
                .findByOnboardingToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        employee.setStatus(EmployeeStatus.UNDER_REVIEW);
        employee.setOnboardingForm(null);

        employeeRepository.save(employee);

        return "Onboarding submitted successfully";
    }
}