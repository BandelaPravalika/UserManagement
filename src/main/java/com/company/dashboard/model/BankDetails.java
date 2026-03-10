
package com.company.dashboard.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "bank_details")
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @NotBlank
    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @NotBlank
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{4}0[0-9]{6}$")
    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode;

    @Column(name = "upi_id")
    private String upiId;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private BankDocumentType documentType;

    @Column(name = "document_file_path", nullable = false)
    private String documentFilePath;

    @OneToOne
    @JoinColumn(name = "employee_form_id", nullable = false, unique = true)
    private EmployeeForm employeeForm;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProofStatus status = ProofStatus.PENDING;

    @Column(name = "rejection_reason", length = 1000)
    private String rejectionReason;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public BankDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(BankDocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentFilePath() {
        return documentFilePath;
    }

    public void setDocumentFilePath(String documentFilePath) {
        this.documentFilePath = documentFilePath;
    }

    public EmployeeForm getEmployeeForm() {
        return employeeForm;
    }

    public void setEmployeeForm(EmployeeForm employeeForm) {
        this.employeeForm = employeeForm;
    }

    public ProofStatus getStatus() {
        return status;
    }

    public void setStatus(ProofStatus status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}
