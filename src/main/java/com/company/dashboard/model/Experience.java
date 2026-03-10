package com.company.dashboard.model;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "experiences")

public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String companyName;

    @Min(0)
    private Integer yearsOfExperience;

    private String offerLetterPath;
    private String relievingLetterPath;
    private String payslipsPath;
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

	public Integer getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public String getOfferLetterPath() {
		return offerLetterPath;
	}

	public void setOfferLetterPath(String offerLetterPath) {
		this.offerLetterPath = offerLetterPath;
	}

	public String getRelievingLetterPath() {
		return relievingLetterPath;
	}

	public void setRelievingLetterPath(String relievingLetterPath) {
		this.relievingLetterPath = relievingLetterPath;
	}

	public String getPayslipsPath() {
		return payslipsPath;
	}

	public void setPayslipsPath(String payslipsPath) {
		this.payslipsPath = payslipsPath;
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