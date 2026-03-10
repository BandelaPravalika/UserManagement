package com.company.dashboard.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.dashboard.model.Vendor;
import com.company.dashboard.repository.VendorRepository;
import com.company.dashboard.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    @Transactional
    public Vendor createVendor(Vendor vendor) {
        if (vendor.getVendorName() == null || vendor.getVendorName().trim().isEmpty()) {
            throw new IllegalArgumentException("Vendor name is required");
        }
        if (vendor.getItemsSupplied() == null || vendor.getItemsSupplied().trim().isEmpty()) {
            throw new IllegalArgumentException("Items supplied is required");
        }
        if (vendorRepository.existsByEmailOrPhone(vendor.getEmail(), vendor.getPhone())) {
            throw new IllegalArgumentException("Email or phone already in use");
        }

        return vendorRepository.save(vendor);
    }

    @Override
    @Transactional
    public Vendor updateVendor(Long vendorId, Vendor updates) {
        Vendor existing = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));

        if (updates.getEmail() != null &&
            vendorRepository.existsByEmailAndVendorIdNot(updates.getEmail(), vendorId)) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (updates.getPhone() != null &&
            vendorRepository.existsByPhoneAndVendorIdNot(updates.getPhone(), vendorId)) {
            throw new IllegalArgumentException("Phone already in use");
        }

        if (updates.getVendorName() != null) existing.setVendorName(updates.getVendorName());
        if (updates.getVendorType() != null) existing.setVendorType(updates.getVendorType());
        if (updates.getItemsSupplied() != null) existing.setItemsSupplied(updates.getItemsSupplied());
        if (updates.getContractStart() != null) existing.setContractStart(updates.getContractStart());
        if (updates.getContractEnd() != null) existing.setContractEnd(updates.getContractEnd());
        if (updates.getContactPerson() != null) existing.setContactPerson(updates.getContactPerson());
        if (updates.getPhone() != null) existing.setPhone(updates.getPhone());
        if (updates.getEmail() != null) existing.setEmail(updates.getEmail());
        if (updates.getStatus() != null) existing.setStatus(updates.getStatus());

        return vendorRepository.save(existing);
    }

    @Override
    public Vendor getVendorById(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public List<Vendor> getVendorsByStatus(Vendor.Status status) {
        return vendorRepository.findByStatusOrderByVendorNameAsc(status);
    }

    @Override
    public List<Vendor> getVendorsByType(String vendorType) {
        return vendorRepository.findByVendorType(vendorType);
    }

    @Override
    @Transactional
    public void deactivateVendor(Long vendorId) {
        Vendor vendor = getVendorById(vendorId);
        vendor.setStatus(Vendor.Status.INACTIVE);
        vendorRepository.save(vendor);
    }
}