package com.company.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.Employee;
import com.company.dashboard.model.EmployeeForm;

public interface EmployeeFormRepository extends JpaRepository<EmployeeForm, Long> {

    Optional<EmployeeForm> findByEmployee_Id(Long employeeId);

    List<EmployeeForm> findByEmployee_StatusAndEmployee_ActivatedAtIsNotNull(
            Employee.EmployeeStatus status
    );
}