package com.company.dashboard.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.dashboard.model.Employee;
import com.company.dashboard.model.Employee.EmployeeStatus;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findTopByEntityAndRoleAndDateOfOnboardingBetweenOrderByEmpIdDesc(
            String entity,
            String role,
            LocalDate start,
            LocalDate end
    );

    @Query("""
    	    SELECT DISTINCT e FROM Employee e
    	    LEFT JOIN FETCH e.onboardingForm f
    	    LEFT JOIN FETCH f.educations
    	    LEFT JOIN FETCH f.internships
    	    LEFT JOIN FETCH f.experiences
    	    LEFT JOIN FETCH f.certifications
    	    LEFT JOIN FETCH f.identityProofs
    	    LEFT JOIN FETCH f.bankDetails
    	    WHERE e.id = :id
    	""")
    	Optional<Employee> findFullEmployee(@Param("id") Long id);

    Optional<Employee> findByEmpId(String empId);

    List<Employee> findByStatus(EmployeeStatus status);

    Optional<Employee> findByOnboardingToken(String token);

    @Query("""
        SELECT e FROM Employee e
        WHERE e.entity = :entity
        AND e.role = :role
        AND FUNCTION('MONTH', e.dateOfOnboarding) = :month
        AND FUNCTION('YEAR', e.dateOfOnboarding) = :year
        ORDER BY e.dateOfOnboarding DESC
    """)
    Employee findTopByEntityRoleAndOnboardingMonthYear(
            @Param("entity") String entity,
            @Param("role") String role,
            @Param("month") int month,
            @Param("year") int year
    );

    List<Employee> findByStatusAndEmpIdIsNullAndActivatedAtIsNotNull(EmployeeStatus status);

    // Used by scheduler: only employees activated at least 60 seconds ago
    @Query("""
        SELECT e FROM Employee e
        WHERE e.status = :status
        AND e.empId IS NULL
        AND e.activatedAt IS NOT NULL
        AND e.activatedAt <= :cutoff
    """)
    List<Employee> findEligibleForCodeGeneration(
            @Param("status") EmployeeStatus status,
            @Param("cutoff") LocalDateTime cutoff
    );

    // Fallback: find ALL active employees without a code (regardless of activatedAt)
    List<Employee> findByStatusAndEmpIdIsNull(EmployeeStatus status);

    @Query("""
        SELECT e FROM Employee e
        LEFT JOIN FETCH e.onboardingForm
        WHERE e.id = :id
    """)
    Optional<Employee> findCompleteById(@Param("id") Long id);
}