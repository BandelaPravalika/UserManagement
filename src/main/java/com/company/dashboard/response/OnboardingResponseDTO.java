package com.company.dashboard.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.company.dashboard.model.Education;
import com.company.dashboard.model.EducationType;
import com.company.dashboard.model.Employee;
import com.company.dashboard.model.EmployeeForm;
import com.company.dashboard.model.IdentityProof;
import com.company.dashboard.util.FileUrlUtil;

public class OnboardingResponseDTO {

    // ───────── BASIC EMPLOYEE INFO ─────────
    private Long id;
    private String empId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private String permanentAddress;
    private String presentAddress;

    // ───────── FAMILY & EMERGENCY ─────────
    private String fathersName;
    private String fathersPhone;
    private String mothersName;
    private String mothersPhone;
    private String emergencyContactName;
    private String emergencyRelationship;
    private String emergencyNumber;

    // ───────── BANK (SEPARATED PROPERLY) ─────────
    private BankDetailsDTO bankDetails;

    // ───────── FILE PATHS ─────────
    private String passbookPath;
    private String photoPath;
    private String panPath;
    private String aadharPath;
    private String voterPath;
    private String passportPath;
    private String sscCertificatePath;
    private String sscMarksPath;
    private String interCertificatePath;
    private List<String> gradCertificatePaths = new ArrayList<>();
    private List<String> postGradCertificatePaths = new ArrayList<>();
    private List<String> certificationPaths = new ArrayList<>();

    // ───────── NUMBERS ─────────
    private String panNumber;
    private String aadharNumber;

    // ───────── COUNTS ─────────
    private int educationCount;
    private int certificationCount;
    private int internshipCount;
    private int workExperienceCount;
    private int identityProofCount;

    // ───────── LISTS ─────────
    private List<EducationDTO> educations = new ArrayList<>();
    private List<CertificationDTO> certifications = new ArrayList<>();
    private List<InternshipDTO> internships = new ArrayList<>();
    private List<ExperienceDTO> experiences = new ArrayList<>();
    private List<IdentityProofDTO> identityProofs = new ArrayList<>();

    // ───────── REJECTION INFO ─────────
    private Boolean onboardingRejected;
    private List<String> rejectedDocuments = new ArrayList<>();

