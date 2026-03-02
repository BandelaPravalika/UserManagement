package com.company.dashboard.response;

public class IdentityProofDTO {
    private String proofType; // PAN, AADHAR, PHOTO, PASSPORT, VOTER
    private String documentNumber;
    private String filePath;

    // Getters (you already have these)
    public String getProofType() {
        return proofType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    // Missing setters – ADD THESE
    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}