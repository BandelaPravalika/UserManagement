package com.company.dashboard.service;

import java.util.List;

import com.company.dashboard.model.OnboardingToken;

public interface OnboardingTokenService {

    String generateToken(Long employeeId);

    Long validateToken(String token);

    void markUsed(String token);
    
    List<OnboardingToken> getAllTokens();

    void deleteByEmployee(Long employeeId);
}

