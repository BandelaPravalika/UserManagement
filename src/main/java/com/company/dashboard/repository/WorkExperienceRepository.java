package com.company.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.company.dashboard.model.WorkExperience;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {

    // Check if ANY record is REJECTED for given employee
    boolean existsByEmployeeForm_Employee_IdAndStatus(
            Long employeeId,
            WorkExperience.ProofStatus status
    );

    // Count records that are NOT ACCEPTED for given employee
    long countByEmployeeForm_Employee_IdAndStatusNot(
            Long employeeId,
            WorkExperience.ProofStatus status
    );

}