package com.company.dashboard.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class OnboardingRequestDTO {

    private String fullName;
    private String email;
    private String phoneNumber;
//    private LocalDate dateOfBirth;
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

    private EducationDTO ssc;
    private EducationDTO intermediate;
    private EducationDTO graduation;
    private List<EducationDTO> postGraduations = new ArrayList<>();

    private List<CertificationDTO> otherCertificates = new ArrayList<>();
    private List<InternshipDTO> internships = new ArrayList<>();
    private List<WorkExperienceDTO> workExperiences = new ArrayList<>();

    private String bankName;
    private String branchName;
    private String accountNumber;
    private String ifscCode;
    private String upiId;
    private String passbookPath;
    private String photoPath;
    
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    // Identity proofs
    private IdentityProofDTO panProof;
    private IdentityProofDTO aadharProof;
    private IdentityProofDTO photoProof;
    private IdentityProofDTO passportProof;
    private IdentityProofDTO voterProof;

    // ───── Added for passbook proof ─────
    private IdentityProofDTO passbookProof;

    // ───── Getters & Setters ─────
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

    public EducationDTO getGraduation() { return graduation; }
    public void setGraduation(EducationDTO graduation) { this.graduation = graduation; }

    public List<EducationDTO> getPostGraduations() { return postGraduations; }
    public void setPostGraduations(List<EducationDTO> postGraduations) { this.postGraduations = postGraduations; }

    public List<CertificationDTO> getOtherCertificates() { return otherCertificates; }
    public void setOtherCertificates(List<CertificationDTO> otherCertificates) { this.otherCertificates = otherCertificates; }

    public List<InternshipDTO> getInternships() { return internships; }
    public void setInternships(List<InternshipDTO> internships) { this.internships = internships; }

    public List<WorkExperienceDTO> getWorkExperiences() { return workExperiences; }
    public void setWorkExperiences(List<WorkExperienceDTO> workExperiences) { this.workExperiences = workExperiences; }

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

    public String getPassbookPath() { return passbookPath; }
    public void setPassbookPath(String passbookPath) { this.passbookPath = passbookPath; }

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

    // ───── Added getter/setter for passbookProof ─────
    public IdentityProofDTO getPassbookProof() { return passbookProof; }
    public void setPassbookProof(IdentityProofDTO passbookProof) { this.passbookProof = passbookProof; }
}
