package com.company.dashboard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "photo_path")
    private String photoPath;

 // inside EmployeeForm.java
    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "aadhar_number")
    private String aadharNumber;

    // Add getters and setters
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
    
    private LocalDateTime activatedAt;
    public LocalDateTime getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(LocalDateTime activatedAt) {
        this.activatedAt = activatedAt;
    }
    
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

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
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
	public List<Education> getEducations() {
		return educations;
	}
	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}
	public List<Certification> getCertifications() {
		return certifications;
	}
	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}
	public List<Internship> getInternships() {
		return internships;
	}
	public void setInternships(List<Internship> internships) {
		this.internships = internships;
	}
	public List<WorkExperience> getWorkExperiences() {
		return workExperiences;
	}
	public void setWorkExperiences(List<WorkExperience> workExperiences) {
		this.workExperiences = workExperiences;
	}
	public List<IdentityProof> getIdentityProofs() {
		return identityProofs;
	}
	public void setIdentityProofs(List<IdentityProof> identityProofs) {
		this.identityProofs = identityProofs;
	}
	@OneToOne
	@JoinColumn(name = "employee_id", nullable = false, unique = true)
	@JsonIgnore

	private Employee employee;

	@OneToMany(mappedBy = "employeeForm", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Education> educations = new ArrayList<>();

	@OneToMany(mappedBy = "employeeForm", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Certification> certifications = new ArrayList<>();

	@OneToMany(mappedBy = "employeeForm", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Internship> internships = new ArrayList<>();

	@OneToMany(mappedBy = "employeeForm", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WorkExperience> workExperiences = new ArrayList<>();

	@OneToMany(mappedBy = "employeeForm", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<IdentityProof> identityProofs = new ArrayList<>();

    // relation methods
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    

    public void addInternship(Internship internship) {
        internships.add(internship);
        internship.setEmployeeForm(this);
    }

    public void addWorkExperience(WorkExperience workExperience) {
        workExperiences.add(workExperience);
        workExperience.setEmployeeForm(this);
    }

    public void addCertification(Certification certification) {
        certifications.add(certification);
        certification.setEmployeeForm(this);
    }

    public void addEducation(Education education) {
        educations.add(education);
        education.setEmployeeForm(this);
    }

    public void addIdentityProof(IdentityProof proof) {
        identityProofs.add(proof);
        proof.setEmployeeForm(this);
    }
    
    public void removeInternship(Internship internship) {
        internships.remove(internship);
        internship.setEmployeeForm(null);
    }
    public void removeWorkExperience(WorkExperience workExperience) {
        internships.remove(workExperience);
        workExperience.setEmployeeForm(null);
    }
    public void removeCertification(Certification certification) {
        internships.remove(certification);
        certification.setEmployeeForm(null);
    }
    public void removeEducation(Education education) {
        internships.remove(education);
        education.setEmployeeForm(null);
    }
    public void removeIdentityProof(IdentityProof identityProof) {
        internships.remove(identityProof);
        identityProof.setEmployeeForm(null);
    }
    
}


