package com.company.dashboard.response;

public class EducationDTO {

    private String institutionName;
    private String hallTicketNo;
    private Integer passoutYear;
    private Double percentageCgpa;
    private String certificatePath;
    private String marksMemoPath;
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
	 private String educationType; // add this
	    // existing fields like institutionName, certificatePath, etc.

	    public String getEducationType() { return educationType; }
	    public void setEducationType(String educationType) { this.educationType = educationType; }
	
    
}