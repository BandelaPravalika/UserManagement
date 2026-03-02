package com.company.dashboard.response;

import java.time.LocalDate;
import java.util.List;

public class OnboardingResponseDTO {
    // Basic employee info
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private String permanentAddress;
    private String presentAddress;
    
    
    private String empId;
    
    public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	// Family & emergency
    private String fathersName;
    private String fathersPhone;
    private String mothersName;
    private String mothersPhone;
    private String emergencyContactName;
    private String emergencyRelationship;
    private String emergencyNumber;

    // Bank details
    private String bankName;
    private String branchName;
    private String accountNumber;
    private String ifscCode;
    private String upiId;

    // File paths – THIS IS WHAT WAS MISSING
    private String passbookPath;            // already there
    private String photoPath;               // passport size photo
    private String panPath;                 // PAN card
    private String aadharPath;              // Aadhar card
    private String voterPath;               // Voter ID
    private String passportPath;            // Passport document
    private String sscCertificatePath;      // SSC certificate
    private String sscMarksPath;            // SSC marks memo
    private String interCertificatePath;    // Intermediate certificate
    private String gradCertificatePath;     // Graduation certificate

    // Counts
    private int educationCount;
    private int certificationCount;
    private int internshipCount;
    private int workExperienceCount;
    private int identityProofCount;

    // Lists – already good, keep them
    private List<EducationDTO> educations;
    private List<CertificationDTO> certifications;
    private List<InternshipDTO> internships;
    private List<WorkExperienceDTO> workExperiences;
    private List<IdentityProofDTO> identityProofs;

    private String panNumber;
    private String aadharNumber;
    
    
    // ────────────────────────────────────────────────
    // Existing getters & setters (unchanged)
    // ────────────────────────────────────────────────

    public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public int getEducationCount() { return educationCount; }
    public void setEducationCount(int educationCount) { this.educationCount = educationCount; }
    public int getCertificationCount() { return certificationCount; }
    public void setCertificationCount(int certificationCount) { this.certificationCount = certificationCount; }
    public int getInternshipCount() { return internshipCount; }
    public void setInternshipCount(int internshipCount) { this.internshipCount = internshipCount; }
    public int getWorkExperienceCount() { return workExperienceCount; }
    public void setWorkExperienceCount(int workExperienceCount) { this.workExperienceCount = workExperienceCount; }
    public int getIdentityProofCount() { return identityProofCount; }
    public void setIdentityProofCount(int identityProofCount) { this.identityProofCount = identityProofCount; }
    public List<EducationDTO> getEducations() { return educations; }
    public void setEducations(List<EducationDTO> educations) { this.educations = educations; }
    public List<CertificationDTO> getCertifications() { return certifications; }
    public void setCertifications(List<CertificationDTO> certifications) { this.certifications = certifications; }
    public List<InternshipDTO> getInternships() { return internships; }
    public void setInternships(List<InternshipDTO> internships) { this.internships = internships; }
    public List<WorkExperienceDTO> getWorkExperiences() { return workExperiences; }
    public void setWorkExperiences(List<WorkExperienceDTO> workExperiences) { this.workExperiences = workExperiences; }
    public List<IdentityProofDTO> getIdentityProofs() { return identityProofs; }
    public void setIdentityProofs(List<IdentityProofDTO> identityProofs) { this.identityProofs = identityProofs; }

    // ────────────────────────────────────────────────
    // NEW getters & setters for file paths
    // ────────────────────────────────────────────────

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public String getPanPath() { return panPath; }
    public void setPanPath(String panPath) { this.panPath = panPath; }

    public String getAadharPath() { return aadharPath; }
    public void setAadharPath(String aadharPath) { this.aadharPath = aadharPath; }

    public String getVoterPath() { return voterPath; }
    public void setVoterPath(String voterPath) { this.voterPath = voterPath; }

    public String getPassportPath() { return passportPath; }
    public void setPassportPath(String passportPath) { this.passportPath = passportPath; }

    public String getSscCertificatePath() { return sscCertificatePath; }
    public void setSscCertificatePath(String sscCertificatePath) { this.sscCertificatePath = sscCertificatePath; }

    public String getSscMarksPath() { return sscMarksPath; }
    public void setSscMarksPath(String sscMarksPath) { this.sscMarksPath = sscMarksPath; }

    public String getInterCertificatePath() { return interCertificatePath; }
    public void setInterCertificatePath(String interCertificatePath) { this.interCertificatePath = interCertificatePath; }

    public String getGradCertificatePath() { return gradCertificatePath; }
    public void setGradCertificatePath(String gradCertificatePath) { this.gradCertificatePath = gradCertificatePath; }
}