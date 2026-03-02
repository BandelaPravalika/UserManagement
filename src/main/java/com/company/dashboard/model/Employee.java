package com.company.dashboard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(
    name = "employees",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "empId"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")
    }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // internal DB ID

    @Column(name = "emp_id", unique = true,  length = 100)
    private String empId; // unique employee code for frontend

    @NotBlank
    private String fullName;

    @NotBlank
    private String dept;

    @NotBlank
    private String role;

    @NotBlank
    private String entity;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    private LocalDate dateOfInterview;

    @NotNull
    private LocalDate dateOfOnboarding;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{10}")
    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus status = EmployeeStatus.ONBOARDING;

    @Column(unique = true)
    private String onboardingToken;

    @OneToOne(mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private EmployeeForm onboardingForm;

    private LocalDateTime activatedAt;

    @ElementCollection
    @CollectionTable(name = "employee_rejected_documents", 
                     joinColumns = @JoinColumn(name = "employee_id"))
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

    // ===== SAFE RELATION SETTER =====
    public void setOnboardingForm(EmployeeForm onboardingForm) {
        this.onboardingForm = onboardingForm;
        if (onboardingForm != null) {
            onboardingForm.setEmployee(this);
        }
    }

    // ===== ENUM =====
    public enum EmployeeStatus {
        ONBOARDING,
        UNDER_REVIEW,
        ACTIVE,
        INACTIVE
    }

    // ===== GETTERS & SETTERS =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDept() { return dept; }
    public void setDept(String dept) { this.dept = dept; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEntity() { return entity; }
    public void setEntity(String entity) { this.entity = entity; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public LocalDate getDateOfInterview() { return dateOfInterview; }
    public void setDateOfInterview(LocalDate dateOfInterview) { this.dateOfInterview = dateOfInterview; }

    public LocalDate getDateOfOnboarding() { return dateOfOnboarding; }
    public void setDateOfOnboarding(LocalDate dateOfOnboarding) { this.dateOfOnboarding = dateOfOnboarding; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public EmployeeStatus getStatus() { return status; }
    public void setStatus(EmployeeStatus status) { this.status = status; }

    public String getOnboardingToken() { return onboardingToken; }
    public void setOnboardingToken(String onboardingToken) { this.onboardingToken = onboardingToken; }

    public EmployeeForm getOnboardingForm() { return onboardingForm; }

    public LocalDateTime getActivatedAt() { return activatedAt; }
    public void setActivatedAt(LocalDateTime activatedAt) { this.activatedAt = activatedAt; }

    public List<String> getRejectedDocuments() { return rejectedDocuments; }
    public void setRejectedDocuments(List<String> rejectedDocuments) { this.rejectedDocuments = rejectedDocuments; }

    public String getReviewRemarks() { return reviewRemarks; }
    public void setReviewRemarks(String reviewRemarks) { this.reviewRemarks = reviewRemarks; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

}