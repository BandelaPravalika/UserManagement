package com.company.dashboard.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.dashboard.model.Vendor;
import com.company.dashboard.service.VendorService;

@RestController
@RequestMapping("/api/vendors")

public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
        Vendor created = vendorService.createVendor(vendor);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{vendorId}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long vendorId,
            @RequestBody Vendor vendor) {
        return ResponseEntity.ok(vendorService.updateVendor(vendorId, vendor));
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long vendorId) {
        return ResponseEntity.ok(vendorService.getVendorById(vendorId));
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Vendor>> getVendorsByStatus(@PathVariable Vendor.Status status) {
        return ResponseEntity.ok(vendorService.getVendorsByStatus(status));
    }

    @GetMapping("/type/{vendorType}")
    public ResponseEntity<List<Vendor>> getVendorsByType(@PathVariable String vendorType) {
        return ResponseEntity.ok(vendorService.getVendorsByType(vendorType));
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<Void> deactivateVendor(@PathVariable Long vendorId) {
        vendorService.deactivateVendor(vendorId);
        return ResponseEntity.noContent().build();
    }
}

