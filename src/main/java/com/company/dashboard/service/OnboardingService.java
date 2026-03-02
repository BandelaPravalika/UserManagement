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

    /**
     * Submit onboarding details along with optional uploaded files.
     *
     * @param dto     DTO containing onboarding information
     * @param employeeId ID of the employee being onboarded
     * @param files   Map of document type → MultipartFile (e.g. "passbook", "photo", "pan", etc.)
     * @return OnboardingResponseDTO with result or confirmation
     * @throws IOException if file handling fails
     */
    OnboardingResponseDTO submitOnboarding(
            OnboardingRequestDTO dto,
            Long employeeId,
            Map<String, MultipartFile> files
    ) throws IOException;

    /**
     * Find an onboarding record by employee ID.
     *
     * @param id employee ID
     * @return Optional containing the EmployeeForm if found
     */
    Optional<EmployeeForm> findOnboardingById(Long id);

    /**
     * Submit review decision for an employee's onboarding.
     *
     * @param employeeId        ID of the employee
     * @param status            Approved / Rejected / etc.
     * @param remarks           Review comments
     * @param rejectedDocuments List of document keys that were rejected (if any)
     */
    void submitReview(
            Long employeeId,
            String status,
            String remarks,
            List<String> rejectedDocuments
    );

    /**
     * Reject a single specific document during review.
     *
     * @param employeeId ID of the employee
     * @param entityType Type of document/entity (e.g. "pan", "aadhar")
     * @param entityId   Optional ID if the document has its own ID
     * @param remarks    Reason for rejection
     */
    void rejectDocument(
            Long employeeId,
            String entityType,
            Long entityId,
            String remarks
    );
}