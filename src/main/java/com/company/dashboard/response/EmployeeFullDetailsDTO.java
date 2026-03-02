package com.company.dashboard.response;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;

public class EmployeeFullDetailsDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
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
    private String bankName;
    private String branchName;
    private String accountNumber;
    private String ifscCode;
    private String upiId;
    private String passbookPath;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "aadhar_number")
    private String aadharNumber;
    
    
    
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
	private List<EducationDTO> educations;
    private List<CertificationDTO> certifications;
    private List<InternshipDTO> internships;
    private List<WorkExperienceDTO> workExperiences;
    private List<IdentityProofDTO> identityProofs;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate localDate) {
		this.dateOfBirth = localDate;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getPresentAddress() {
		return presentAddress;
	}
	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}
	public String getFathersName() {
		return fathersName;
	}
	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}
	public String getFathersPhone() {
		return fathersPhone;
	}
	public void setFathersPhone(String fathersPhone) {
		this.fathersPhone = fathersPhone;
	}
	public String getMothersName() {
		return mothersName;
	}
	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}
	public String getMothersPhone() {
		return mothersPhone;
	}
	public void setMothersPhone(String mothersPhone) {
		this.mothersPhone = mothersPhone;
	}
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
	public String getEmergencyRelationship() {
		return emergencyRelationship;
	}
	public void setEmergencyRelationship(String emergencyRelationship) {
		this.emergencyRelationship = emergencyRelationship;
	}
	public String getEmergencyNumber() {
		return emergencyNumber;
	}
	public void setEmergencyNumber(String emergencyNumber) {
		this.emergencyNumber = emergencyNumber;
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
	public String getPassbookPath() {
		return passbookPath;
	}
	public void setPassbookPath(String passbookPath) {
		this.passbookPath = passbookPath;
	}
	public List<EducationDTO> getEducations() {
		return educations;
	}
	public void setEducations(List<EducationDTO> educations) {
		this.educations = educations;
	}
	public List<CertificationDTO> getCertifications() {
		return certifications;
	}
	public void setCertifications(List<CertificationDTO> certifications) {
		this.certifications = certifications;
	}
	public List<InternshipDTO> getInternships() {
		return internships;
	}
	public void setInternships(List<InternshipDTO> internships) {
		this.internships = internships;
	}
	public List<WorkExperienceDTO> getWorkExperiences() {
		return workExperiences;
	}
	public void setWorkExperiences(List<WorkExperienceDTO> workExperiences) {
		this.workExperiences = workExperiences;
	}
	public List<IdentityProofDTO> getIdentityProofs() {
		return identityProofs;
	}
	public void setIdentityProofs(List<IdentityProofDTO> identityProofs) {
		this.identityProofs = identityProofs;
	}
    
}