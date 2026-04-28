package com.company.dashboard.serviceimpl;

import java.io.IOException;
import com.company.dashboard.service.OnboardingTokenService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.BankDetails;
import com.company.dashboard.model.Certification;
import com.company.dashboard.model.Education;
import com.company.dashboard.model.EducationType;
import com.company.dashboard.model.Employee;
import com.company.dashboard.model.EmployeeForm;
import com.company.dashboard.model.Experience;
import com.company.dashboard.model.IdentityProof;
import com.company.dashboard.model.Internship;
import com.company.dashboard.model.ProofStatus;
import com.company.dashboard.repository.BankDetailsRepository;
import com.company.dashboard.repository.EmployeeFormRepository;
import com.company.dashboard.repository.EmployeeRepository;
import com.company.dashboard.repository.IdentityProofRepository;
import com.company.dashboard.response.BankDetailsDTO;
import com.company.dashboard.response.CertificationDTO;
import com.company.dashboard.response.EducationDTO;
import com.company.dashboard.response.ExperienceDTO;
import com.company.dashboard.response.InternshipDTO;
import com.company.dashboard.response.OnboardingRequestDTO;
import com.company.dashboard.response.OnboardingResponseDTO;
import com.company.dashboard.service.EmailService;
import com.company.dashboard.service.EmployeeCodeService;
import com.company.dashboard.service.FileStorageService;
import com.company.dashboard.service.OnboardingService;
import com.company.dashboard.util.FolderMappingUtil;