    // ───────── GETTERS & SETTERS ─────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }
    public String getPresentAddress() { return presentAddress; }
    public void setPresentAddress(String presentAddress) { this.presentAddress = presentAddress; }
    public String getFathersName() { return fathersName; }
    public void setFathersName(String fathersName) { this.fathersName = fathersName; }
    public String getFathersPhone() { return fathersPhone; }
    public void setFathersPhone(String fathersPhone) { this.fathersPhone = fathersPhone; }
    public String getMothersName() { return mothersName; }
    public void setMothersName(String mothersName) { this.mothersName = mothersName; }
    public String getMothersPhone() { return mothersPhone; }
    public void setMothersPhone(String mothersPhone) { this.mothersPhone = mothersPhone; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public String getEmergencyRelationship() { return emergencyRelationship; }
    public void setEmergencyRelationship(String emergencyRelationship) { this.emergencyRelationship = emergencyRelationship; }
    public String getEmergencyNumber() { return emergencyNumber; }
    public void setEmergencyNumber(String emergencyNumber) { this.emergencyNumber = emergencyNumber; }
    public BankDetailsDTO getBankDetails() { return bankDetails; }
    public void setBankDetails(BankDetailsDTO bankDetails) { this.bankDetails = bankDetails; }
    public String getPassbookPath() { return passbookPath; }
    public void setPassbookPath(String passbookPath) { this.passbookPath = passbookPath; }
    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
    public String getPanPath() { return panPath; }
    public void setPanPath(String panPath) { this.panPath = panPath; }
    public String getAadharPath() { return aadharPath; }
    public void setAadharPath(String aadharPath) { this.aadharPath = aadharPath; }
    public String getVoterPath() { return voterPath; }
    public void setVoterPath(String voterPath) { this.voterPath = voterPath; }
    public String getPassportPath() { return passportPath; }
    public void setPassportPath(String passportPath) { this.passportPath = passportPath; }
    public String getSscCertificatePath() { return sscCertificatePath; }
    public void setSscCertificatePath(String sscCertificatePath) { this.sscCertificatePath = sscCertificatePath; }
    public String getSscMarksPath() { return sscMarksPath; }
    public void setSscMarksPath(String sscMarksPath) { this.sscMarksPath = sscMarksPath; }
    public String getInterCertificatePath() { return interCertificatePath; }
    public void setInterCertificatePath(String interCertificatePath) { this.interCertificatePath = interCertificatePath; }
    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
    public String getAadharNumber() { return aadharNumber; }
    public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }
    public int getEducationCount() { return educationCount; }
    public void setEducationCount(int educationCount) { this.educationCount = educationCount; }
    public int getCertificationCount() { return certificationCount; }
    public void setCertificationCount(int certificationCount) { this.certificationCount = certificationCount; }
    public int getInternshipCount() { return internshipCount; }
    public void setInternshipCount(int internshipCount) { this.internshipCount = internshipCount; }
    public int getWorkExperienceCount() { return workExperienceCount; }
    public void setWorkExperienceCount(int workExperienceCount) { this.workExperienceCount = workExperienceCount; }
    public int getIdentityProofCount() { return identityProofCount; }
    public void setIdentityProofCount(int identityProofCount) { this.identityProofCount = identityProofCount; }
    public List<EducationDTO> getEducations() { return educations; }
    public void setEducations(List<EducationDTO> educations) { this.educations = educations; }
    public List<CertificationDTO> getCertifications() { return certifications; }
    public void setCertifications(List<CertificationDTO> certifications) { this.certifications = certifications; }
    public List<InternshipDTO> getInternships() { return internships; }
    public void setInternships(List<InternshipDTO> internships) { this.internships = internships; }
    public List<ExperienceDTO> getExperiences() { return experiences; }
    public void setExperiences(List<ExperienceDTO> experiences) { this.experiences = experiences; }
    public List<IdentityProofDTO> getIdentityProofs() { return identityProofs; }
    public void setIdentityProofs(List<IdentityProofDTO> identityProofs) { this.identityProofs = identityProofs; }
    public List<String> getGradCertificatePaths() { return gradCertificatePaths; }
    public void setGradCertificatePaths(List<String> gradCertificatePaths) { this.gradCertificatePaths = gradCertificatePaths; }
    public List<String> getPostGradCertificatePaths() { return postGradCertificatePaths; }
    public void setPostGradCertificatePaths(List<String> postGradCertificatePaths) { this.postGradCertificatePaths = postGradCertificatePaths; }
    public List<String> getCertificationPaths() { return certificationPaths; }
    public void setCertificationPaths(List<String> certificationPaths) { this.certificationPaths = certificationPaths; }

    public Boolean getOnboardingRejected() { return onboardingRejected; }
    public void setOnboardingRejected(Boolean onboardingRejected) { this.onboardingRejected = onboardingRejected; }

    public List<String> getRejectedDocuments() { return rejectedDocuments; }
    public void setRejectedDocuments(List<String> rejectedDocuments) { this.rejectedDocuments = rejectedDocuments; }

    // ─────────────────────────────────────────────
    // FROM ENTITY MAPPING
    // ─────────────────────────────────────────────
    public static OnboardingResponseDTO fromEntity(EmployeeForm employeeForm) {
        OnboardingResponseDTO dto = new OnboardingResponseDTO();
        Employee employee = employeeForm.getEmployee();

        // ───────── BASIC INFO ─────────
        dto.setId(employeeForm.getId());
        dto.setEmpId(employee.getEmpId());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhone());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setBloodGroup(employeeForm.getBloodGroup());
        dto.setPermanentAddress(employeeForm.getPermanentAddress());
        dto.setPresentAddress(employeeForm.getPresentAddress());

        // ───────── REJECTION INFO ─────────
        dto.setOnboardingRejected(employee.getOnboardingRejected());
        dto.setRejectedDocuments(new ArrayList<>());

        // ───────── FAMILY & EMERGENCY ─────────
        dto.setFathersName(employeeForm.getFatherName());
        dto.setFathersPhone(employeeForm.getFatherPhone());
        dto.setMothersName(employeeForm.getMotherName());
        dto.setMothersPhone(employeeForm.getMotherPhone());
        dto.setEmergencyContactName(employeeForm.getEmergencyContactName());
        dto.setEmergencyRelationship(employeeForm.getEmergencyRelationship());
        dto.setEmergencyNumber(employeeForm.getEmergencyContactNumber());

