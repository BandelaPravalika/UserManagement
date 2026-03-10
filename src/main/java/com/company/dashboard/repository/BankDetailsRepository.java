package com.company.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.BankDetails;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {

    Optional<BankDetails> findByEmployeeForm_Id(Long formId);
}
