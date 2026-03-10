package com.company.dashboard.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.Certification;
import com.company.dashboard.model.ProofStatus;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    boolean existsByEmployeeForm_Employee_IdAndStatus(
            Long employeeId,
            ProofStatus status
    );

    long countByEmployeeForm_Employee_IdAndStatusNot(
            Long employeeId,
            ProofStatus status
    );
}