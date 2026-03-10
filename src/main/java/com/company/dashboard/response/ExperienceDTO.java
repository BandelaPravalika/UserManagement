package com.company.dashboard.response;

import java.time.LocalDateTime;

import com.company.dashboard.model.Experience;
import com.company.dashboard.model.ProofStatus;

public class ExperienceDTO {

    private Long id;
    private String companyName;
    private Integer yearsOfExperience;
    private String offerLetterPath;
    private String relievingLetterPath;
    private String payslipsPath;
    private String experienceCertificatePath;
    private ProofStatus status;
    private String rejectionReason;
    private LocalDateTime reviewedAt;

    // Convert entity to DTO
    public static ExperienceDTO fromEntity(Experience experience) {
        if (experience == null) return null;

        ExperienceDTO dto = new ExperienceDTO();
        dto.setId(experience.getId());
        dto.setCompanyName(experience.getCompanyName());
        dto.setYearsOfExperience(experience.getYearsOfExperience());
        dto.setOfferLetterPath(experience.getOfferLetterPath());
        dto.setRelievingLetterPath(experience.getRelievingLetterPath());
        dto.setPayslipsPath(experience.getPayslipsPath());
        dto.setExperienceCertificatePath(experience.getExperienceCertificatePath());
        dto.setStatus(experience.getStatus());
        dto.setRejectionReason(experience.getRejectionReason());
        dto.setReviewedAt(experience.getReviewedAt());

        return dto;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public String getOfferLetterPath() { return offerLetterPath; }
    public void setOfferLetterPath(String offerLetterPath) { this.offerLetterPath = offerLetterPath; }

    public String getRelievingLetterPath() { return relievingLetterPath; }
    public void setRelievingLetterPath(String relievingLetterPath) { this.relievingLetterPath = relievingLetterPath; }

    public String getPayslipsPath() { return payslipsPath; }
    public void setPayslipsPath(String payslipsPath) { this.payslipsPath = payslipsPath; }

    public String getExperienceCertificatePath() { return experienceCertificatePath; }
    public void setExperienceCertificatePath(String experienceCertificatePath) { this.experienceCertificatePath = experienceCertificatePath; }

    public ProofStatus getStatus() { return status; }
    public void setStatus(ProofStatus status) { this.status = status; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}