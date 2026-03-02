package com.company.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByVendorNameIgnoreCase(String vendorName);
    Optional<Vendor> findByEmail(String email);
    Optional<Vendor> findByPhone(String phone);

    boolean existsByEmailOrPhone(String email, String phone);
    boolean existsByEmailAndVendorIdNot(String email, Long vendorId);
    boolean existsByPhoneAndVendorIdNot(String phone, Long vendorId);

    List<Vendor> findByStatusOrderByVendorNameAsc(Vendor.Status status);
    List<Vendor> findByVendorType(String vendorType);
}