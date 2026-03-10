package com.company.dashboard.response;

import java.time.LocalDateTime;

import com.company.dashboard.model.BankDetails;
import com.company.dashboard.model.BankDocumentType;
import com.company.dashboard.model.ProofStatus;

public class BankDetailsDTO {

    private Long id;
    private String bankName;
    private String branchName;
    private String accountNumber;
    private String ifscCode;
    private String upiId;
    private BankDocumentType documentType;

    // File path stored in DB
    private String documentFilePath;

    private ProofStatus status;
    private String rejectionReason;
    private LocalDateTime reviewedAt;

    // Convert entity to DTO
    public static BankDetailsDTO fromEntity(BankDetails bank) {
        if (bank == null) return null;

        BankDetailsDTO dto = new BankDetailsDTO();
        dto.setId(bank.getId());
        dto.setBankName(bank.getBankName());
        dto.setBranchName(bank.getBranchName());
        dto.setAccountNumber(bank.getAccountNumber());
        dto.setIfscCode(bank.getIfscCode());
        dto.setUpiId(bank.getUpiId());
        dto.setDocumentType(bank.getDocumentType());
        dto.setDocumentFilePath(bank.getDocumentFilePath()); // String path
        dto.setStatus(bank.getStatus());
        dto.setRejectionReason(bank.getRejectionReason());
        dto.setReviewedAt(bank.getReviewedAt());

        return dto;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getUpiId() { return upiId; }
    public void setUpiId(String upiId) { this.upiId = upiId; }

    public BankDocumentType getDocumentType() { return documentType; }
    public void setDocumentType(BankDocumentType documentType) { this.documentType = documentType; }

    public String getDocumentFilePath() { return documentFilePath; }
    public void setDocumentFilePath(String documentFilePath) { this.documentFilePath = documentFilePath; }

    public ProofStatus getStatus() { return status; }
    public void setStatus(ProofStatus status) { this.status = status; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}