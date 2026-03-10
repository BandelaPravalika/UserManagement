package com.company.dashboard.serviceimpl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.dashboard.model.Employee;
import com.company.dashboard.model.OnboardingToken;
import com.company.dashboard.repository.EmployeeRepository;
import com.company.dashboard.repository.OnboardingTokenRepository;
import com.company.dashboard.service.OnboardingTokenService;

@Service
public class OnboardingTokenServiceImpl implements OnboardingTokenService {

    private final OnboardingTokenRepository tokenRepo;
    private final EmployeeRepository employeeRepo;

    public OnboardingTokenServiceImpl(OnboardingTokenRepository tokenRepo,
                                      EmployeeRepository employeeRepo) {
        this.tokenRepo = tokenRepo;
        this.employeeRepo = employeeRepo;
    }

    // ---------------- GENERATE TOKEN ----------------
    @Override
    @Transactional
    public String generateToken(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);

        String tokenValue = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        OnboardingToken token = new OnboardingToken();
        token.setToken(tokenValue);
        token.setEmployee(employee);
        token.setExpiryTime(LocalDateTime.now().plusHours(48)); // token valid 48 hours
        token.setUsed(false);

        tokenRepo.save(token);

        return tokenValue;
    }

    // ---------------- VALIDATE TOKEN ----------------
    @Override
    @Transactional(readOnly = true)
    public Long validateToken(String tokenValue) {
        OnboardingToken token = tokenRepo.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (token.isUsed()) {
            throw new RuntimeException("Token already used");
        }

        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        return token.getEmployee().getId();
    }

    // ---------------- MARK TOKEN AS USED ----------------
    @Override
    @Transactional
    public void markUsed(String tokenValue) {
        OnboardingToken token = tokenRepo.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        token.setUsed(true);
        tokenRepo.save(token);
    }

    // ---------------- GET ALL TOKENS ----------------
    @Override
    @Transactional(readOnly = true)
    public List<OnboardingToken> getAllTokens() {
        return tokenRepo.findAll(); // fetch all tokens from database
    }

    // ---------------- DELETE TOKENS BY EMPLOYEE ----------------
    @Override
    @Transactional
    public void deleteByEmployee(Long employeeId) {
        Optional<OnboardingToken> tokens = tokenRepo.findById(employeeId);
        if (!tokens.isEmpty()) {
            tokenRepo.deleteAll();
        }
    }
}
