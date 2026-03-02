package com.company.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.dashboard.model.Education;
import com.company.dashboard.model.Employee;

public interface EducationRepository extends JpaRepository<Education, Long> {

    // Check if ANY record is REJECTED for given employee
    boolean existsByEmployeeForm_Employee_IdAndStatus(
            Long employeeId,
            Education.ProofStatus status
    );

    // Count records that are NOT ACCEPTED for given employee
    long countByEmployeeForm_Employee_IdAndStatusNot(
            Long employeeId,
            Education.ProofStatus status
    );
    
//    List<Education> findByStatusAndEmployeeForm_EmployeeIsNullAndActivatedAtIsNotNull(Employee.EmployeeStatus status);
}