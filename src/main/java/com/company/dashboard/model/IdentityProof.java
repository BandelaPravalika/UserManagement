package com.company.dashboard.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "identity_proofs")
public class IdentityProof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^$|[A-Z]{5}[0-9]{4}[A-Z]$", message = "Invalid PAN format")
    private String panNumber;

    @Pattern(regexp = "^$|[0-9]{12}$", message = "Invalid Aadhaar format")
    private String aadhaarNumber;
    
    private String panFilePath;

    private String aadhaarFilePath;

    private String passportFilePath;
    private String voterIdFilePath;

    private String photoFilePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "employee_form_id", nullable = false)
    private EmployeeForm employeeForm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProofStatus status = ProofStatus.PENDING;

    @Column(length = 1000)
    private String rejectionReason;

    private LocalDateTime reviewedAt;

    // ---------------- Getters & Setters ----------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }

    public String getPanFilePath() { return panFilePath; }
    public void setPanFilePath(String panFilePath) { this.panFilePath = panFilePath; }

    public String getAadhaarNumber() { return aadhaarNumber; }
    public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }

    public String getAadhaarFilePath() { return aadhaarFilePath; }
    public void setAadhaarFilePath(String aadhaarFilePath) { this.aadhaarFilePath = aadhaarFilePath; }

    public String getPassportFilePath() { return passportFilePath; }
    public void setPassportFilePath(String passportFilePath) { this.passportFilePath = passportFilePath; }

    public String getVoterIdFilePath() { return voterIdFilePath; }
    public void setVoterIdFilePath(String voterIdFilePath) { this.voterIdFilePath = voterIdFilePath; }

    public String getPhotoFilePath() { return photoFilePath; }
    public void setPhotoFilePath(String photoFilePath) { this.photoFilePath = photoFilePath; }

    public EmployeeForm getEmployeeForm() { return employeeForm; }
    public void setEmployeeForm(EmployeeForm employeeForm) { this.employeeForm = employeeForm; }

    public ProofStatus getStatus() { return status; }
    public void setStatus(ProofStatus status) { this.status = status; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }

    // ---------------- Cleaning before persist/update ----------------

    @PrePersist
    @PreUpdate
    private void cleanFields() {
        if (panNumber != null && !panNumber.isEmpty()) 
            panNumber = panNumber.replaceAll("\\s+", "").toUpperCase();
        if (aadhaarNumber != null && !aadhaarNumber.isEmpty()) 
            aadhaarNumber = aadhaarNumber.replaceAll("\\s+", "");
    }
}