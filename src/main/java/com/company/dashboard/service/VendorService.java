package com.company.dashboard.service;

import java.util.List;

import com.company.dashboard.model.Vendor;

public interface VendorService {

    Vendor createVendor(Vendor vendor);
    Vendor updateVendor(Long vendorId, Vendor vendor);
    Vendor getVendorById(Long vendorId);
    List<Vendor> getAllVendors();
    List<Vendor> getVendorsByStatus(Vendor.Status status);
    List<Vendor> getVendorsByType(String vendorType);
    void deactivateVendor(Long vendorId);
}