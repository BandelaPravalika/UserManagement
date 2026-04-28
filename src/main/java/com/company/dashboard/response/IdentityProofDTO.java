package com.company.dashboard.response;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.IdentityProof;
import com.company.dashboard.model.ProofStatus;
import com.company.dashboard.util.FileUrlUtil;

public class IdentityProofDTO {

    private Long id;

    // Numbers
    private String panNumber;
    private String aadhaarNumber;

    // File paths (for DB storage)
    private String panFilePath;
    private String aadhaarFilePath;
    private String passportFilePath;
    private String voterIdFilePath;
    private String photoFilePath;
    private String passbookFilePath;

    // Uploaded files (for API)
    private MultipartFile panFile;
    private MultipartFile aadhaarFile;
    private MultipartFile passportFile;
    private MultipartFile voterIdFile;
    private MultipartFile photoFile;
    private MultipartFile passbookFile;

    // Status & review info
    private ProofStatus status;
    private String rejectionReason;
    private LocalDateTime reviewedAt;

    // Convert entity to DTO
    public static IdentityProofDTO fromEntity(IdentityProof proof) {
        if (proof == null) return null;

        IdentityProofDTO dto = new IdentityProofDTO();
        dto.setId(proof.getId());
        dto.setPanNumber(proof.getPanNumber());
        dto.setAadhaarNumber(proof.getAadhaarNumber());

        // Assign paths from entity with safe prefixing
        dto.setPanFilePath(FileUrlUtil.ensurePrefix(proof.getPanFilePath()));
        dto.setAadhaarFilePath(FileUrlUtil.ensurePrefix(proof.getAadhaarFilePath()));
        dto.setPassportFilePath(FileUrlUtil.ensurePrefix(proof.getPassportFilePath()));
        dto.setVoterIdFilePath(FileUrlUtil.ensurePrefix(proof.getVoterIdFilePath()));
        dto.setPhotoFilePath(FileUrlUtil.ensurePrefix(proof.getPhotoFilePath()));

        dto.setStatus(proof.getStatus());
        dto.setRejectionReason(proof.getRejectionReason());
        dto.setReviewedAt(proof.getReviewedAt());

        return dto;
    }

    // ───── Getters & Setters ─────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }

    public String getAadhaarNumber() { return aadhaarNumber; }
    public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }

    public String getPanFilePath() { return panFilePath; }
    public void setPanFilePath(String panFilePath) { this.panFilePath = panFilePath; }

    public String getAadhaarFilePath() { return aadhaarFilePath; }
    public void setAadhaarFilePath(String aadhaarFilePath) { this.aadhaarFilePath = aadhaarFilePath; }

    public String getPassportFilePath() { return passportFilePath; }
    public void setPassportFilePath(String passportFilePath) { this.passportFilePath = passportFilePath; }

    public String getVoterIdFilePath() { return voterIdFilePath; }
    public void setVoterIdFilePath(String voterIdFilePath) { this.voterIdFilePath = voterIdFilePath; }

    public String getPhotoFilePath() { return photoFilePath; }
    public void setPhotoFilePath(String photoFilePath) { this.photoFilePath = photoFilePath; }

    public String getPassbookFilePath() { return passbookFilePath; }
    public void setPassbookFilePath(String passbookFilePath) { this.passbookFilePath = passbookFilePath; }

    public MultipartFile getPanFile() { return panFile; }
    public void setPanFile(MultipartFile panFile) { this.panFile = panFile; }

    public MultipartFile getAadhaarFile() { return aadhaarFile; }
    public void setAadhaarFile(MultipartFile aadhaarFile) { this.aadhaarFile = aadhaarFile; }

    public MultipartFile getPassportFile() { return passportFile; }
    public void setPassportFile(MultipartFile passportFile) { this.passportFile = passportFile; }

    public MultipartFile getVoterIdFile() { return voterIdFile; }
    public void setVoterIdFile(MultipartFile voterIdFile) { this.voterIdFile = voterIdFile; }

    public MultipartFile getPhotoFile() { return photoFile; }
    public void setPhotoFile(MultipartFile photoFile) { this.photoFile = photoFile; }

    public MultipartFile getPassbookFile() { return passbookFile; }
    public void setPassbookFile(MultipartFile passbookFile) { this.passbookFile = passbookFile; }

    public ProofStatus getStatus() { return status; }
    public void setStatus(ProofStatus status) { this.status = status; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}