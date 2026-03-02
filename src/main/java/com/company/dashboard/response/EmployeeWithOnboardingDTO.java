package com.company.dashboard.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.company.dashboard.model.Employee;
import com.company.dashboard.model.Employee.EmployeeStatus;
import com.company.dashboard.model.EmployeeForm;

//Add all fields you want from Employee + Onboarding
public class EmployeeWithOnboardingDTO {

 // From Employee
 private Long id;
 private String empId;
 private String fullName;
 private String dept;
 private String role;
 private String entity;
 private LocalDate dateOfBirth;
 private LocalDate dateOfInterview;
 private LocalDate dateOfOnboarding;
 private String email;
 private String phone;
 private EmployeeStatus status;
 private String onboardingToken;
 private LocalDateTime activatedAt;

 
 
 // From EmployeeForm + children
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
 
 
 private String photoPath;               // Passport size photo
 private String panPath;                 // PAN card
 private String aadharPath;              // Aadhar card
 private String voterPath;               // Voter ID
 private String passportPath;            // Passport document
 private String sscCertificatePath;      // SSC certificate
 private String sscMarksPath;            // SSC marks memo
 private String interCertificatePath;    // Intermediate certificate
 private String gradCertificatePath;     // Graduation certificate
 

 
 
 public String getPhotoPath() {
	return photoPath;
}

 public void setPhotoPath(String photoPath) {
	this.photoPath = photoPath;
 }

 public String getPanPath() {
	return panPath;
 }

 public void setPanPath(String panPath) {
	this.panPath = panPath;
 }

 public String getAadharPath() {
	return aadharPath;
 }

 public void setAadharPath(String aadharPath) {
	this.aadharPath = aadharPath;
 }

 public String getVoterPath() {
	return voterPath;
 }

 public void setVoterPath(String voterPath) {
	this.voterPath = voterPath;
 }

 public String getPassportPath() {
	return passportPath;
 }

 public void setPassportPath(String passportPath) {
	this.passportPath = passportPath;
 }

 public String getSscCertificatePath() {
	return sscCertificatePath;
 }

 public void setSscCertificatePath(String sscCertificatePath) {
	this.sscCertificatePath = sscCertificatePath;
 }

 public String getSscMarksPath() {
	return sscMarksPath;
 }

 public void setSscMarksPath(String sscMarksPath) {
	this.sscMarksPath = sscMarksPath;
 }

 public String getInterCertificatePath() {
	return interCertificatePath;
 }

 public void setInterCertificatePath(String interCertificatePath) {
	this.interCertificatePath = interCertificatePath;
 }

 public String getGradCertificatePath() {
	return gradCertificatePath;
 }

 public void setGradCertificatePath(String gradCertificatePath) {
	this.gradCertificatePath = gradCertificatePath;
 }

 private List<EducationDTO> educations;
 private List<CertificationDTO> certifications;
 private List<InternshipDTO> internships;
 private List<WorkExperienceDTO> workExperiences;
 private List<IdentityProofDTO> identityProofs;