@Service
@Transactional
public class OnboardingServiceImpl implements OnboardingService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeFormRepository employeeFormRepository;
    private final IdentityProofRepository identityProofRepository;
    private final EmployeeServiceImpl employeeServiceimpl;
    private final BankDetailsRepository bankDetailsRepository;
    private final FileStorageService fileStorageService;
    private final EmployeeCodeService employeeCodeService;
    private final EmailService emailService;
    private final OnboardingTokenService onboardingTokenService;

    public OnboardingServiceImpl(
            EmployeeRepository employeeRepository,
            EmployeeFormRepository employeeFormRepository,
            IdentityProofRepository identityProofRepository,
            BankDetailsRepository bankDetailsRepository,
            EmployeeServiceImpl employeeServiceimpl,
            FileStorageService fileStorageService,
            EmployeeCodeService employeeCodeService,
            EmailService emailService,
            OnboardingTokenService onboardingTokenService) {

        this.employeeRepository = employeeRepository;
        this.employeeFormRepository = employeeFormRepository;
        this.identityProofRepository = identityProofRepository;
        this.bankDetailsRepository = bankDetailsRepository;
        this.employeeServiceimpl = employeeServiceimpl;
        this.fileStorageService = fileStorageService;
        this.employeeCodeService = employeeCodeService;
        this.emailService = emailService;
        this.onboardingTokenService = onboardingTokenService;
    }

    @Override
    public OnboardingResponseDTO submitOnboarding(
            OnboardingRequestDTO dto,
            Long employeeId,
            Map<String, MultipartFile> files,
            String token) throws IOException {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Check for existing form to update instead of creating a duplicate
        EmployeeForm form = employeeFormRepository.findByEmployee_Id(employeeId)
                .orElse(new EmployeeForm());

        // If updating, clear existing lists to avoid duplicates (orphanRemoval will handle deletion)
        if (form.getId() != null) {
            if (form.getEducations() != null) form.getEducations().clear(); else form.setEducations(new ArrayList<>());
            if (form.getInternships() != null) form.getInternships().clear(); else form.setInternships(new ArrayList<>());
            if (form.getExperiences() != null) form.getExperiences().clear(); else form.setExperiences(new ArrayList<>());
            if (form.getCertifications() != null) form.getCertifications().clear(); else form.setCertifications(new ArrayList<>());
            if (form.getIdentityProofs() != null) form.getIdentityProofs().clear(); else form.setIdentityProofs(new ArrayList<>());
        } else {
            // Initialize lists for new form
            form.setEducations(new ArrayList<>());
            form.setInternships(new ArrayList<>());
            form.setExperiences(new ArrayList<>());
            form.setCertifications(new ArrayList<>());
            form.setIdentityProofs(new ArrayList<>());
        }
        
        form.setEmployee(employee);

        // BASIC DETAILS
        form.setFullName(dto.getFullName());
        form.setEmail(dto.getEmail());
        form.setPhoneNumber(dto.getPhoneNumber());
        form.setDateOfBirth(dto.getDateOfBirth());
        form.setBloodGroup(dto.getBloodGroup());
        form.setPermanentAddress(dto.getPermanentAddress());
        form.setPresentAddress(dto.getPresentAddress());
        form.setFatherName(dto.getFathersName());
        form.setFatherPhone(dto.getFathersPhone());
        form.setMotherName(dto.getMothersName());
        form.setMotherPhone(dto.getMothersPhone());
        form.setEmergencyContactName(dto.getEmergencyContactName());
        form.setEmergencyRelationship(dto.getEmergencyRelationship());
        form.setEmergencyContactNumber(dto.getEmergencyNumber());

        // EDUCATION
        if (dto.getSsc() != null)
            form.getEducations().add(convertEducation(dto.getSsc(), EducationType.SSC, form));

        if (dto.getIntermediate() != null)
            form.getEducations().add(convertEducation(dto.getIntermediate(), EducationType.INTERMEDIATE, form));

        if (dto.getGraduations() != null) {
            for (int i = 0; i < dto.getGraduations().size(); i++) {
                EducationDTO grad = dto.getGraduations().get(i);

                // Files are already saved by the controller; we just use the paths from the DTO
                form.getEducations().add(convertEducation(grad, EducationType.GRADUATION, form));
            }
        }

        if (dto.getPostGraduations() != null) {
            for (int i = 0; i < dto.getPostGraduations().size(); i++) {
                EducationDTO pg = dto.getPostGraduations().get(i);
                form.getEducations().add(convertEducation(pg, EducationType.POST_GRADUATION, form));
            }
        }

        // INTERNSHIPS
        if (dto.getInternships() != null) {
            for (int i = 0; i < dto.getInternships().size(); i++) {
                InternshipDTO iDto = dto.getInternships().get(i);

                Internship internship = new Internship();
                internship.setCompanyName(iDto.getCompanyName());
                internship.setJoiningDate(iDto.getJoiningDate());
                internship.setRelievingDate(iDto.getRelievingDate());
                internship.setDuration(iDto.getDuration());
                internship.setEmployeeForm(form);

                if (iDto.getOfferLetterPath() != null) {
                    internship.setOfferLetterPath(iDto.getOfferLetterPath());
                }

                if (iDto.getExperienceCertificatePath() != null) {
                    internship.setExperienceCertificatePath(iDto.getExperienceCertificatePath());
                }

                form.getInternships().add(internship);
            }
        }

        // EXPERIENCES
        if (dto.getWorkExperiences() != null) {
            for (int i = 0; i < dto.getWorkExperiences().size(); i++) {
                ExperienceDTO exDto = dto.getWorkExperiences().get(i);

                Experience experience = new Experience();
                experience.setCompanyName(exDto.getCompanyName());
                experience.setYearsOfExperience(exDto.getYearsOfExperience());
                experience.setEmployeeForm(form);

                if (exDto.getOfferLetterPath() != null) {
                    experience.setOfferLetterPath(exDto.getOfferLetterPath());
                }

                if (exDto.getRelievingLetterPath() != null) {
                    experience.setRelievingLetterPath(exDto.getRelievingLetterPath());
                }

                if (exDto.getPayslipsPath() != null) {
                    experience.setPayslipsPath(exDto.getPayslipsPath());
                }

                if (exDto.getExperienceCertificatePath() != null) {
                    experience.setExperienceCertificatePath(exDto.getExperienceCertificatePath());
                }

                form.getExperiences().add(experience);
            }
        }

        // CERTIFICATIONS
        if (dto.getOtherCertificates() != null) {

            for (CertificationDTO certDto : dto.getOtherCertificates()) {

                boolean alreadyAdded = form.getCertifications().stream()
                        .anyMatch(c -> c.getInstituteName().equals(certDto.getInstituteName())
                                && Objects.equals(c.getCertificateNumber(), certDto.getCertificateNumber()));

                if (!alreadyAdded) {

                    Certification cert = new Certification();

                    cert.setInstituteName(certDto.getInstituteName());
                    cert.setCertificateNumber(certDto.getCertificateNumber());
                    cert.setCertificateFilePath(certDto.getCertificateFilePath());
                    cert.setEmployeeForm(form);

                    form.getCertifications().add(cert);
                }
            }
        }

        EmployeeForm savedForm = employeeFormRepository.save(form);

        if (dto.getBankDetails() != null) {

            BankDetailsDTO bankDto = dto.getBankDetails();

            // Reuse existing bank details if they exist
            BankDetails bank = (savedForm.getBankDetails() != null) ? savedForm.getBankDetails() : new BankDetails();

            bank.setBankName(bankDto.getBankName());
            bank.setBranchName(bankDto.getBranchName());
            bank.setAccountNumber(bankDto.getAccountNumber());
            bank.setIfscCode(bankDto.getIfscCode());
            bank.setUpiId(bankDto.getUpiId());
            bank.setDocumentType(bankDto.getDocumentType());
            bank.setDocumentFilePath(bankDto.getDocumentFilePath());

            bank.setEmployeeForm(savedForm);
            bank.setStatus(ProofStatus.PENDING);

            bankDetailsRepository.save(bank);
            savedForm.setBankDetails(bank);
        }

        IdentityProof proof = new IdentityProof();
        proof.setEmployeeForm(savedForm);
        proof.setStatus(ProofStatus.PENDING);

        if (dto.getPanProof() != null)
            proof.setPanNumber(dto.getPanProof().getPanNumber());
        if (dto.getAadharProof() != null)
            proof.setAadhaarNumber(dto.getAadharProof().getAadhaarNumber());

        if (dto.getPanProof() != null)
            proof.setPanFilePath(dto.getPanProof().getPanFilePath());
        if (dto.getAadharProof() != null)
            proof.setAadhaarFilePath(dto.getAadharProof().getAadhaarFilePath());
        if (dto.getPassportProof() != null)
            proof.setPassportFilePath(dto.getPassportProof().getPassportFilePath());
        if (dto.getVoterProof() != null)
            proof.setVoterIdFilePath(dto.getVoterProof().getVoterIdFilePath());
        if (dto.getPhotoProof() != null)
            proof.setPhotoFilePath(dto.getPhotoProof().getPhotoFilePath());

        savedForm.getIdentityProofs().add(proof);
        identityProofRepository.save(proof);

        // FINALIZING SUBMISSION (Integrated inside the transaction)
        employee.setStatus(Employee.EmployeeStatus.UNDER_REVIEW);
        employeeRepository.save(employee);
        
        // Mark token as used
        if (token != null) {
            onboardingTokenService.markUsed(token);
        }

        return buildResponse(savedForm);
    }

    private Education convertEducation(EducationDTO dto, EducationType type, EmployeeForm form) {

        Education e = new Education();

        e.setEducationType(type);
        e.setInstitutionName(dto.getInstitutionName());
        e.setHallTicketNumber(dto.getHallTicketNumber());
        e.setPassoutYear(dto.getPassoutYear());
        e.setPercentage(dto.getPercentage());
        e.setCertificateFilePath(dto.getCertificateFilePath());
        e.setMarksMemoFilePath(dto.getMarksMemoFilePath());
        e.setEmployeeForm(form);

        return e;
    }

    private OnboardingResponseDTO buildResponse(EmployeeForm form) {
        return OnboardingResponseDTO.fromEntity(form);
    }

    @Override
    public Optional<EmployeeForm> findOnboardingById(Long id) {
        return employeeFormRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Map<String, String>> submitReview(Long employeeId,
            String status,
            String remarks) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setReviewRemarks(remarks);
        employee.setReviewedAt(LocalDateTime.now());
        
        List<Map<String, String>> responseList = new ArrayList<>();

        if ("APPROVED".equalsIgnoreCase(status)) {

            if (employee.getStatus() != Employee.EmployeeStatus.ACTIVE) {
                employee.setStatus(Employee.EmployeeStatus.ACTIVE);
                employee.setActivatedAt(LocalDateTime.now());
                employeeRepository.save(employee);

                employeeCodeService.scheduleEmployeeCode(employee);
            }

            emailService.sendMail(
                    employee.getEmail(),
                    "Onboarding Approved",
                    "Hi " + employee.getFullName() + ",\n\n" +
                            "Your onboarding has been approved successfully.\n\n" +
                            "Welcome onboard!\n\nHR Department");

        }

        else if ("REJECTED".equalsIgnoreCase(status)) {

            List<String> rejectedDocs = employee.getRejectedDocuments();

            if (rejectedDocs == null || rejectedDocs.isEmpty()) {
                throw new RuntimeException("At least one document must be rejected before performing an overall reject.");
            }

            // Update: EMPLOYEE_STATUS = ONBOARDING; (Per user requirement)
            employee.setStatus(Employee.EmployeeStatus.ONBOARDING);
            employee.setOnboardingRejected(true);

            // Fetch all rejected images: image_name, reject_reason 
            StringBuilder formattedList = new StringBuilder();
            for (int i = 0; i < rejectedDocs.size(); i++) {
                String doc = rejectedDocs.get(i);
                formattedList.append(i + 1).append(". ").append(doc).append("\n");
                
                String[] parts = doc.split(" - ", 2);
                Map<String, String> map = new java.util.HashMap<>();
                map.put("image_name", parts[0]);
                map.put("reject_reason", parts.length > 1 ? parts[1] : "");
                responseList.add(map);
            }

            // Must use onboardingTokenService to generate and persist a valid token
            String newToken = onboardingTokenService.generateToken(employee.getId());
            employee.setOnboardingToken(newToken);

            emailService.sendRejectedOnboardingMail(
                    employee.getEmail(),
                    employee.getFullName(),
                    newToken,
                    formattedList.toString());

            // We do not clear the list here so it is available during re-submission.
            // clearRejectedDocuments(employeeId);
        }

        else if ("UNDER_REVIEW".equalsIgnoreCase(status)) {
            employee.setStatus(Employee.EmployeeStatus.UNDER_REVIEW);
        }

        else {
            throw new IllegalArgumentException("Invalid status");
        }

        employeeRepository.save(employee);
        
        return responseList;
    }

    @Override
    @Transactional
    public void rejectDocument(Long employeeId,
            String entityType,
            Long entityId,
            String remarks) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeForm form = employeeFormRepository.findByEmployee_Id(employeeId)
                .orElseThrow(() -> new RuntimeException("Onboarding form not found for this employee"));

        String documentName = mapEntityTypeToDocument(entityType);
        
        // 1. Update the specific entity record
        updateSubEntityStatus(form, entityType, entityId, ProofStatus.REJECTED, remarks);

        // 2. Update the string list on Employee for quick lookups
        List<String> rejectedDocs = employee.getRejectedDocuments();
        if (rejectedDocs == null) {
            rejectedDocs = new ArrayList<>();
        }
        
        String entry = documentName + (remarks != null ? " - " + remarks : "");
        if (!rejectedDocs.contains(entry)) {
            rejectedDocs.add(entry);
        }

        employee.setRejectedDocuments(rejectedDocs);
        employeeRepository.save(employee);
    }

    private void updateSubEntityStatus(EmployeeForm form, String type, Long id, ProofStatus status, String remarks) {
        if (type == null) return;
        
        switch (type.toLowerCase()) {
            case "bank":
                if (form.getBankDetails() != null) {
                    form.getBankDetails().setStatus(status);
                    form.getBankDetails().setRejectionReason(remarks);
                    form.getBankDetails().setReviewedAt(LocalDateTime.now());
                }
                break;
            case "pan":
            case "aadhaar":
            case "aadhar":
            case "passport":
            case "voter":
            case "photo":
            case "identity":
                if (form.getIdentityProofs() != null) {
                    form.getIdentityProofs().stream()
                        .filter(p -> Objects.equals(p.getId(), id))
                        .findFirst()
                        .ifPresent(p -> {
                            p.setStatus(status);
                            p.setRejectionReason(remarks);
                            p.setReviewedAt(LocalDateTime.now());
                        });
                }
                break;
            case "education":
            case "ssc":
            case "inter":
            case "graduation":
            case "postgraduation":
                if (form.getEducations() != null) {
                    form.getEducations().stream()
                        .filter(e -> Objects.equals(e.getId(), id))
                        .findFirst()
                        .ifPresent(e -> {
                            e.setStatus(status);
                            e.setRejectionReason(remarks);
                            e.setReviewedAt(LocalDateTime.now());
                        });
                }
                break;
            case "certification":
                if (form.getCertifications() != null) {
                    form.getCertifications().stream()
                        .filter(c -> Objects.equals(c.getId(), id))
                        .findFirst()
                        .ifPresent(c -> {
                            c.setStatus(status);
                            c.setRejectionReason(remarks);
                            c.setReviewedAt(LocalDateTime.now());
                        });
                }
                break;
            case "internship":
                if (form.getInternships() != null) {
                    form.getInternships().stream()
                        .filter(i -> Objects.equals(i.getId(), id))
                        .findFirst()
                        .ifPresent(i -> {
                            i.setStatus(status);
                            i.setRejectionReason(remarks);
                            i.setReviewedAt(LocalDateTime.now());
                        });
                }
                break;
            case "experience":
                if (form.getExperiences() != null) {
                    form.getExperiences().stream()
                        .filter(ex -> Objects.equals(ex.getId(), id))
                        .findFirst()
                        .ifPresent(ex -> {
                            ex.setStatus(status);
                            ex.setRejectionReason(remarks);
                            ex.setReviewedAt(LocalDateTime.now());
                        });
                }
                break;
        }
        employeeFormRepository.save(form);
    }

    private String mapEntityTypeToDocument(String entityType) {

        if (entityType == null) {
            return "Unknown Document";
        }

        switch (entityType.toLowerCase()) {

            case "education":
                return "Education Certificate";

            case "experience":
                return "Experience Documents";

            case "bank":
                return "Bank Proof";

            case "pan":
                return "PAN Card";

            case "aadhaar":
                return "Aadhaar Card";

            case "passport":
                return "Passport";

            case "voter":
                return "Voter ID";

            case "identity":
                return "Identity Proof";

            default:
                return entityType;
        }
    }

    @Override
    public List<String> getRejectedDocuments(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getRejectedDocuments() == null) {
            return new ArrayList<>();
        }

        return employee.getRejectedDocuments();
    }

    @Override
    public void clearRejectedDocuments(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setRejectedDocuments(new ArrayList<>());

        employeeRepository.save(employee);
    }

}