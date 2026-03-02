package com.company.dashboard.model;

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
public class IdentityProof  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String proofType;
    private String documentNumber;
    private String filePath;
    
    
    
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

	public String getProofType() {
		return proofType;
	}

	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public EmployeeForm getEmployeeForm() {
		return employeeForm;
	}

	public void setEmployeeForm(EmployeeForm employeeForm) {
		this.employeeForm = employeeForm;
	}

	
}

