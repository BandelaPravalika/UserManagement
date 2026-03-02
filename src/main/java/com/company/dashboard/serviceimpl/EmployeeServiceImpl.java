package com.company.dashboard.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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
    
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
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

        // 🔹 Force default status for new employee
        employee.setStatus(EmployeeStatus.ONBOARDING);

        // 1️⃣ Save employee FIRST
        Employee saved = employeeRepository.save(employee);

        // 2️⃣ Generate token using real ID
        String token = onboardingTokenService.generateToken(saved.getId());
        saved.setOnboardingToken(token); // optional: return token in response

        // 3️⃣ Send mail
        mailService.sendOnboardingMail(
                saved.getEmail(),
                saved.getFullName(),
                token
        );

        return saved;
    }




        @Override
    @Transactional(readOnly = true)
    public EmployeeWithOnboardingDTO getCompleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        EmployeeForm form = employee.getOnboardingForm();

        // This is the key fix – force Hibernate to initialize all child collections
        if (form != null) {
            Hibernate.initialize(form.getEducations());
            Hibernate.initialize(form.getCertifications());
            Hibernate.initialize(form.getInternships());
            Hibernate.initialize(form.getWorkExperiences());
            Hibernate.initialize(form.getIdentityProofs());
        }

        return EmployeeWithOnboardingDTO.from(employee, form);
    }

       // ---------------- PARTIAL UPDATE ----------------
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
                    existing.setStatus(Employee.EmployeeStatus.valueOf((String) value));
                    break;
                case "onboardingToken":
                    existing.setOnboardingToken((String) value);
                    break;
                case "activatedAt":
                    existing.setActivatedAt(
                        value != null ? LocalDate.parse((String) value).atStartOfDay() : null
                    );
                    break;
                case "onboardingForm":
                    // optional: handle nested update logic for EmployeeForm
                    break;
                default:
                    // ignore unknown fields
                    break;
            }
        });

        return employeeRepository.save(existing);
    }
    
    @Override
    public Employee updateEmployeeStatusToActive(Long id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setStatus(Employee.EmployeeStatus.ACTIVE);
        return employeeRepository.save(existing);
    }

    // ---------------- GET SINGLE EMPLOYEE ----------------
    @Override
    @Transactional(readOnly = true)
    public Employee getEmployee(Long id) {
        return employeeRepository.findFullEmployee(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // ---------------- GET ALL EMPLOYEES ----------------
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ---------------- DELETE EMPLOYEE ----------------
    @Override
    public void deactivateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Soft delete: just update status
        employee.setStatus(Employee.EmployeeStatus.INACTIVE);

        // Optional: also deactivate onboarding form if needed
        if (employee.getOnboardingForm() != null) {
            // You can choose to mark form inactive instead of deleting
            // e.g., employee.getOnboardingForm().setActive(false);
        }

        employeeRepository.save(employee); // no delete
    }




    // ---------------- ACTIVATE EMPLOYEE ----------------
    @Override
    public Employee activateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getStatus() != Employee.EmployeeStatus.UNDER_REVIEW) {
            throw new RuntimeException("Employee must be UNDER_REVIEW to activate");
        }

        employee.setStatus(Employee.EmployeeStatus.ACTIVE);
        employee.setActivatedAt(LocalDateTime.now());

        return employeeRepository.save(employee);
    }

   

    private LocalDateTime addWorkingDays(LocalDateTime localDateTime, int days) {
        LocalDateTime result = localDateTime;
        int addedDays = 0;

        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek().toString().equals("SATURDAY") ||
                  result.getDayOfWeek().toString().equals("SUNDAY"))) {
                addedDays++;
            }
        }

        return result;
    }

//    @Scheduled(cron = "0 0 2 * * ?")
//    public void checkEligibleEmployees() {
//        List<Employee> activeEmployees = employeeRepository.findByStatus(EmployeeStatus.ACTIVE);
//        activeEmployees.forEach(this::generateEmployeeCodeIfEligible);
//    }
//    
    
    @Async
    public void generateEmployeeCodeAfterDelayAsync(Long employeeId) {
        try {
            Thread.sleep(60000); // 1 minute delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getEmpId() == null) {
            String code = generateEmployeeCode(employee);
            employee.setEmpId(code);

            // Force save and flush immediately
            employeeRepository.saveAndFlush(employee);

            System.out.println("Generated Employee Code: " + code);
        }
    }

    // ---------------- VERIFY TOKEN ----------------
    @Override
    public String verifyToken(String token) {
        Employee employee = employeeRepository
                .findByOnboardingToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        employee.setStatus(Employee.EmployeeStatus.UNDER_REVIEW);
        employee.setOnboardingForm(null);

        employeeRepository.save(employee);

        return "Onboarding submitted successfully";
    }

	@Override
	public void generateEmployeeCodeAfterDelay(Long employeeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String generateEmployeeCode(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
