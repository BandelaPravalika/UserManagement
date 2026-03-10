package com.company.dashboard.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.Employee;
import com.company.dashboard.model.EmployeeForm;
import com.company.dashboard.repository.EmployeeRepository;
import com.company.dashboard.response.*;
import com.company.dashboard.service.FileStorageService;
import com.company.dashboard.service.OnboardingService;
import com.company.dashboard.service.OnboardingTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/api/onboarding")
@CrossOrigin("*")
//@CrossOrigin(origins = "http://localhost:5173")
public class OnboardingController {

    private final OnboardingService onboardingService;
    private final OnboardingTokenService tokenService;
    private final EmployeeRepository employeeRepo;
    private final FileStorageService fileStorageService;
    private final Path baseUploadDir;

    @Value("${app.file.base-url:http://localhost:8090}")
    private String baseUrl;

    public OnboardingController(
            OnboardingService onboardingService,
            OnboardingTokenService tokenService,
            EmployeeRepository employeeRepo,
            FileStorageService fileStorageService,
            @Value("${onboarding.upload.dir:C:/onboard/uploads}") String uploadDirPath
    ) throws IOException {
        this.onboardingService = onboardingService;
        this.tokenService = tokenService;
        this.employeeRepo = employeeRepo;
        this.fileStorageService = fileStorageService;

        this.baseUploadDir = Paths.get(uploadDirPath).toAbsolutePath().normalize();
        if (!Files.exists(baseUploadDir)) Files.createDirectories(baseUploadDir);
    }

    // ───────── SERVE FILE ─────────
    @GetMapping("/files/{path:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String path) throws IOException {
        Path file = baseUploadDir.resolve(path).normalize();
        if (!file.startsWith(baseUploadDir)) return ResponseEntity.badRequest().build();
        if (!Files.exists(file)) return ResponseEntity.notFound().build();

        Resource resource = new UrlResource(file.toUri());
        String contentType = Files.probeContentType(file);
        if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // ───────── LIST FILES ─────────
    @GetMapping("/files")
    public List<String> listFiles() throws IOException {
        if (!Files.exists(baseUploadDir)) return List.of();
        return Files.walk(baseUploadDir)
                .filter(Files::isRegularFile)
                .map(path -> baseUploadDir.relativize(path).toString().replace("\\", "/"))
                .map(path -> "/api/onboarding/files/" + path)
                .toList();
    }

    // ───────── SUBMIT ONBOARDING ─────────
    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitOnboarding(
            @RequestPart("data") String data,
            @RequestParam Map<String, MultipartFile> multipartFiles,
            @RequestParam("token") String token
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            OnboardingRequestDTO dto = mapper.readValue(data, OnboardingRequestDTO.class);

            Long employeeId = tokenService.validateToken(token);
            if (employeeId == null) return ResponseEntity.badRequest().body("Invalid token");

            // ───────── HANDLE FILES ─────────
            Map<String, MultipartFile> files = new HashMap<>();
            if (multipartFiles != null) {
                multipartFiles.forEach((k, v) -> {
                    if (v != null && !v.isEmpty()) files.put(k.toLowerCase(), v);
                });
            }

            files.forEach((key, file) -> mapFileToDTO(dto, key, file, employeeId));

            // ───────── SUBMIT TO SERVICE ─────────
            OnboardingResponseDTO result = onboardingService.submitOnboarding(dto, employeeId, files);

            employeeRepo.findById(employeeId).ifPresent(emp -> {
                emp.setStatus(Employee.EmployeeStatus.UNDER_REVIEW);
                employeeRepo.save(emp);
            });

            tokenService.markUsed(token);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Submission failed: " + e.getMessage());
        }
    }

