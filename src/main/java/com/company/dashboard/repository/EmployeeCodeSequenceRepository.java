package com.company.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.EmployeeCodeSequence;

public interface EmployeeCodeSequenceRepository
extends JpaRepository<EmployeeCodeSequence, Long> {

Optional<EmployeeCodeSequence>
findByEntityAndMonthAndYearAndRole(
    String entity,
    String month,
    String year,
    String role
);
}
