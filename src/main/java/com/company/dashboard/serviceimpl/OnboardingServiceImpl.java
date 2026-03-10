package com.company.dashboard.serviceimpl;

import java.io.IOException;
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

import com.company.dashboard.service.EmployeeCodeService;
import com.company.dashboard.service.EmailService;

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
import com.company.dashboard.model.Employee.EmployeeStatus;
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
import com.company.dashboard.service.OnboardingService;

@Service
@Transactional
public class OnboardingServiceImpl implements OnboardingService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeFormRepository employeeFormRepository;
    private final IdentityProofRepository identityProofRepository;
    private final EmployeeServiceImpl employeeServiceimpl;
    private final BankDetailsRepository bankDetailsRepository;

    @Value("${app.file.base-url:http://localhost:8090}")
    private String baseUrl;

    @Value("${onboarding.upload.dir:C:/onboard/uploads}")
    private String uploadDir;
    
    private final EmployeeCodeService employeeCodeService;
    private final EmailService emailService;

    public OnboardingServiceImpl(
            EmployeeRepository employeeRepository,
            EmployeeFormRepository employeeFormRepository,
            IdentityProofRepository identityProofRepository,
            BankDetailsRepository bankDetailsRepository,
            EmployeeServiceImpl employeeServiceimpl,
            EmployeeCodeService employeeCodeService,
            EmailService emailService) {

        this.employeeRepository = employeeRepository;
        this.employeeFormRepository = employeeFormRepository;
        this.identityProofRepository = identityProofRepository;
        this.bankDetailsRepository = bankDetailsRepository;
        this.employeeServiceimpl = employeeServiceimpl;
        this.employeeCodeService = employeeCodeService;
        this.emailService = emailService;
    }

    @Override
    public OnboardingResponseDTO submitOnboarding(
            OnboardingRequestDTO dto,
            Long employeeId,
            Map<String, MultipartFile> files) throws IOException {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeForm form = new EmployeeForm();
        form.setEmployee(employee);

        if (form.getEducations() == null) form.setEducations(new ArrayList<>());
        if (form.getInternships() == null) form.setInternships(new ArrayList<>());
        if (form.getExperiences() == null) form.setExperiences(new ArrayList<>());
        if (form.getCertifications() == null) form.setCertifications(new ArrayList<>());
        if (form.getIdentityProofs() == null) form.setIdentityProofs(new ArrayList<>());

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

                if (files != null && files.containsKey("grad_certificate_" + i)) {
                    grad.setCertificateFilePath(
                            saveFileToFolder(files.get("grad_certificate_" + i), employeeId, "education"));
                }

                // NEW SUPPORT FOR GRADUATION MARKS MEMO
                if (files != null && files.containsKey("grad_marks_" + i)) {
                    grad.setMarksMemoFilePath(
                            saveFileToFolder(files.get("grad_marks_" + i), employeeId, "education"));
                }

                form.getEducations().add(convertEducation(grad, EducationType.GRADUATION, form));
            }
        }

        if (dto.getPostGraduations() != null) {
            for (int i = 0; i < dto.getPostGraduations().size(); i++) {
                EducationDTO pg = dto.getPostGraduations().get(i);
                if (files != null && files.containsKey("postgrad_certificate_" + i)) {
                    pg.setCertificateFilePath(
                            saveFileToFolder(files.get("postgrad_certificate_" + i), employeeId, "education"));
                }
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

                if (files != null) {
                    if (files.containsKey("internship_offer_letter_" + i)) {
                        internship.setOfferLetterPath(
                                saveFileToFolder(files.get("internship_offer_letter_" + i), employeeId, "internship"));
                    }

                    if (files.containsKey("internship_experience_certificate_" + i)) {
                        internship.setExperienceCertificatePath(
                                saveFileToFolder(files.get("internship_experience_certificate_" + i), employeeId, "internship"));
                    }
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

                if (files != null) {

                    if (files.containsKey("experience_offer_letter_" + i)) {
                        experience.setOfferLetterPath(
                                saveFileToFolder(files.get("experience_offer_letter_" + i), employeeId, "experience"));
                    }

                    if (files.containsKey("experience_relieving_letter_" + i)) {
                        experience.setRelievingLetterPath(
                                saveFileToFolder(files.get("experience_relieving_letter_" + i), employeeId, "experience"));
                    }

                    if (files.containsKey("experience_payslips_" + i)) {
                        experience.setPayslipsPath(
                                saveFileToFolder(files.get("experience_payslips_" + i), employeeId, "experience"));
                    }

                    if (files.containsKey("experience_certificate_" + i)) {
                        experience.setExperienceCertificatePath(
                                saveFileToFolder(files.get("experience_certificate_" + i), employeeId, "experience"));
                    }
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

            BankDetails bank = new BankDetails();

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
        }
        
        
        
        IdentityProof proof = new IdentityProof();
        proof.setEmployeeForm(savedForm);
        proof.setStatus(ProofStatus.PENDING);

        if (dto.getPanProof() != null) proof.setPanNumber(dto.getPanProof().getPanNumber());
        if (dto.getAadharProof() != null) proof.setAadhaarNumber(dto.getAadharProof().getAadhaarNumber());

        if (files != null) {

            for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {

                String key = entry.getKey().toLowerCase();
                MultipartFile file = entry.getValue();

                if (file == null || file.isEmpty()) continue;

                String savedPath = saveFileToFolder(file, employeeId, key);

                switch (key) {

                    case "pan":
                        proof.setPanFilePath(savedPath);
                        break;

                    case "aadhaar":
                        proof.setAadhaarFilePath(savedPath);
                        break;

                    case "passport":
                        proof.setPassportFilePath(savedPath);
                        break;

                    case "voter":
                        proof.setVoterIdFilePath(savedPath);
                        break;

                    case "photo":
                        proof.setPhotoFilePath(savedPath);
                        break;
                }
            }
        }

        savedForm.getIdentityProofs().add(proof);

        identityProofRepository.save(proof);

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

    private String saveFileToFolder(MultipartFile file, Long employeeId, String folderName) {

        try {

            Path folderPath = Paths.get(uploadDir, folderName).toAbsolutePath().normalize();

            Files.createDirectories(folderPath);

            String original = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();

            String sanitized = original.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");

            String fileName = employeeId + "_" + System.currentTimeMillis() + "_" + sanitized;

            Path targetPath = folderPath.resolve(fileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return folderName + "/" + fileName;

        } catch (IOException e) {

            throw new RuntimeException("Failed to save file: " + file.getOriginalFilename(), e);
        }
    }

    private OnboardingResponseDTO buildResponse(EmployeeForm form) {
        return OnboardingResponseDTO.fromEntity(form);
    }

    private String buildFileUrl(String path) {
        if (path == null) return null;
        return baseUrl + "/api/files/" + path;
    }

    @Override
    public Optional<EmployeeForm> findOnboardingById(Long id) {
        return employeeFormRepository.findById(id);
    }

    @Override
    @Transactional
    public void submitReview(Long employeeId,
                             String status,
                             String remarks,
                             List<String> rejectedDocuments) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setReviewRemarks(remarks);
        employee.setReviewedAt(LocalDateTime.now());

        if ("APPROVED".equalsIgnoreCase(status)) {

            if (employee.getStatus() != Employee.EmployeeStatus.ACTIVE) {
                employee.setStatus(Employee.EmployeeStatus.ACTIVE);
                employee.setActivatedAt(LocalDateTime.now());
                employeeRepository.save(employee);

                // 🔹 Schedule Employee ID generation after 1 minute
                employeeCodeService.scheduleEmployeeCode(employee);
                System.out.println("Employee scheduled for code generation: " + employee.getEmail());
                }

            emailService.sendMail(
                employee.getEmail(),
                "Onboarding Approved",
                "Hi " + employee.getFullName() + ",\n\n" +
                "Your onboarding has been approved successfully.\n\n" +
                "Welcome onboard!\n\nHR Department"
            );

        } else if ("REJECTED".equalsIgnoreCase(status)) {
            employee.setStatus(Employee.EmployeeStatus.ONBOARDING);
            employee.setRejectedDocuments(rejectedDocuments);

            String newToken = UUID.randomUUID().toString();
            employee.setOnboardingToken(newToken);

            emailService.sendRejectedOnboardingMail(
                    employee.getEmail(),
                    employee.getFullName(),
                    newToken,
                    String.join(", ", rejectedDocuments)
            );

        } else if ("UNDER_REVIEW".equalsIgnoreCase(status)) {
            employee.setStatus(Employee.EmployeeStatus.UNDER_REVIEW);
        } else {
            throw new IllegalArgumentException("Invalid status value");
        }

        employeeRepository.save(employee);
    }
    
    @Override
    public void rejectDocument(Long employeeId, String entityType, Long entityId, String remarks) {
        if (employeeId == null || entityType == null || entityId == null) {
            throw new IllegalArgumentException("employeeId, entityType and entityId are required");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

        // --- Add to review remarks ---
        String rejectionInfo = String.format(
                "Rejected document: %s (ID: %d) - %s",
                entityType, entityId, remarks != null ? remarks : "No reason provided"
        );

        String currentRemarks = employee.getReviewRemarks();
        employee.setReviewRemarks(
                currentRemarks == null || currentRemarks.trim().isEmpty()
                        ? rejectionInfo
                        : currentRemarks + "\n" + rejectionInfo
        );

        employee.setReviewedAt(LocalDateTime.now());
        employee.setStatus(EmployeeStatus.ONBOARDING);

        // --- Keep track of rejected documents ---
        List<String> rejectedDocs = employee.getRejectedDocuments();
        if (rejectedDocs == null) rejectedDocs = new ArrayList<>();
        rejectedDocs.add(entityType);
        employee.setRejectedDocuments(rejectedDocs);

        // --- Generate new onboarding token for re-upload ---
        String newToken = UUID.randomUUID().toString();
        employee.setOnboardingToken(newToken);

        employeeRepository.save(employee);

        // --- Send rejection email with onboarding link ---
        emailService.sendRejectedOnboardingMail(
                employee.getEmail(),
                employee.getFullName(),
                newToken,
                String.join(", ", rejectedDocs)
        );

        System.out.println("[REJECT DOCUMENT] " + rejectionInfo + " for employee " + employeeId);
    }

}