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
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String yearsOfExp;
    private String offerLetterPath;
    private String relievingLetterPath;
    private String payslipsPath;
    private String experienceCertificatePath;
    
    
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getYearsOfExp() {
		return yearsOfExp;
	}

	public void setYearsOfExp(String yearsOfExp) {
		this.yearsOfExp = yearsOfExp;
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

	
    
}
