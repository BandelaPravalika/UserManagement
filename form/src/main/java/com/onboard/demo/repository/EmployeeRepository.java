package com.onboard.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onboard.demo.model.Employee;
import com.onboard.demo.model.Employee.EmployeeStatus;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmpId(String empId);

    Optional<Employee> findByOnboardingToken(String token);

    List<Employee> findByStatus(EmployeeStatus status);

    List<Employee> findByStatusAndEmpIdIsNullAndActivatedAtIsNotNull(EmployeeStatus status);

    @Query("""
        SELECT e FROM Employee e
        LEFT JOIN FETCH e.onboardingForm f
        LEFT JOIN FETCH f.educations
        LEFT JOIN FETCH f.internships
        LEFT JOIN FETCH f.workExperiences
        LEFT JOIN FETCH f.certifications
        LEFT JOIN FETCH f.identityProofs
        WHERE e.id = :id
    """)
    Optional<Employee> findFullEmployee(@Param("id") Long id);

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
}
