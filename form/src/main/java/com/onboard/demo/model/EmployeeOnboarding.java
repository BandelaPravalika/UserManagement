package com.onboard.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee_onboarding")
@Getter
@Setter
public class EmployeeOnboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to existing Employee
    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false, unique = true)
    private Employee employee;

    /* =======================
       SECTION 1 – PERSONAL
       ======================= */

    @Column(nullable = false)
    private String fullName; // as per Aadhar

    @Column(nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private String emailId;

    private String bloodGroup;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 500)
    private String permanentAddress;

    @Column(nullable = false, length = 500)
    private String presentAddress;

    @Column(nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private String fatherPhone;

    @Column(nullable = false)
    private String motherName;

    @Column(nullable = false)
    private String motherPhone;

    @Column(nullable = false)
    private String emergencyContactName;

    @Column(nullable = false)
    private String emergencyRelationship;

    @Column(nullable = false)
    private String emergencyContactNumber;

    /* =======================
       SECTION 2 – EDUCATION
       ======================= */

    @OneToOne(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private SscDetails sscDetails;

    @OneToOne(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private IntermediateDetails intermediateDetails;

    @OneToOne(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private GraduationDetails graduationDetails;

    @OneToOne(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private PostGraduationDetails postGraduationDetails;

    @OneToMany(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OtherCertificate> otherCertificates = new ArrayList<>();

    /* =======================
       SECTION 3 – EXPERIENCE
       ======================= */

    @OneToMany(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InternshipDetails> internships = new ArrayList<>();

    @OneToMany(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperienceDetails> workExperiences = new ArrayList<>();

    /* =======================
       SECTION 4 – BANK
       ======================= */

    @OneToOne(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private BankDetails bankDetails;

    /* =======================
       SECTION 5 – DOCUMENTS
       ======================= */

    @OneToOne(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private DocumentDetails documentDetails;
}
