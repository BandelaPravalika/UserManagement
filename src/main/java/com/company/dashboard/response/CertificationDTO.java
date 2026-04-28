package com.company.dashboard.response;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.Certification;
import com.company.dashboard.model.ProofStatus;
import com.company.dashboard.util.FileUrlUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CertificationDTO {

    private Long id;
    private String instituteName;
    private String certificateNumber;

    // Only the saved path goes here
    private String certificateFilePath;

    private ProofStatus status;
    private String rejectionReason;
    private LocalDateTime reviewedAt;

    // Convert entity → DTO
    public static CertificationDTO fromEntity(Certification cert) {
        if (cert == null) return null;
        CertificationDTO dto = new CertificationDTO();
        dto.setId(cert.getId());
        dto.setInstituteName(cert.getInstituteName());
        dto.setCertificateNumber(cert.getCertificateNumber());
        dto.setCertificateFilePath(FileUrlUtil.ensurePrefix(cert.getCertificateFilePath())); // only string path
        dto.setStatus(cert.getStatus());
        dto.setRejectionReason(cert.getRejectionReason());
        dto.setReviewedAt(cert.getReviewedAt());
        return dto;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getInstituteName() { return instituteName; }
    public void setInstituteName(String instituteName) { this.instituteName = instituteName; }
    public String getCertificateNumber() { return certificateNumber; }
    public void setCertificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; }
    public String getCertificateFilePath() { return certificateFilePath; }
    public void setCertificateFilePath(String certificateFilePath) { this.certificateFilePath = certificateFilePath; }
    public ProofStatus getStatus() { return status; }
    public void setStatus(ProofStatus status) { this.status = status; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}