    // ───────── HELPER METHOD TO MAP FILES TO DTO ─────────
    private void mapFileToDTO(OnboardingRequestDTO dto, String key, MultipartFile file, Long employeeId) {
        String savedPath = fileStorageService.saveFile(file, employeeId, key);

        switch (key) {

            case "bank":
                if (dto.getBankDetails() == null) dto.setBankDetails(new BankDetailsDTO());
                dto.getBankDetails().setDocumentFilePath(savedPath);
                break;

            case "photo":
                if (dto.getPhotoProof() == null) dto.setPhotoProof(new IdentityProofDTO());
                dto.getPhotoProof().setPhotoFilePath(savedPath);
                break;

            case "pan":
                if (dto.getPanProof() == null) dto.setPanProof(new IdentityProofDTO());
                dto.getPanProof().setPanFilePath(savedPath);
                break;

            case "aadhaar":
                if (dto.getAadharProof() == null) dto.setAadharProof(new IdentityProofDTO());
                dto.getAadharProof().setAadhaarFilePath(savedPath);
                break;

            case "voter":
                if (dto.getVoterProof() == null) dto.setVoterProof(new IdentityProofDTO());
                dto.getVoterProof().setVoterIdFilePath(savedPath);
                break;

            case "passport":
                if (dto.getPassportProof() == null) dto.setPassportProof(new IdentityProofDTO());
                dto.getPassportProof().setPassportFilePath(savedPath);
                break;

            // EDUCATION
            case "ssc_certificate":
                if (dto.getSsc() == null) dto.setSsc(new EducationDTO());
                dto.getSsc().setCertificateFilePath(savedPath);
                break;

            case "inter_certificate":
                if (dto.getIntermediate() == null) dto.setIntermediate(new EducationDTO());
                dto.getIntermediate().setCertificateFilePath(savedPath);
                break;

            // INTERNSHIP FILES
            case "internship_offer_letter":
                if (dto.getInternships() != null && !dto.getInternships().isEmpty()) {
                    dto.getInternships().get(0).setOfferLetterPath(savedPath);
                }
                break;

            case "internship_experience_certificate":
                if (dto.getInternships() != null && !dto.getInternships().isEmpty()) {
                    dto.getInternships().get(0).setExperienceCertificatePath(savedPath);
                }
                break;

            // WORK EXPERIENCE FILES
            case "experience_offer_letter":
                if (dto.getWorkExperiences() != null && !dto.getWorkExperiences().isEmpty()) {
                    dto.getWorkExperiences().get(0).setOfferLetterPath(savedPath);
                }
                break;

            case "experience_relieving_letter":
                if (dto.getWorkExperiences() != null && !dto.getWorkExperiences().isEmpty()) {
                    dto.getWorkExperiences().get(0).setRelievingLetterPath(savedPath);
                }
                break;

            case "experience_payslips":
                if (dto.getWorkExperiences() != null && !dto.getWorkExperiences().isEmpty()) {
                    dto.getWorkExperiences().get(0).setPayslipsPath(savedPath);
                }
                break;

            case "experience_certificate":
                if (dto.getWorkExperiences() != null && !dto.getWorkExperiences().isEmpty()) {
                    dto.getWorkExperiences().get(0).setExperienceCertificatePath(savedPath);
                }
                break;

            default:
                mapIndexedEducationFiles(dto, key, savedPath);
                break;
        }
    }

    // ───────── INDEXED EDUCATION FILES ─────────
    private void mapIndexedEducationFiles(OnboardingRequestDTO dto, String key, String savedPath) {
        try {

            if (key.startsWith("grad_certificate_") && dto.getGraduations() != null) {
                int index = Integer.parseInt(key.substring("grad_certificate_".length()));
                if (index >= 0 && index < dto.getGraduations().size()) {
                    dto.getGraduations().get(index).setCertificateFilePath(savedPath);
                }
            }

            // FIX ADDED HERE (GRAD MARKS MEMO SUPPORT)
            if (key.startsWith("grad_marks_") && dto.getGraduations() != null) {
                int index = Integer.parseInt(key.substring("grad_marks_".length()));
                if (index >= 0 && index < dto.getGraduations().size()) {
                    dto.getGraduations().get(index).setMarksMemoFilePath(savedPath);
                }
            }

            if (key.startsWith("postgrad_certificate_") && dto.getPostGraduations() != null) {
                int index = Integer.parseInt(key.substring("postgrad_certificate_".length()));
                if (index >= 0 && index < dto.getPostGraduations().size()) {
                    dto.getPostGraduations().get(index).setCertificateFilePath(savedPath);
                }
            }

            if (key.startsWith("certification_certificate_file_") && dto.getOtherCertificates() != null) {
                int index = Integer.parseInt(key.substring("certification_certificate_file_".length()));
                if (index >= 0 && index < dto.getOtherCertificates().size()) {
                    dto.getOtherCertificates().get(index).setCertificateFilePath(savedPath);
                }
            }

        } catch (NumberFormatException e) {
        }
    }

    // ───────── REVIEW ONBOARDING ─────────
    @PostMapping("/review")
    public ResponseEntity<String> reviewOnboarding(@RequestBody ReviewRequest request) {
        try {
            onboardingService.submitReview(
                    request.getEmployeeId(),
                    request.getStatus(),
                    request.getRemarks(),
                    request.getRejectedDocuments()
            );
            return ResponseEntity.ok("Review processed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Review failed: " + e.getMessage());
        }
    }

    // ───────── REJECT DOCUMENT ─────────
    @PostMapping("/reject-document")
    public ResponseEntity<String> rejectDocument(@RequestBody RejectDocumentRequest request) {
        try {
            onboardingService.rejectDocument(
                    request.getEmployeeId(),
                    request.getEntityType(),
                    request.getEntityId(),
                    request.getRemarks()
            );
            return ResponseEntity.ok("Document rejected successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to reject document: " + e.getMessage());
        }
    }

    // ───────── GET ONBOARDING BY ID ─────────
    @GetMapping("/{id}")
    public ResponseEntity<OnboardingResponseDTO> getOnboarding(@PathVariable Long id) {
        return onboardingService.findOnboardingById(id)
                .map(this::mapToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/detail")
    public ResponseEntity<?> getOnboardingByToken(@RequestParam String token) {

        Long employeeId = tokenService.validateToken(token);

        if (employeeId == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        return onboardingService.findOnboardingById(employeeId)
                .map(this::mapToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // ───────── ENTITY → RESPONSE ─────────
    private OnboardingResponseDTO mapToResponse(EmployeeForm form) {
        return OnboardingResponseDTO.fromEntity(form);
    }
}