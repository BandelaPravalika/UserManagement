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
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.Certification;
import com.company.dashboard.model.Education;
import com.company.dashboard.model.Employee;
import com.company.dashboard.model.Employee.EmployeeStatus;
import com.company.dashboard.model.EmployeeForm;
import com.company.dashboard.model.IdentityProof;
import com.company.dashboard.model.Internship;
import com.company.dashboard.model.WorkExperience;
import com.company.dashboard.repository.CertificationRepository;
import com.company.dashboard.repository.EducationRepository;
import com.company.dashboard.repository.EmployeeFormRepository;
import com.company.dashboard.repository.EmployeeRepository;
import com.company.dashboard.repository.IdentityProofRepository;
import com.company.dashboard.repository.InternshipRepository;
import com.company.dashboard.repository.WorkExperienceRepository;
import com.company.dashboard.response.EducationDTO;
import com.company.dashboard.response.IdentityProofDTO;
import com.company.dashboard.response.InternshipDTO;
import com.company.dashboard.response.OnboardingRequestDTO;
import com.company.dashboard.response.OnboardingResponseDTO;
import com.company.dashboard.response.WorkExperienceDTO;
import com.company.dashboard.service.EmailService;
import com.company.dashboard.service.EmployeeCodeService;
import com.company.dashboard.service.EmployeeService;
import com.company.dashboard.service.OnboardingService;

@Service
@Transactional
public class OnboardingServiceImpl implements OnboardingService {

    private final EmployeeFormRepository employeeFormRepository;
    private final EducationRepository educationRepository;
    private final CertificationRepository certificationRepository;
    private final InternshipRepository internshipRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final IdentityProofRepository identityProofRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final EmployeeService employeeService;
    private final EmployeeCodeService employeeCodeService;
    
    private final Path baseUploadDir = Paths.get("C:/onboard/uploads").toAbsolutePath().normalize();
    public OnboardingServiceImpl(
            EmployeeFormRepository employeeFormRepository,
            EducationRepository educationRepository,
            CertificationRepository certificationRepository,
            InternshipRepository internshipRepository,
            WorkExperienceRepository workExperienceRepository,
            IdentityProofRepository identityProofRepository,
            EmployeeRepository employeeRepository,
            EmailService emailService,
            EmployeeService employeeService,
            EmployeeCodeService employeeCodeService) {

        this.employeeFormRepository = employeeFormRepository;
        this.educationRepository = educationRepository;
        this.certificationRepository = certificationRepository;
        this.internshipRepository = internshipRepository;
        this.workExperienceRepository = workExperienceRepository;
        this.identityProofRepository = identityProofRepository;
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
        this.employeeService = employeeService;
        this.employeeCodeService = employeeCodeService;
    }

