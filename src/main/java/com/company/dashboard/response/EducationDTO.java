package com.company.dashboard.response;

import java.time.LocalDateTime;

import com.company.dashboard.model.Education;
import com.company.dashboard.model.EducationType;
import com.company.dashboard.model.EmployeeForm;
import com.company.dashboard.model.ProofStatus;

public class EducationDTO {


private Long id;
private EducationType educationType;
private String institutionName;
private String hallTicketNumber;
private Integer passoutYear;
private Double percentage;
private String certificateFilePath;
private String marksMemoFilePath;
private ProofStatus status;
private String rejectionReason;
private LocalDateTime reviewedAt;

// ================= ENTITY → DTO =================
public static EducationDTO fromEntity(Education e) {

    if (e == null) return null;

    EducationDTO dto = new EducationDTO();

    dto.setId(e.getId());
    dto.setEducationType(e.getEducationType());
    dto.setInstitutionName(e.getInstitutionName());
    dto.setHallTicketNumber(e.getHallTicketNumber());
    dto.setPassoutYear(e.getPassoutYear());
    dto.setPercentage(e.getPercentage());
    dto.setCertificateFilePath(e.getCertificateFilePath());
    dto.setMarksMemoFilePath(e.getMarksMemoFilePath());
    dto.setStatus(e.getStatus());
    dto.setRejectionReason(e.getRejectionReason());
    dto.setReviewedAt(e.getReviewedAt());

    return dto;
}

// ================= DTO → ENTITY =================
public Education toEntity(EmployeeForm form) {

    Education e = new Education();

    e.setId(this.id);
    e.setEducationType(this.educationType);
    e.setInstitutionName(this.institutionName);
    e.setHallTicketNumber(this.hallTicketNumber);
    e.setPassoutYear(this.passoutYear);
    e.setPercentage(this.percentage);
    e.setCertificateFilePath(this.certificateFilePath);
    e.setMarksMemoFilePath(this.marksMemoFilePath);
    e.setStatus(this.status != null ? this.status : ProofStatus.PENDING);
    e.setRejectionReason(this.rejectionReason);
    e.setReviewedAt(this.reviewedAt);

    if (form != null) {
        e.setEmployeeForm(form);
    }

    return e;
}

// ================= GETTERS & SETTERS =================

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public EducationType getEducationType() {
    return educationType;
}

public void setEducationType(EducationType educationType) {
    this.educationType = educationType;
}

public String getInstitutionName() {
    return institutionName;
}

public void setInstitutionName(String institutionName) {
    this.institutionName = institutionName;
}

public String getHallTicketNumber() {
    return hallTicketNumber;
}

public void setHallTicketNumber(String hallTicketNumber) {
    this.hallTicketNumber = hallTicketNumber;
}

public Integer getPassoutYear() {
    return passoutYear;
}

public void setPassoutYear(Integer passoutYear) {
    this.passoutYear = passoutYear;
}

public Double getPercentage() {
    return percentage;
}

public void setPercentage(Double percentage) {
    this.percentage = percentage;
}

public String getCertificateFilePath() {
    return certificateFilePath;
}

public void setCertificateFilePath(String certificateFilePath) {
    this.certificateFilePath = certificateFilePath;
}

public String getMarksMemoFilePath() {
    return marksMemoFilePath;
}

public void setMarksMemoFilePath(String marksMemoFilePath) {
    this.marksMemoFilePath = marksMemoFilePath;
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
