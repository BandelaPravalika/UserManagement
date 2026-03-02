package com.company.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.OnboardingToken;

public interface OnboardingTokenRepository 
        extends JpaRepository<OnboardingToken, Long> {

    Optional<OnboardingToken> findByToken(String token);
   
    
    void deleteByEmployee_Id(Long employeeId);

   
    void deleteByEmployee_IdAndUsedFalse(Long employeeId);
}

