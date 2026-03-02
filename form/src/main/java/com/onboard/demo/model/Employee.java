package com.onboard.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.processing.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;

@Entity
@Table(
    name = "employees",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "emp_id"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")
    }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_id", unique = true, length = 100)
    private String empId;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Column(nullable = false)
    private String dept;

    @NotBlank
    @Column(nullable = false)
    private String role;

    @NotBlank
    @Column(nullable = false)
    private String entity;

    @NotNull
    @Past
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    private LocalDate dateOfInterview;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfOnboarding;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{10}")
    @Column(unique = true, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus status = EmployeeStatus.ONBOARDING;

    @Column(unique = true)
    private String onboardingToken;

    @OneToOne(mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private EmployeeForm onboardingForm;

    private LocalDateTime activatedAt;

    @ElementCollection
    @CollectionTable(
        name = "employee_rejected_documents",
        joinColumns = @JoinColumn(name = "employee_id")
    )
    @Column(name = "document_name")
    private List<String> rejectedDocuments;

    @Column(length = 1000)
    private String reviewRemarks;

    private LocalDateTime reviewedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setOnboardingForm(EmployeeForm onboardingForm) {
        this.onboardingForm = onboardingForm;
        if (onboardingForm != null) {
            onboardingForm.setEmployee(this);
        }
    }

    public enum EmployeeStatus {
        ONBOARDING,
        UNDER_REVIEW,
        ACTIVE,
        INACTIVE
    }

    // ===== GETTERS & SETTERS =====
    // (keep your existing getters and setters exactly as before)
}