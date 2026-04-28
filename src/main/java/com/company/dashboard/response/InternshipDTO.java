package com.company.dashboard.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.Internship;
import com.company.dashboard.model.ProofStatus;
import com.company.dashboard.util.FileUrlUtil;

public class InternshipDTO {

    private Long id;
    private String companyName;
    private LocalDate joiningDate;
    private LocalDate relievingDate;
    private String internshipId;
    private String duration;
    private String offerLetterPath;            // DB path
    private String experienceCertificatePath;  // DB path

    private MultipartFile offerLetter;         // Upload
    private MultipartFile experienceCertificate; // Upload

    private ProofStatus status;
    private String rejectionReason;
    private LocalDateTime reviewedAt;

    // ================= ENTITY → DTO =================
    public static InternshipDTO fromEntity(Internship internship) {
        if (internship == null) return null;

        InternshipDTO dto = new InternshipDTO();
        dto.setId(internship.getId());
        dto.setCompanyName(internship.getCompanyName());
        dto.setJoiningDate(internship.getJoiningDate());
        dto.setRelievingDate(internship.getRelievingDate());
        dto.setInternshipId(internship.getInternshipId());
        dto.setDuration(internship.getDuration());
        dto.setOfferLetterPath(FileUrlUtil.ensurePrefix(internship.getOfferLetterPath()));
        dto.setExperienceCertificatePath(FileUrlUtil.ensurePrefix(internship.getExperienceCertificatePath()));
        dto.setStatus(internship.getStatus());
        dto.setRejectionReason(internship.getRejectionReason());
        dto.setReviewedAt(internship.getReviewedAt());

        return dto;
    }

    // ================= GETTERS & SETTERS =================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }

    public LocalDate getRelievingDate() { return relievingDate; }
    public void setRelievingDate(LocalDate relievingDate) { this.relievingDate = relievingDate; }

    public String getInternshipId() { return internshipId; }
    public void setInternshipId(String internshipId) { this.internshipId = internshipId; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getOfferLetterPath() { return offerLetterPath; }
    public void setOfferLetterPath(String offerLetterPath) { this.offerLetterPath = offerLetterPath; }

    public String getExperienceCertificatePath() { return experienceCertificatePath; }
    public void setExperienceCertificatePath(String experienceCertificatePath) { this.experienceCertificatePath = experienceCertificatePath; }

    public MultipartFile getOfferLetter() { return offerLetter; }
    public void setOfferLetter(MultipartFile offerLetter) { this.offerLetter = offerLetter; }

    public MultipartFile getExperienceCertificate() { return experienceCertificate; }
    public void setExperienceCertificate(MultipartFile experienceCertificate) { this.experienceCertificate = experienceCertificate; }

    public ProofStatus getStatus() { return status; }
    public void setStatus(ProofStatus status) { this.status = status; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}