    // ================== MAIN METHOD ==================
    @Override
    public OnboardingResponseDTO submitOnboarding(
            OnboardingRequestDTO dto,
            Long employeeId,
            Map<String, MultipartFile> files) throws IOException {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        EmployeeForm form = new EmployeeForm();
        mapBasicDetails(dto, form);

        if (dto.getPanProof() != null) {
            form.setPanNumber(dto.getPanProof().getDocumentNumber());
        }
        if (dto.getAadharProof() != null) {
            form.setAadharNumber(dto.getAadharProof().getDocumentNumber());
        }

        form.setEmployee(employee);
        EmployeeForm savedForm = employeeFormRepository.save(form);

        // =================== FILE UPLOAD ===================
        if (files != null) {
            files.forEach((key, file) -> {
                if (file != null && !file.isEmpty()) {
                    try {
                        String filePath = saveFile(file, key.toLowerCase(), savedForm.getId());

                        switch (key.toLowerCase()) {
                            case "passbook":
                                savedForm.setPassbookPath(filePath);
                                break;
                            case "photo":
                                savedForm.setPhotoPath(filePath);
                                break;
                            case "pan":
                                IdentityProof panProof = new IdentityProof();
                                panProof.setProofType("PAN");
                                panProof.setFilePath(filePath);
                                if (dto.getPanProof() != null)
                                    panProof.setDocumentNumber(dto.getPanProof().getDocumentNumber());
                                savedForm.setPanNumber(panProof.getDocumentNumber());
                                panProof.setEmployeeForm(savedForm);
                                identityProofRepository.save(panProof);
                                break;
                            case "aadhar":
                                IdentityProof aadharProof = new IdentityProof();
                                aadharProof.setProofType("AADHAR");
                                aadharProof.setFilePath(filePath);
                                if (dto.getAadharProof() != null)
                                    aadharProof.setDocumentNumber(dto.getAadharProof().getDocumentNumber());
                                savedForm.setAadharNumber(aadharProof.getDocumentNumber());
                                aadharProof.setEmployeeForm(savedForm);
                                identityProofRepository.save(aadharProof);
                                break;
                            default:
                                IdentityProof proof = new IdentityProof();
                                proof.setProofType(key.toUpperCase());
                                proof.setFilePath(filePath);
                                proof.setEmployeeForm(savedForm);
                                identityProofRepository.save(proof);
                                break;
                        }

                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save file: " + key, e);
                    }
                }
            });
        }

        employeeFormRepository.save(savedForm);

        // =================== OTHER ENTITIES ===================
        saveEducations(dto, savedForm);
        saveCertifications(dto, savedForm);
        saveInternships(dto, savedForm);
        saveWorkExperiences(dto, savedForm);
        saveIdentityProofs(dto, savedForm);

        // =================== BUILD RESPONSE ===================
        OnboardingResponseDTO response = new OnboardingResponseDTO();

     // Basic employee info
     response.setId(savedForm.getId());
     response.setEmpId(employee.getEmpId());
     response.setFullName(savedForm.getFullName());
     response.setEmail(savedForm.getEmail());
     response.setPhoneNumber(savedForm.getPhoneNumber());
     response.setBankName(savedForm.getBankName());
     response.setBranchName(savedForm.getBranchName());
     response.setAccountNumber(savedForm.getAccountNumber());
     response.setIfscCode(savedForm.getIfscCode());
     response.setUpiId(savedForm.getUpiId());

     // Initialize flattened proof fields
     String panNumber = null;
     String aadharNumber = null;

     // Map IdentityProofDTOs
  // Holder for mutable variables
     class ProofHolder {
         String panNumber;
         String aadharNumber;
     }

     ProofHolder holder = new ProofHolder();

     List<IdentityProofDTO> proofDTOs = savedForm.getIdentityProofs().stream()
             .map(p -> {
                 IdentityProofDTO dtoProof = new IdentityProofDTO();
                 dtoProof.setProofType(p.getProofType());
                 dtoProof.setDocumentNumber(p.getDocumentNumber());
                 dtoProof.setFilePath(buildFileUrl(p.getFilePath()));

                 // Flatten key proofs
                 switch (p.getProofType()) {
                     case "PAN" -> {
                         holder.panNumber = p.getDocumentNumber();
                         response.setPanPath(dtoProof.getFilePath());
                     }
                     case "AADHAR" -> {
                         holder.aadharNumber = p.getDocumentNumber();
                         response.setAadharPath(dtoProof.getFilePath());
                     }
                     case "PHOTO" -> response.setPhotoPath(dtoProof.getFilePath());
                     case "PASSPORT" -> response.setPassportPath(dtoProof.getFilePath());
                     case "VOTER" -> response.setVoterPath(dtoProof.getFilePath());
                     case "PASSBOOK" -> response.setPassbookPath(dtoProof.getFilePath());
                 }

                 return dtoProof;
             })
             .collect(Collectors.toList());

     response.setPanNumber(holder.panNumber);
     response.setAadharNumber(holder.aadharNumber);
     response.setIdentityProofs(proofDTOs);
        List<WorkExperienceDTO> workDTOs = savedForm.getWorkExperiences().stream()
                .map(w -> {
                    WorkExperienceDTO dtoWork = new WorkExperienceDTO();
                    dtoWork.setCompanyName(w.getCompanyName());
                    dtoWork.setOfferLetterPath(buildFileUrl(w.getOfferLetterPath()));
                    dtoWork.setRelievingLetterPath(buildFileUrl(w.getRelievingLetterPath()));
                    dtoWork.setPayslipsPath(buildFileUrl(w.getPayslipsPath()));
                    dtoWork.setExperienceCertificatePath(buildFileUrl(w.getExperienceCertificatePath()));
                    return dtoWork;
                }).collect(Collectors.toList());
        response.setWorkExperiences(workDTOs);

        return response;
    }

