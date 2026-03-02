package com.company.dashboard.model;

import com.company.dashboard.model.Certification.ProofStatus;
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
@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String educationType;
    private String institutionName;
    private String hallTicketNo;
    private Integer passoutYear;
    private Double percentageCgpa;
    private String certificatePath;
    private String marksMemoPath;
    
    @Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ProofStatus status = ProofStatus.PENDING;

	public enum ProofStatus {
	    PENDING,
	    ACCEPTED,
	    REJECTED
	}
	
	
	
	
	public ProofStatus getStatus() {
		return status;
	}
	public void setStatus(ProofStatus status) {
		this.status = status;
	}
    
    
    

	@ManyToOne
	@JoinColumn(name = "employee_form_id") // FK column
	@JsonIgnore 
	private EmployeeForm employeeForm;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEducationType() {
		return educationType;
	}

	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getHallTicketNo() {
		return hallTicketNo;
	}

	public void setHallTicketNo(String hallTicketNo) {
		this.hallTicketNo = hallTicketNo;
	}

	public Integer getPassoutYear() {
		return passoutYear;
	}

	public void setPassoutYear(Integer passoutYear) {
		this.passoutYear = passoutYear;
	}

	public Double getPercentageCgpa() {
		return percentageCgpa;
	}

	public void setPercentageCgpa(Double percentageCgpa) {
		this.percentageCgpa = percentageCgpa;
	}

	public String getCertificatePath() {
		return certificatePath;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	public String getMarksMemoPath() {
		return marksMemoPath;
	}

	public void setMarksMemoPath(String marksMemoPath) {
		this.marksMemoPath = marksMemoPath;
	}

	public EmployeeForm getEmployeeForm() {
		return employeeForm;
	}

	public void setEmployeeForm(EmployeeForm employeeForm) {
		this.employeeForm = employeeForm;
	}

	
    
}

