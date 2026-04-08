package com.company.dashboard.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.dashboard.model.OnboardingToken;
import com.company.dashboard.service.OnboardingTokenService;

@RestController
@RequestMapping("/api/onboardingtoken")

public class OnboardingTokenController {

    private final OnboardingTokenService tokenService;

    public OnboardingTokenController(OnboardingTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<?> validateToken(@PathVariable String token) {
        try {
            Long employeeId = tokenService.validateToken(token);
            return ResponseEntity.ok(
                    java.util.Map.of(
                            "employee_id", employeeId,
                            "status", "valid"
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(java.util.Map.of("error", e.getMessage()));
        }
    }
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<?> deleteByEmployee(@PathVariable Long employeeId) {
        tokenService.deleteByEmployee(employeeId);
        return ResponseEntity.ok("Tokens deleted for employee");
    }

    @GetMapping("/tokens")
    public List<OnboardingToken> getAllTokens() {
        return tokenService.getAllTokens();
    }


    
}

