package com.company.dashboard.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.company.dashboard.model.Employee;
import com.company.dashboard.model.Employee.EmployeeStatus;
import com.company.dashboard.model.EmployeeForm;

public class EmployeeWithOnboardingDTO {

    // ===== EMPLOYEE =====
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

    // ===== PERSONAL =====
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

    // ===== BANK (CORRECT) =====
    private BankDetailsDTO bankDetails;

    // ===== IDENTITY =====
    private String panNumber;
    private String panPath;
    private String aadhaarNumber;
    private String aadhaarPath;
    private String passportPath;
    private String voterPath;
    private String photoPath;

    // ===== LISTS =====
    private List<EducationDTO> educations;
    private List<CertificationDTO> certifications;
    private List<InternshipDTO> internships;
    private List<ExperienceDTO> workExperiences;

    public static EmployeeWithOnboardingDTO from(Employee emp, EmployeeForm form) {

        EmployeeWithOnboardingDTO dto = new EmployeeWithOnboardingDTO();

        // ===== EMPLOYEE =====
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

            // ===== PERSONAL =====
            dto.bloodGroup = form.getBloodGroup();
            dto.permanentAddress = form.getPermanentAddress();
            dto.presentAddress = form.getPresentAddress();
            dto.fathersName = form.getFatherName();
            dto.fathersPhone = form.getFatherPhone();
            dto.mothersName = form.getMotherName();
            dto.mothersPhone = form.getMotherPhone();
            dto.emergencyContactName = form.getEmergencyContactName();
            dto.emergencyRelationship = form.getEmergencyRelationship();
            dto.emergencyNumber = form.getEmergencyContactNumber();

            // ===== BANK (THIS IS THE ONLY CORRECT LINE) =====
            dto.bankDetails = BankDetailsDTO.fromEntity(form.getBankDetails());

            // ===== IDENTITY =====
            if (form.getIdentityProofs() != null && !form.getIdentityProofs().isEmpty()) {
                var proof = form.getIdentityProofs().get(0);

                dto.panNumber = proof.getPanNumber();
                dto.panPath = proof.getPanFilePath();
                dto.aadhaarNumber = proof.getAadhaarNumber();
                dto.aadhaarPath = proof.getAadhaarFilePath();
                dto.passportPath = proof.getPassportFilePath();
                dto.voterPath = proof.getVoterIdFilePath();
                dto.photoPath = proof.getPhotoFilePath();
            }

            // ===== EDUCATION =====
            dto.educations = form.getEducations() != null
                    ? form.getEducations().stream()
                        .map(EducationDTO::fromEntity)
                        .collect(Collectors.toList())
                    : Collections.emptyList();

            // ===== CERTIFICATIONS =====
            dto.certifications = form.getCertifications() != null
                    ? form.getCertifications().stream()
                        .map(CertificationDTO::fromEntity)
                        .collect(Collectors.toList())
                    : Collections.emptyList();

            // ===== INTERNSHIPS =====
            dto.internships = form.getInternships() != null
                    ? form.getInternships().stream()
                        .map(InternshipDTO::fromEntity)
                        .collect(Collectors.toList())
                    : Collections.emptyList();

            // ===== EXPERIENCE =====
            dto.workExperiences = form.getExperiences() != null
                    ? form.getExperiences().stream()
                        .map(ExperienceDTO::fromEntity)
                        .collect(Collectors.toList())
                    : Collections.emptyList();
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

	public LocalDateTime getActivatedAt() {
		return activatedAt;
	}

	public void setActivatedAt(LocalDateTime activatedAt) {
		this.activatedAt = activatedAt;
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

	public BankDetailsDTO getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetailsDTO bankDetails) {
		this.bankDetails = bankDetails;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getPanPath() {
		return panPath;
	}

	public void setPanPath(String panPath) {
		this.panPath = panPath;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getAadhaarPath() {
		return aadhaarPath;
	}

	public void setAadhaarPath(String aadhaarPath) {
		this.aadhaarPath = aadhaarPath;
	}

	public String getPassportPath() {
		return passportPath;
	}

	public void setPassportPath(String passportPath) {
		this.passportPath = passportPath;
	}

	public String getVoterPath() {
		return voterPath;
	}

	public void setVoterPath(String voterPath) {
		this.voterPath = voterPath;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
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

	public List<ExperienceDTO> getWorkExperiences() {
		return workExperiences;
	}

	public void setWorkExperiences(List<ExperienceDTO> workExperiences) {
		this.workExperiences = workExperiences;
	}

    
}