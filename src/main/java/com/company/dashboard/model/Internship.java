
package com.company.dashboard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "internships")

public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String companyName;

    @NotNull
    @Column(nullable = false)
    private LocalDate joiningDate;

    private LocalDate relievingDate;

    private String internshipId;

    private String duration;

    private String offerLetterPath;

    private String experienceCertificatePath;

    @ManyToOne
    @JoinColumn(name = "employee_form_id")
    @JsonIgnore
    private EmployeeForm employeeForm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public LocalDate getRelievingDate() {
		return relievingDate;
	}

	public void setRelievingDate(LocalDate relievingDate) {
		this.relievingDate = relievingDate;
	}

	public String getInternshipId() {
		return internshipId;
	}

	public void setInternshipId(String internshipId) {
		this.internshipId = internshipId;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getOfferLetterPath() {
		return offerLetterPath;
	}

	public void setOfferLetterPath(String offerLetterPath) {
		this.offerLetterPath = offerLetterPath;
	}

	public String getExperienceCertificatePath() {
		return experienceCertificatePath;
	}

	public void setExperienceCertificatePath(String experienceCertificatePath) {
		this.experienceCertificatePath = experienceCertificatePath;
	}

	public EmployeeForm getEmployeeForm() {
		return employeeForm;
	}

	public void setEmployeeForm(EmployeeForm employeeForm) {
		this.employeeForm = employeeForm;
	}
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ProofStatus status = ProofStatus.PENDING;

	@Column(length = 1000)
	private String rejectionReason;

	private LocalDateTime reviewedAt;

	public ProofStatus getStatus() {
		return status;
	}

	public void setStatus(ProofStatus status) {
		this.status = status;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public LocalDateTime getReviewedAt() {
		return reviewedAt;
	}

	public void setReviewedAt(LocalDateTime reviewedAt) {
		this.reviewedAt = reviewedAt;
	}
	
    
}