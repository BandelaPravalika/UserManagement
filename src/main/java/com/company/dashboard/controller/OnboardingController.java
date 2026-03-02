package com.company.dashboard.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.Employee;
import com.company.dashboard.repository.EmployeeRepository;
import com.company.dashboard.response.OnboardingRequestDTO;
import com.company.dashboard.response.OnboardingResponseDTO;
import com.company.dashboard.response.RejectDocumentRequest;
import com.company.dashboard.response.ReviewRequest;
import com.company.dashboard.service.OnboardingService;
import com.company.dashboard.service.OnboardingTokenService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/onboarding")
@CrossOrigin("*")
public class OnboardingController {

    private final OnboardingService onboardingService;
    private final OnboardingTokenService tokenService;
    private final EmployeeRepository employeeRepo;
    private final Path baseUploadDir;

    public OnboardingController(
            OnboardingService onboardingService,
            OnboardingTokenService tokenService,
            EmployeeRepository employeeRepo,
            @Value("${upload.directory:C:/onboard/uploads}") String uploadDirPath) {
        this.onboardingService = onboardingService;
        this.tokenService = tokenService;
        this.employeeRepo = employeeRepo;
        this.baseUploadDir = Paths.get(uploadDirPath).toAbsolutePath().normalize();

        // Startup log for debugging
        System.out.println("[OnboardingController] Upload base directory: " + baseUploadDir);
        System.out.println("[OnboardingController] Directory exists: " + Files.exists(baseUploadDir));
    }

    // ==================== FILE SERVE ====================
 // ==================== FILE SERVE ====================
    @GetMapping("/files/**")
    public ResponseEntity<Resource> serveFile(HttpServletRequest request) throws IOException {

        String requestUri = request.getRequestURI();
        System.out.println("Requested URI: " + request.getRequestURI());

        String filename = requestUri.replace("/api/onboarding/files/", "");

        Path file = baseUploadDir.resolve(filename).normalize();

        if (!file.startsWith(baseUploadDir)) {
            return ResponseEntity.badRequest().build();
        }

        if (!Files.exists(file)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(file.toUri());
        String contentType = Files.probeContentType(file);

        return ResponseEntity.ok()
                .contentType(contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    // ==================== FILE LIST ====================
    @GetMapping("/files")
    public List<String> listFiles() throws IOException {
        Path uploadBase = baseUploadDir.toAbsolutePath().normalize();
        if (!Files.exists(uploadBase)) return Collections.emptyList();
        return Files.list(uploadBase)
                .filter(Files::isRegularFile)
                .map(path -> "/api/onboarding/files/" + path.getFileName().toString())
                .toList();
    }

    // ==================== SUBMIT ONBOARDING ====================
    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitOnboarding(
            @RequestPart("data") OnboardingRequestDTO dto,
            @RequestPart(value = "passbook", required = false) MultipartFile passbookFile,
            @RequestPart(value = "photo", required = false) MultipartFile photoFile,
            @RequestPart(value = "pan", required = false) MultipartFile panFile,
            @RequestPart(value = "aadhar", required = false) MultipartFile aadharFile,
            @RequestPart(value = "voter", required = false) MultipartFile voterFile,
            @RequestPart(value = "passport", required = false) MultipartFile passportFile,
            @RequestPart(value = "ssc_certificate", required = false) MultipartFile sscCertificateFile,
            @RequestPart(value = "ssc_marks", required = false) MultipartFile sscMarksFile,
            @RequestPart(value = "inter_certificate", required = false) MultipartFile interCertificateFile,
            @RequestPart(value = "grad_certificate", required = false) MultipartFile gradCertificateFile,
            @RequestParam("token") String token) {

        try {
            Long employeeId = tokenService.validateToken(token);
            if (employeeId == null) {
                return ResponseEntity.badRequest().body("Invalid token");
            }

            Map<String, MultipartFile> files = new HashMap<>();
            putIfPresent(files, "passbook", passbookFile);
            putIfPresent(files, "photo", photoFile);
            putIfPresent(files, "pan", panFile);
            putIfPresent(files, "aadhar", aadharFile);
            putIfPresent(files, "voter", voterFile);
            putIfPresent(files, "passport", passportFile);
            putIfPresent(files, "ssc_certificate", sscCertificateFile);
            putIfPresent(files, "ssc_marks", sscMarksFile);
            putIfPresent(files, "inter_certificate", interCertificateFile);
            putIfPresent(files, "grad_certificate", gradCertificateFile);

            // Pass original files to service – let service save them
            OnboardingResponseDTO result = onboardingService.submitOnboarding(dto, employeeId, files);

            employeeRepo.findById(employeeId).ifPresent(emp -> {
                emp.setStatus(Employee.EmployeeStatus.UNDER_REVIEW);
                employeeRepo.save(emp);
            });

            tokenService.markUsed(token);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Submission failed: " + e.getMessage());
        }
    }

    private void putIfPresent(Map<String, MultipartFile> map, String key, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            map.put(key, file);
        }
    }

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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Review failed: " + e.getMessage());
        }
    }

    @PostMapping("/reject-document")
    public ResponseEntity<String> rejectDocument(
            @RequestBody RejectDocumentRequest request) {
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
}