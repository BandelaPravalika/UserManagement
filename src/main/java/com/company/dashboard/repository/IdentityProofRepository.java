package com.company.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.IdentityProof;
import com.company.dashboard.model.ProofStatus;

public interface IdentityProofRepository extends JpaRepository<IdentityProof, Long> {

    // Check if ANY record is REJECTED for given employee
    boolean existsByEmployeeForm_Employee_IdAndStatus(Long employeeId, ProofStatus status);

    // Count records that are NOT ACCEPTED for given employee
    long countByEmployeeForm_Employee_IdAndStatusNot(Long employeeId, ProofStatus status);

    // Removed invalid method since 'proofType' does not exist in model
    // Optional<IdentityProof> findByEmployeeFormIdAndProofType(Long formId, String proofType);
}