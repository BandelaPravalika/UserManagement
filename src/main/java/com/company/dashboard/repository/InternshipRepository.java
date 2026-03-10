package com.company.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.Internship;
import com.company.dashboard.model.ProofStatus;

public interface InternshipRepository extends JpaRepository<Internship, Long> {

    boolean existsByEmployeeForm_Employee_IdAndStatus(
            Long employeeId,
            ProofStatus status
    );

    long countByEmployeeForm_Employee_IdAndStatusNot(
            Long employeeId,
            ProofStatus status
    );
}