        // ───────── BANK DETAILS ─────────
        if (employeeForm.getBankDetails() != null) {
            dto.setBankDetails(BankDetailsDTO.fromEntity(employeeForm.getBankDetails()));
            dto.setPassbookPath(FileUrlUtil.ensurePrefix(employeeForm.getBankDetails().getDocumentFilePath()));
        }

        // ───────── IDENTITY PROOFS ─────────
        if (employeeForm.getIdentityProofs() != null && !employeeForm.getIdentityProofs().isEmpty()) {
            List<IdentityProofDTO> identityDTOs = employeeForm.getIdentityProofs().stream()
                    .map(IdentityProofDTO::fromEntity)
                    .collect(Collectors.toList());
            dto.setIdentityProofs(identityDTOs);
            dto.setIdentityProofCount(identityDTOs.size());

            IdentityProof first = employeeForm.getIdentityProofs().get(0);
            dto.setPanPath(FileUrlUtil.ensurePrefix(first.getPanFilePath()));
            dto.setAadharPath(FileUrlUtil.ensurePrefix(first.getAadhaarFilePath()));
            dto.setPassportPath(FileUrlUtil.ensurePrefix(first.getPassportFilePath()));
            dto.setVoterPath(FileUrlUtil.ensurePrefix(first.getVoterIdFilePath()));
            dto.setPhotoPath(FileUrlUtil.ensurePrefix(first.getPhotoFilePath()));
            dto.setPanNumber(first.getPanNumber());
            dto.setAadharNumber(first.getAadhaarNumber());
        }

        // ───────── EDUCATIONS ─────────
        if (employeeForm.getEducations() != null && !employeeForm.getEducations().isEmpty()) {
            dto.setEducations(employeeForm.getEducations().stream()
                    .map(EducationDTO::fromEntity)
                    .collect(Collectors.toList()));
            dto.setEducationCount(employeeForm.getEducations().size());

            for (Education edu : employeeForm.getEducations()) {
                if (edu.getEducationType() == EducationType.SSC) {
                    dto.setSscCertificatePath(FileUrlUtil.ensurePrefix(edu.getCertificateFilePath()));
                    dto.setSscMarksPath(FileUrlUtil.ensurePrefix(edu.getMarksMemoFilePath()));
                } else if (edu.getEducationType() == EducationType.INTERMEDIATE) {
                    dto.setInterCertificatePath(FileUrlUtil.ensurePrefix(edu.getCertificateFilePath()));
                } else if (edu.getEducationType() == EducationType.GRADUATION) {
                    if (edu.getCertificateFilePath() != null) dto.getGradCertificatePaths().add(FileUrlUtil.ensurePrefix(edu.getCertificateFilePath()));
                } else if (edu.getEducationType() == EducationType.POST_GRADUATION) {
                    if (edu.getCertificateFilePath() != null) dto.getPostGradCertificatePaths().add(FileUrlUtil.ensurePrefix(edu.getCertificateFilePath()));
                }
            }
        }

        // ───────── CERTIFICATIONS ─────────
        if (employeeForm.getCertifications() != null) {
            dto.setCertifications(employeeForm.getCertifications().stream()
                    .map(CertificationDTO::fromEntity)
                    .collect(Collectors.toList()));
            dto.setCertificationCount(employeeForm.getCertifications().size());
            employeeForm.getCertifications().forEach(cert -> {
                if (cert.getCertificateFilePath() != null) dto.getCertificationPaths().add(FileUrlUtil.ensurePrefix(cert.getCertificateFilePath()));
            });
        }

        // ───────── INTERNSHIPS ─────────
        if (employeeForm.getInternships() != null) {
            dto.setInternships(employeeForm.getInternships().stream()
                    .map(InternshipDTO::fromEntity)
                    .collect(Collectors.toList()));
            dto.setInternshipCount(employeeForm.getInternships().size());
        }

        // ───────── WORK EXPERIENCES ─────────
        if (employeeForm.getExperiences() != null) {
            dto.setExperiences(employeeForm.getExperiences().stream()
                    .map(ExperienceDTO::fromEntity)
                    .collect(Collectors.toList()));
            dto.setWorkExperienceCount(employeeForm.getExperiences().size());
        }

        return dto;
    }
}