    private String buildFileUrl(String fileName) {
        if (fileName == null) return null;
        return "http://localhost:8080/api/onboarding/files/" + fileName;    }


    private String saveFile(MultipartFile file, String key, Long formId) throws IOException {
        Files.createDirectories(baseUploadDir);
        String safeName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
        String filename = formId + "_" + key + "_" + safeName;
        Path target = baseUploadDir.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }


    
    
    // ================== BASIC DETAILS ==================
    private void mapBasicDetails(OnboardingRequestDTO dto, EmployeeForm form) {
        form.setFullName(dto.getFullName());
        form.setEmail(dto.getEmail());
        form.setPhoneNumber(dto.getPhoneNumber());
        form.setDateOfBirth(dto.getDateOfBirth());
        form.setBloodGroup(dto.getBloodGroup());
        form.setPermanentAddress(dto.getPermanentAddress());
        form.setPresentAddress(dto.getPresentAddress());
        form.setFathersName(dto.getFathersName());
        form.setFathersPhone(dto.getFathersPhone());
        form.setMothersName(dto.getMothersName());
        form.setMothersPhone(dto.getMothersPhone());
        form.setEmergencyContactName(dto.getEmergencyContactName());
        form.setEmergencyRelationship(dto.getEmergencyRelationship());
        form.setEmergencyNumber(dto.getEmergencyNumber());
        form.setBankName(dto.getBankName());
        form.setBranchName(dto.getBranchName());
        form.setAccountNumber(dto.getAccountNumber());
        form.setIfscCode(dto.getIfscCode());
        form.setUpiId(dto.getUpiId());
        if (dto.getPanProof() != null)
            form.setPanNumber(dto.getPanProof().getDocumentNumber());

        if (dto.getAadharProof() != null)
            form.setAadharNumber(dto.getAadharProof().getDocumentNumber());
        }

    // ================== EDUCATION ==================
    private void saveEducations(OnboardingRequestDTO dto, EmployeeForm form) {

        List<Education> educations = new ArrayList<>();

        if (dto.getSsc() != null)
            educations.add(mapToEducation(dto.getSsc(), "SSC", form));

        if (dto.getIntermediate() != null)
            educations.add(mapToEducation(dto.getIntermediate(), "INTERMEDIATE", form));

        if (dto.getGraduation() != null)
            educations.add(mapToEducation(dto.getGraduation(), "GRADUATION", form));

        if (dto.getPostGraduations() != null) {
            educations.addAll(dto.getPostGraduations().stream()
                    .filter(Objects::nonNull)
                    .map(pg -> mapToEducation(pg, "POST_GRADUATION", form))
                    .collect(Collectors.toList()));
        }

        educationRepository.saveAll(educations);
    }

    private Education mapToEducation(EducationDTO dto, String type, EmployeeForm form) {

        Education edu = new Education();
        edu.setEducationType(type);
        edu.setInstitutionName(dto.getInstitutionName());
        edu.setHallTicketNo(dto.getHallTicketNo());
        edu.setPassoutYear(dto.getPassoutYear());
        edu.setPercentageCgpa(dto.getPercentageCgpa());
        edu.setCertificatePath(dto.getCertificatePath());
        edu.setMarksMemoPath(dto.getMarksMemoPath());
        edu.setEmployeeForm(form);

        return edu;
    }

