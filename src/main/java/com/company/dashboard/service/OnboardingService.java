package com.company.dashboard.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.EmployeeForm;
import com.company.dashboard.response.OnboardingRequestDTO;
import com.company.dashboard.response.OnboardingResponseDTO;

public interface OnboardingService {

    /*
     * SUBMIT EMPLOYEE ONBOARDING FORM
     * Handles complete onboarding submission including file uploads
     */
    OnboardingResponseDTO submitOnboarding(
            OnboardingRequestDTO dto,
            Long employeeId,
            Map<String, MultipartFile> files
    ) throws IOException;


    /*
     * FETCH ONBOARDING FORM BY ID
     */
    Optional<EmployeeForm> findOnboardingById(Long id);


    /*
     * HR REVIEW PROCESS
     * Approve / Reject onboarding after document verification
     */
    void submitReview(
            Long employeeId,
            String status,
            String remarks,
            List<String> rejectedDocuments
    );


    /*
     * REJECT SPECIFIC DOCUMENT
     */
    void rejectDocument(
            Long employeeId,
            String entityType,
            Long entityId,
            String remarks
    );
}