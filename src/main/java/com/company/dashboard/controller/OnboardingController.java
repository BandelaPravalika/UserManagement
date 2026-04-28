package com.company.dashboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.Employee;
import com.company.dashboard.model.EmployeeForm;
import com.company.dashboard.repository.EmployeeRepository;
import com.company.dashboard.util.FolderMappingUtil;
import com.company.dashboard.response.*;
import com.company.dashboard.service.FileStorageService;
import com.company.dashboard.service.OnboardingService;
import com.company.dashboard.service.OnboardingTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;
    private final OnboardingTokenService tokenService;
    private final EmployeeRepository employeeRepo;
    private final FileStorageService fileStorageService;

    public OnboardingController(
            OnboardingService onboardingService,
            OnboardingTokenService tokenService,
            EmployeeRepository employeeRepo,
            FileStorageService fileStorageService
    ) {
        this.onboardingService = onboardingService;
        this.tokenService = tokenService;
        this.employeeRepo = employeeRepo;
        this.fileStorageService = fileStorageService;
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

            Employee employee = employeeRepo.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            // ───────── HANDLE FILES ─────────
            Map<String, MultipartFile> files = new HashMap<>();
            if (multipartFiles != null) {
                multipartFiles.forEach((k, v) -> {
                    if (v != null && !v.isEmpty()) files.put(k.toLowerCase(), v);
                });
            }

            // Upload each file to Cloudinary; savedPath is now a full https:// URL
            files.forEach((key, file) -> mapFileToDTO(dto, key, file, employeeId, employee.getFullName()));

            // ───────── SUBMIT TO SERVICE (Integrated Transaction) ─────────
            OnboardingResponseDTO result = onboardingService.submitOnboarding(dto, employeeId, files, token);

            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg != null && (msg.toLowerCase().contains("token") || msg.toLowerCase().contains("expired") || msg.toLowerCase().contains("used"))) {
                System.err.println("⚠️ [ONBOARDING SUBMIT BAD REQUEST] " + msg);
                return ResponseEntity.badRequest().body("Submission failed: " + msg);
            }

            System.err.println("❌ [ONBOARDING SUBMIT ERROR]");
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Submission failed: " + e.getMessage());

        } catch (Exception e) {
            System.err.println("❌ [ONBOARDING SUBMIT CRITICAL FAILED]");
            e.printStackTrace();

            java.io.StringWriter sw = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(sw));

            return ResponseEntity.internalServerError()
                    .body("Submission failed: " + e.getMessage() + " | Stack trace: " + sw.toString());
        }
    }

    // ───────── HELPER METHOD TO MAP FILES TO DTO ─────────
    private void mapFileToDTO(OnboardingRequestDTO dto, String key, MultipartFile file, Long employeeId, String employeeName) {
        String k = key.toLowerCase();

        String folderName = FolderMappingUtil.getFolderNameForKey(k);
        String baseFileName = FolderMappingUtil.getFileNameForKey(k);

        // Upload to Cloudinary → returns full secure_url
        String savedPath = fileStorageService.saveFile(file, employeeId, employeeName, folderName, baseFileName);
        System.out.println("✅ Cloudinary upload [" + file.getOriginalFilename() + "] key=[" + key + "] url=" + savedPath);

        if (k.contains("bank")) {
            if (dto.getBankDetails() == null) dto.setBankDetails(new BankDetailsDTO());
            dto.getBankDetails().setDocumentFilePath(savedPath);
        } else if (k.contains("photo")) {
            if (dto.getPhotoProof() == null) dto.setPhotoProof(new IdentityProofDTO());
            dto.getPhotoProof().setPhotoFilePath(savedPath);
        } else if (k.contains("pan")) {
            if (dto.getPanProof() == null) dto.setPanProof(new IdentityProofDTO());
            dto.getPanProof().setPanFilePath(savedPath);
        } else if (k.contains("aadhaar") || k.contains("aadhar")) {
            if (dto.getAadharProof() == null) dto.setAadharProof(new IdentityProofDTO());
            dto.getAadharProof().setAadhaarFilePath(savedPath);
        } else if (k.contains("voter")) {
            if (dto.getVoterProof() == null) dto.setVoterProof(new IdentityProofDTO());
            dto.getVoterProof().setVoterIdFilePath(savedPath);
        } else if (k.contains("passport")) {
            if (dto.getPassportProof() == null) dto.setPassportProof(new IdentityProofDTO());
            dto.getPassportProof().setPassportFilePath(savedPath);
        } else if (k.contains("ssc")) {
            if (dto.getSsc() == null) dto.setSsc(new EducationDTO());
            dto.getSsc().setCertificateFilePath(savedPath);
        } else if (k.contains("internship")) {
            mapIndexedWorkFiles(dto, k, savedPath, true);
        } else if (k.contains("experience")) {
            mapIndexedWorkFiles(dto, k, savedPath, false);
        } else if (k.contains("inter")) {
            if (dto.getIntermediate() == null) dto.setIntermediate(new EducationDTO());
            dto.getIntermediate().setCertificateFilePath(savedPath);
        } else {
            mapIndexedEducationFiles(dto, k, savedPath);
        }
    }

    // ───────── INDEXED INTERNSHIP / EXPERIENCE FILES ─────────
    private void mapIndexedWorkFiles(OnboardingRequestDTO dto, String k, String savedPath, boolean isInternship) {
        try {
            int index = 0;
            String numberOnly = k.replaceAll("[^0-9]", "");
            if (!numberOnly.isEmpty()) index = Integer.parseInt(numberOnly);

            if (isInternship && dto.getInternships() != null) {
                if (index < dto.getInternships().size()) {
                    InternshipDTO i = dto.getInternships().get(index);
                    if (k.contains("offer")) i.setOfferLetterPath(savedPath);
                    else i.setExperienceCertificatePath(savedPath);
                }
            } else if (!isInternship && dto.getWorkExperiences() != null) {
                if (index < dto.getWorkExperiences().size()) {
                    ExperienceDTO e = dto.getWorkExperiences().get(index);
                    if (k.contains("offer")) e.setOfferLetterPath(savedPath);
                    else if (k.contains("reliev")) e.setRelievingLetterPath(savedPath);
                    else if (k.contains("payslip")) e.setPayslipsPath(savedPath);
                    else e.setExperienceCertificatePath(savedPath);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to map work file key: " + k);
        }
    }

    // ───────── INDEXED EDUCATION FILES ─────────
    private void mapIndexedEducationFiles(OnboardingRequestDTO dto, String k, String savedPath) {
        try {
            int index = 0;
            String numberOnly = k.replaceAll("[^0-9]", "");
            if (!numberOnly.isEmpty()) index = Integer.parseInt(numberOnly);

            if (k.contains("postgrad") && dto.getPostGraduations() != null) {
                if (index < dto.getPostGraduations().size()) {
                    dto.getPostGraduations().get(index).setCertificateFilePath(savedPath);
                }
            } else if (k.contains("grad") && dto.getGraduations() != null) {
                if (index < dto.getGraduations().size()) {
                    if (k.contains("mark")) dto.getGraduations().get(index).setMarksMemoFilePath(savedPath);
                    else dto.getGraduations().get(index).setCertificateFilePath(savedPath);
                }
            } else if (k.contains("certific") && dto.getOtherCertificates() != null) {
                if (index < dto.getOtherCertificates().size()) {
                    dto.getOtherCertificates().get(index).setCertificateFilePath(savedPath);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to map indexed file key: " + k);
        }
    }

    // ───────── REVIEW ONBOARDING ─────────
    @PostMapping("/review")
    public ResponseEntity<?> reviewOnboarding(@RequestBody ReviewRequest request) {
        try {
            List<Map<String, String>> result = onboardingService.submitReview(
                    request.getEmployeeId(),
                    request.getStatus(),
                    request.getRemarks()
            );

            if ("REJECTED".equalsIgnoreCase(request.getStatus())) {
                return ResponseEntity.ok(result);
            }

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
