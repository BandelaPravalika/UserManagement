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

    OnboardingResponseDTO submitOnboarding(
            OnboardingRequestDTO dto,
            Long employeeId,
            Map<String, MultipartFile> files,
            String token
    ) throws IOException;

    Optional<EmployeeForm> findOnboardingById(Long id);

    void rejectDocument(
            Long employeeId,
            String entityType,
            Long entityId,
            String remarks
    );

    List<Map<String, String>> submitReview(
            Long employeeId,
            String status,
            String remarks
    );

    List<String> getRejectedDocuments(Long employeeId);

    void clearRejectedDocuments(Long employeeId);
}