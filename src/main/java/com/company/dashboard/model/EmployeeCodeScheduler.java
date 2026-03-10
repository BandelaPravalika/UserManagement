//package com.company.dashboard.model;
//
//import java.util.List;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.company.dashboard.repository.EmployeeRepository;
//import com.company.dashboard.service.EmployeeCodeService;
//
//@Component
//public class EmployeeCodeScheduler {
//
//    private final EmployeeRepository employeeRepository;
//    private final EmployeeCodeService employeeCodeService;
//
//    public EmployeeCodeScheduler(EmployeeRepository employeeRepository,
//                                 EmployeeCodeService employeeCodeService) {
//        this.employeeRepository = employeeRepository;
//        this.employeeCodeService = employeeCodeService;
//    }
//
//    @Scheduled(fixedRate = 30000) // every 30 seconds
//    @Transactional
//    public void processEmployeeCodes() {
//    	List<Employee> employees = employeeRepository
//    		    .findByStatusAndEmpIdIsNullAndActivatedAtIsNotNull(Employee.EmployeeStatus.ACTIVE);
//    	for (Employee employee : employees) {
//    	    if (employeeCodeService.isEligibleForCode(employee)) {
//    	        String code = employeeCodeService.generateEmployeeCode(employee);
//    	        employee.setEmpId(code);
//    	        employeeRepository.save(employee);
//    	        System.out.println("Generated Employee Code: " + code);
//    	    }
//    	}
//    }
//}
