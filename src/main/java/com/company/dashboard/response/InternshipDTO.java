package com.company.dashboard.response;

import java.time.LocalDate;

public class InternshipDTO {
    private String companyName;
    private LocalDate joiningDate;
    private LocalDate relievingDate;
    private String internshipId;
    private String duration;
    private String offerLetterPath;
    private String experienceCertificatePath;

    // Getters (you already have these)
    public String getCompanyName() { return companyName; }
    public LocalDate getJoiningDate() { return joiningDate; }
    public LocalDate getRelievingDate() { return relievingDate; }
    public String getInternshipId() { return internshipId; }
    public String getDuration() { return duration; }
    public String getOfferLetterPath() { return offerLetterPath; }
    public String getExperienceCertificatePath() { return experienceCertificatePath; }

    // Missing setter – ADD THIS
    public void setExperienceCertificatePath(String experienceCertificatePath) {
        this.experienceCertificatePath = experienceCertificatePath;
    }

    // Also add setters for all other fields if you want consistency
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }
    public void setRelievingDate(LocalDate relievingDate) { this.relievingDate = relievingDate; }
    public void setInternshipId(String internshipId) { this.internshipId = internshipId; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setOfferLetterPath(String offerLetterPath) { this.offerLetterPath = offerLetterPath; }
}