package com.company.dashboard.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employee_forms")

public class EmployeeForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------------- PERSONAL DETAILS ----------------

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    @Column(nullable = false, length = 10)
    private String phoneNumber;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    private String bloodGroup;

    @NotNull
    @Past
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Size(max = 255)
    private String permanentAddress;

    @Size(max = 255)
    private String presentAddress;

//    @NotBlank
//    @Column(nullable = false)
    private String fatherName;

    @Pattern(regexp = "^[0-9]{10}$")
    @Column(nullable = false)
    private String fatherPhone;

//    @NotBlank
//    @Column(nullable = false)
    private String motherName;

    @Pattern(regexp = "^[0-9]{10}$")
    @Column(nullable = false)
    private String motherPhone;

    @NotBlank
    @Column(nullable = false)
    private String emergencyContactName;

    @NotBlank
    @Column(nullable = false)
    private String emergencyRelationship;

    @Pattern(regexp = "^[0-9]{10}$")
    @Column(nullable = false)
    private String emergencyContactNumber;

    // ---------------- RELATIONSHIPS ----------------

    @OneToMany(mappedBy = "employeeForm",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "employeeForm",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Internship> internships = new ArrayList<>();

    @OneToMany(mappedBy = "employeeForm",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(mappedBy = "employeeForm",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Certification> certifications = new ArrayList<>();

    @OneToMany(mappedBy = "employeeForm",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
//    @JsonBackReference
    private List<IdentityProof> identityProofs = new ArrayList<>();

    @OneToOne(mappedBy = "employeeForm",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private BankDetails bankDetails;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherPhone() {
		return fatherPhone;
	}

	public void setFatherPhone(String fatherPhone) {
		this.fatherPhone = fatherPhone;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMotherPhone() {
		return motherPhone;
	}

	public void setMotherPhone(String motherPhone) {
		this.motherPhone = motherPhone;
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

	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}

	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}

	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public List<Internship> getInternships() {
		return internships;
	}

	public void setInternships(List<Internship> internships) {
		this.internships = internships;
	}

	public List<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	public List<Certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}

	public List<IdentityProof> getIdentityProofs() {
		return identityProofs;
	}

	public void setIdentityProofs(List<IdentityProof> identityProofs) {
		this.identityProofs = identityProofs;
	}

	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}
	
	@OneToOne
	@JoinColumn(name = "employee_id")
	@JsonBackReference
	private Employee employee;
	

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
	    this.employee = employee;
	}
    
}