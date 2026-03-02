package com.company.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.IdentityProof;

public interface IdentityProofRepository extends JpaRepository<IdentityProof, Long> {

    // Check if ANY record is REJECTED for given employee
    boolean existsByEmployeeForm_Employee_IdAndStatus(
            Long employeeId,
            IdentityProof.ProofStatus status
    );

    // Count records that are NOT ACCEPTED for given employee
    long countByEmployeeForm_Employee_IdAndStatusNot(
            Long employeeId,
            IdentityProof.ProofStatus status
    );
    Optional<IdentityProof> findByEmployeeFormIdAndProofType(Long formId, String proofType);
}