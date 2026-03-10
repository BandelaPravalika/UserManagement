package com.company.dashboard.response;

public class EmployeeSummaryDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Integer educationCount;
    private Integer certificationCount;
    private Integer internshipCount;
    private Integer workExperienceCount;
    private Integer identityProofCount;
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
	public Integer getEducationCount() {
		return educationCount;
	}
	public void setEducationCount(Integer educationCount) {
		this.educationCount = educationCount;
	}
	public Integer getCertificationCount() {
		return certificationCount;
	}
	public void setCertificationCount(Integer certificationCount) {
		this.certificationCount = certificationCount;
	}
	public Integer getInternshipCount() {
		return internshipCount;
	}
	public void setInternshipCount(Integer internshipCount) {
		this.internshipCount = internshipCount;
	}
	public Integer getWorkExperienceCount() {
		return workExperienceCount;
	}
	public void setWorkExperienceCount(Integer workExperienceCount) {
		this.workExperienceCount = workExperienceCount;
	}
	public Integer getIdentityProofCount() {
		return identityProofCount;
	}
	public void setIdentityProofCount(Integer identityProofCount) {
		this.identityProofCount = identityProofCount;
	}
    
}