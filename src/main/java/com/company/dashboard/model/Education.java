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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "educations")

public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EducationType educationType;

    @NotBlank
    @Column(nullable = false)
    private String institutionName;

    private String hallTicketNumber;

    @NotNull
    @Min(1900)
    @Column(nullable = false)
    private Integer passoutYear;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double percentage;

    private String certificateFilePath;

    private String marksMemoFilePath;

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

	public EducationType getEducationType() {
		return educationType;
	}

	public void setEducationType(EducationType educationType) {
		this.educationType = educationType;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getHallTicketNumber() {
		return hallTicketNumber;
	}

	public void setHallTicketNumber(String hallTicketNumber) {
		this.hallTicketNumber = hallTicketNumber;
	}

	public Integer getPassoutYear() {
		return passoutYear;
	}

	public void setPassoutYear(Integer passoutYear) {
		this.passoutYear = passoutYear;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getCertificateFilePath() {
		return certificateFilePath;
	}

	public void setCertificateFilePath(String certificateFilePath) {
		this.certificateFilePath = certificateFilePath;
	}

	public String getMarksMemoFilePath() {
		return marksMemoFilePath;
	}

	public void setMarksMemoFilePath(String marksMemoFilePath) {
		this.marksMemoFilePath = marksMemoFilePath;
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