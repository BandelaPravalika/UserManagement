package com.company.dashboard.response;

public class WorkExperienceDTO {

    private String companyName;
    private String yearsOfExp;
    private String offerLetterPath;
    private String relievingLetterPath;
    private String payslipsPath;
    private String experienceCertificatePath;
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
}