    // ================== CERTIFICATIONS ==================
    private void saveCertifications(OnboardingRequestDTO dto, EmployeeForm form) {

        if (dto.getOtherCertificates() == null) return;

        List<Certification> list = dto.getOtherCertificates().stream()
                .filter(Objects::nonNull)
                .map(certDto -> {
                    Certification c = new Certification();
                    c.setInstituteName(certDto.getInstituteName());
                    c.setCertificateNumber(certDto.getCertificateNumber());
                    c.setCertificatePath(certDto.getCertificatePath());
                    c.setEmployeeForm(form);
                    return c;
                }).collect(Collectors.toList());

        certificationRepository.saveAll(list);
    }

    // ================== INTERNSHIPS ==================
    private void saveInternships(OnboardingRequestDTO dto, EmployeeForm form) {

        if (dto.getInternships() == null) return;

        List<Internship> list = dto.getInternships().stream()
                .filter(Objects::nonNull)
                .map(iDto -> {
                    Internship i = new Internship();
                    i.setCompanyName(iDto.getCompanyName());
                    i.setJoiningDate(iDto.getJoiningDate());
                    i.setRelievingDate(iDto.getRelievingDate());
                    i.setInternshipId(iDto.getInternshipId());
                    i.setDuration(iDto.getDuration());
                    i.setOfferLetterPath(iDto.getOfferLetterPath());
                    i.setExperienceCertificatePath(iDto.getExperienceCertificatePath());
                    i.setEmployeeForm(form);
                    return i;
                }).collect(Collectors.toList());

        internshipRepository.saveAll(list);
    }

    // ================== WORK EXPERIENCE ==================
    private void saveWorkExperiences(OnboardingRequestDTO dto, EmployeeForm form) {

        if (dto.getWorkExperiences() == null) return;

        List<WorkExperience> list = dto.getWorkExperiences().stream()
                .filter(Objects::nonNull)
                .map(wDto -> {
                    WorkExperience w = new WorkExperience();
                    w.setCompanyName(wDto.getCompanyName());
                    w.setYearsOfExp(wDto.getYearsOfExp());
                    w.setOfferLetterPath(wDto.getOfferLetterPath());
                    w.setRelievingLetterPath(wDto.getRelievingLetterPath());
                    w.setPayslipsPath(wDto.getPayslipsPath());
                    w.setExperienceCertificatePath(wDto.getExperienceCertificatePath());
                    w.setEmployeeForm(form);
                    return w;
                }).collect(Collectors.toList());

        workExperienceRepository.saveAll(list);
    }

    // ================== IDENTITY PROOFS ==================
    private void saveIdentityProofs(OnboardingRequestDTO dto, EmployeeForm form) {

        addProofIfPresent(dto.getPanProof(), "PAN", form);
        addProofIfPresent(dto.getAadharProof(), "AADHAR", form);
        addProofIfPresent(dto.getPhotoProof(), "PHOTO", form);
        addProofIfPresent(dto.getPassportProof(), "PASSPORT", form);
        addProofIfPresent(dto.getVoterProof(), "VOTER", form);
        addProofIfPresent(dto.getPanProof(), "PAN", form);
        addProofIfPresent(dto.getAadharProof(), "AADHAR", form);
        }

    private void addProofIfPresent(IdentityProofDTO dto, String type, EmployeeForm form) {

        if (dto == null) return;

        IdentityProof proof = new IdentityProof();
        proof.setProofType(type);
        proof.setFilePath(dto.getFilePath());
        proof.setDocumentNumber(dto.getDocumentNumber());
        proof.setEmployeeForm(form);

        identityProofRepository.save(proof);
    }

    // ================== FIND ==================
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