 public static EmployeeWithOnboardingDTO from(Employee emp, EmployeeForm form) {
	    EmployeeWithOnboardingDTO dto = new EmployeeWithOnboardingDTO();

	    // ─── Existing Employee mapping ─── (keep unchanged)
	    dto.id = emp.getId();
	    dto.empId = emp.getEmpId();
	    dto.fullName = emp.getFullName();
	    dto.dept = emp.getDept();
	    dto.role = emp.getRole();
	    dto.entity = emp.getEntity();
	    dto.dateOfBirth = emp.getDateOfBirth();
	    dto.dateOfInterview = emp.getDateOfInterview();
	    dto.dateOfOnboarding = emp.getDateOfOnboarding();
	    dto.email = emp.getEmail();
	    dto.phone = emp.getPhone();
	    dto.status = emp.getStatus();
	    dto.onboardingToken = emp.getOnboardingToken();
	    dto.activatedAt = emp.getActivatedAt();

	    if (form != null) {
	        // ─── Existing form fields ─── (keep unchanged)
	        dto.bloodGroup = form.getBloodGroup();
	        dto.permanentAddress = form.getPermanentAddress();
	        dto.presentAddress = form.getPresentAddress();
	        dto.fathersName = form.getFathersName();
	        dto.fathersPhone = form.getFathersPhone();
	        dto.mothersName = form.getMothersName();
	        dto.mothersPhone = form.getMothersPhone();
	        dto.emergencyContactName = form.getEmergencyContactName();
	        dto.emergencyRelationship = form.getEmergencyRelationship();
	        dto.emergencyNumber = form.getEmergencyNumber();
	        dto.bankName = form.getBankName();
	        dto.branchName = form.getBranchName();
	        dto.accountNumber = form.getAccountNumber();
	        dto.ifscCode = form.getIfscCode();
	        dto.upiId = form.getUpiId();

	        // ─── Existing passbook ─── (keep)
	        dto.passbookPath = form.getPassbookPath();

	        // ─── NEW: add photo path (most important missing one) ───
	        dto.setPhotoPath(form.getPhotoPath());

	        // ─── NEW: map identity proofs to direct fields ───
	        if (form.getIdentityProofs() != null) {
	            form.getIdentityProofs().forEach(proof -> {
	                String type = proof.getProofType() != null ? proof.getProofType().toUpperCase() : "";
	                switch (type) {
	                    case "PAN"      -> dto.setPanPath(proof.getFilePath());
	                    case "AADHAR"   -> dto.setAadharPath(proof.getFilePath());
	                    case "VOTER"    -> dto.setVoterPath(proof.getFilePath());
	                    case "PASSPORT" -> dto.setPassportPath(proof.getFilePath());
	                    // Add more types if needed (e.g. "DRIVING_LICENSE", "PHOTO")
	                }
	            });
	        }

	        // ─── NEW: map education certificates to direct fields ───
	        if (form.getEducations() != null) {
	            form.getEducations().forEach(edu -> {
	                String type = edu.getEducationType() != null ? edu.getEducationType().toUpperCase() : "";
	                switch (type) {
	                    case "SSC" -> {
	                        dto.setSscCertificatePath(edu.getCertificatePath());
	                        dto.setSscMarksPath(edu.getMarksMemoPath());
	                    }
	                    case "INTERMEDIATE" -> dto.setInterCertificatePath(edu.getCertificatePath());
	                    case "GRADUATION"   -> dto.setGradCertificatePath(edu.getCertificatePath());
	                    // Add "POST_GRADUATION" if you have it
	                }
	            });
	        }

	        // ─── Keep all your existing list mappings ─── (do not change these)
	        dto.educations = (form.getEducations() != null)
	                ? form.getEducations().stream().map(e -> {
	                    EducationDTO ed = new EducationDTO();
	                    ed.setInstitutionName(e.getInstitutionName());
	                    ed.setHallTicketNo(e.getHallTicketNo());
	                    ed.setPassoutYear(e.getPassoutYear());
	                    ed.setPercentageCgpa(e.getPercentageCgpa());
	                    ed.setCertificatePath(e.getCertificatePath());
	                    ed.setMarksMemoPath(e.getMarksMemoPath());
	                    return ed;
	                }).collect(Collectors.toList())
	                : Collections.emptyList();

	        // ... keep certifications, internships, workExperiences, identityProofs mapping exactly as-is ...
	    }

	    return dto;
	}

 public Long getId() {
	return id;
 }

 public void setId(Long id) {
	this.id = id;
 }

 public String getEmpId() {
	return empId;
 }

 public void setEmpId(String empId) {
	this.empId = empId;
 }

 public String getFullName() {
	return fullName;
 }

 public void setFullName(String fullName) {
	this.fullName = fullName;
 }

 public String getDept() {
	return dept;
 }

 public void setDept(String dept) {
	this.dept = dept;
 }

 public String getRole() {
	return role;
 }

 public void setRole(String role) {
	this.role = role;
 }

 public String getEntity() {
	return entity;
 }

 public void setEntity(String entity) {
	this.entity = entity;
 }

 public LocalDate getDateOfBirth() {
	return dateOfBirth;
 }

 public void setDateOfBirth(LocalDate dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
 }

 public LocalDate getDateOfInterview() {
	return dateOfInterview;
 }

 public void setDateOfInterview(LocalDate dateOfInterview) {
	this.dateOfInterview = dateOfInterview;
 }

 public LocalDate getDateOfOnboarding() {
	return dateOfOnboarding;
 }

 public void setDateOfOnboarding(LocalDate dateOfOnboarding) {
	this.dateOfOnboarding = dateOfOnboarding;
 }

 public String getEmail() {
	return email;
 }

 public void setEmail(String email) {
	this.email = email;
 }

 public String getPhone() {
	return phone;
 }

 public void setPhone(String phone) {
	this.phone = phone;
 }

 public EmployeeStatus getStatus() {
	return status;
 }

 public void setStatus(EmployeeStatus status) {
	this.status = status;
 }

 public String getOnboardingToken() {
	return onboardingToken;
 }

 public void setOnboardingToken(String onboardingToken) {
	this.onboardingToken = onboardingToken;
 }

// public LocalDate getActivatedAt() {
//	return activatedAt;
// }
//
// public void setActivatedAt(LocalDate activatedAt) {
//	this.activatedAt = activatedAt;
// }

 public String getBloodGroup() {
	return bloodGroup;
 }

 public LocalDateTime getActivatedAt() {
	return activatedAt;
}

 public void setActivatedAt(LocalDateTime activatedAt) {
	this.activatedAt = activatedAt;
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
