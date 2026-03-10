package com.company.dashboard.model; 
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Entity
@Table(name = "certifications")

public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
//    @Column(nullable = false)
    private String instituteName;

    private String certificateNumber;

//    @NotBlank
    
//    @Column(name = "certificate_file_path", nullable = false)
    private String certificateFilePath;
    

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

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getCertificateFilePath() {
		return certificateFilePath;
	}

	public void setCertificateFilePath(String certificateFilePath) {
		this.certificateFilePath = certificateFilePath;
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
