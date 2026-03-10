package com.company.dashboard.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OnboardingRequestDTO {

    private String fullName;
    private String email;
    private String phoneNumber;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String bloodGroup;
    private String permanentAddress;
    private String presentAddress;
    private String fathersName;
    private String fathersPhone;
    private String mothersName;
    private String mothersPhone;
    private String emergencyContactName;
    private String emergencyRelationship;
    private String emergencyNumber;

    // ===== EDUCATION =====
    private EducationDTO ssc;                     // Single certificate
    private EducationDTO intermediate;            // Single certificate

    // Graduations: dynamic list, each with certificate/photo
    private List<EducationDTO> graduations = new ArrayList<>();

    // PostGraduations: dynamic list
    private List<EducationDTO> postGraduations = new ArrayList<>();

    // ===== CERTIFICATIONS / EXPERIENCE =====
    private List<CertificationDTO> otherCertificates = new ArrayList<>();

    // ===== BANK =====
    private BankDetailsDTO bankDetails;

    // ===== IDENTITY =====
    private IdentityProofDTO panProof;
    private IdentityProofDTO aadharProof;
    private IdentityProofDTO photoProof;
    private IdentityProofDTO passportProof;
    private IdentityProofDTO voterProof;
    private IdentityProofDTO passbookProof;

    private List<InternshipDTO> internships;
    private List<ExperienceDTO> workExperiences;

    // ================= GETTERS & SETTERS =================
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }
    public String getPresentAddress() { return presentAddress; }
    public void setPresentAddress(String presentAddress) { this.presentAddress = presentAddress; }
    public String getFathersName() { return fathersName; }
    public void setFathersName(String fathersName) { this.fathersName = fathersName; }
    public String getFathersPhone() { return fathersPhone; }
    public void setFathersPhone(String fathersPhone) { this.fathersPhone = fathersPhone; }
    public String getMothersName() { return mothersName; }
    public void setMothersName(String mothersName) { this.mothersName = mothersName; }
    public String getMothersPhone() { return mothersPhone; }
    public void setMothersPhone(String mothersPhone) { this.mothersPhone = mothersPhone; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public String getEmergencyRelationship() { return emergencyRelationship; }
    public void setEmergencyRelationship(String emergencyRelationship) { this.emergencyRelationship = emergencyRelationship; }
    public String getEmergencyNumber() { return emergencyNumber; }
    public void setEmergencyNumber(String emergencyNumber) { this.emergencyNumber = emergencyNumber; }

    public EducationDTO getSsc() { return ssc; }
    public void setSsc(EducationDTO ssc) { this.ssc = ssc; }
    public EducationDTO getIntermediate() { return intermediate; }
    public void setIntermediate(EducationDTO intermediate) { this.intermediate = intermediate; }

    public List<EducationDTO> getGraduations() { return graduations; }
    public void setGraduations(List<EducationDTO> graduations) { this.graduations = graduations; }

    public List<EducationDTO> getPostGraduations() { return postGraduations; }
    public void setPostGraduations(List<EducationDTO> postGraduations) { this.postGraduations = postGraduations; }

    public List<CertificationDTO> getOtherCertificates() { return otherCertificates; }
    public void setOtherCertificates(List<CertificationDTO> otherCertificates) { this.otherCertificates = otherCertificates; }

    public BankDetailsDTO getBankDetails() { return bankDetails; }
    public void setBankDetails(BankDetailsDTO bankDetails) { this.bankDetails = bankDetails; }

    public IdentityProofDTO getPanProof() { return panProof; }
    public void setPanProof(IdentityProofDTO panProof) { this.panProof = panProof; }
    public IdentityProofDTO getAadharProof() { return aadharProof; }
    public void setAadharProof(IdentityProofDTO aadharProof) { this.aadharProof = aadharProof; }
    public IdentityProofDTO getPhotoProof() { return photoProof; }
    public void setPhotoProof(IdentityProofDTO photoProof) { this.photoProof = photoProof; }
    public IdentityProofDTO getPassportProof() { return passportProof; }
    public void setPassportProof(IdentityProofDTO passportProof) { this.passportProof = passportProof; }
    public IdentityProofDTO getVoterProof() { return voterProof; }
    public void setVoterProof(IdentityProofDTO voterProof) { this.voterProof = voterProof; }
    public IdentityProofDTO getPassbookProof() { return passbookProof; }
    public void setPassbookProof(IdentityProofDTO passbookProof) { this.passbookProof = passbookProof; }

    public List<InternshipDTO> getInternships() { return internships; }
    public void setInternships(List<InternshipDTO> internships) { this.internships = internships; }

    public List<ExperienceDTO> getWorkExperiences() { return workExperiences; }
    public void setWorkExperiences(List<ExperienceDTO> workExperiences) { this.workExperiences = workExperiences; }

    @Override
    public String toString() {
        return "OnboardingRequestDTO{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", ssc=" + ssc +
                ", intermediate=" + intermediate +
                ", graduations=" + graduations +
                ", postGraduations=" + postGraduations +
                ", otherCertificates=" + otherCertificates +
                '}';
    }
}