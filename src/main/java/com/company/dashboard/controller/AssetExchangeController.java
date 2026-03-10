package com.company.dashboard.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.AssetExchange;
import com.company.dashboard.model.Vendor;
import com.company.dashboard.repository.AssetExchangeRepository;
import com.company.dashboard.repository.VendorRepository;
import com.company.dashboard.service.AssetExchangeService;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*")
public class AssetExchangeController {

    private final AssetExchangeRepository assetExchangeRepository;
    private final VendorRepository vendorRepository;
    private final AssetExchangeService assetService;

    public AssetExchangeController(AssetExchangeRepository assetExchangeRepository,
                                   VendorRepository vendorRepository,
                                   AssetExchangeService assetService) {
        this.assetExchangeRepository = assetExchangeRepository;
        this.vendorRepository = vendorRepository;
        this.assetService = assetService;
    }

    // ─────────────────────────────────────────────
    //  GET ALL
    // ─────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllAssets() {
        List<AssetExchange> assets = assetExchangeRepository.findAllWithVendor();

        List<Map<String, Object>> response = assets.stream().map(asset -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", asset.getId());
            map.put("assetName", asset.getAssetName());
            map.put("assetTag", asset.getAssetTag());
            map.put("receiverName", asset.getReceiverName());
            map.put("exchangeType", asset.getExchangeType());
            map.put("remarks", asset.getRemarks());
            map.put("vendorId", asset.getVendor() != null ? asset.getVendor().getVendorId() : null);
            map.put("vendorName", asset.getVendor() != null ? asset.getVendor().getVendorName() : "Unknown");
            map.put("photoPaths", asset.getPhotoPaths() != null && !asset.getPhotoPaths().isBlank()
                    ? Arrays.asList(asset.getPhotoPaths().split(","))
                    : Collections.emptyList());
     
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────
    //  GET ONE
    // ─────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAssetById(@PathVariable Long id) {
        AssetExchange asset = assetExchangeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        Map<String, Object> map = new HashMap<>();
        map.put("id", asset.getId());
        map.put("assetName", asset.getAssetName());
        map.put("assetTag", asset.getAssetTag());
        map.put("receiverName", asset.getReceiverName());
        map.put("exchangeType", asset.getExchangeType());
        map.put("remarks", asset.getRemarks());
        map.put("vendorId", asset.getVendor() != null ? asset.getVendor().getVendorId() : null);
        map.put("vendorName", asset.getVendor() != null ? asset.getVendor().getVendorName() : "Unknown");
        map.put("photoPaths", asset.getPhotoPaths() != null && !asset.getPhotoPaths().isBlank()
                ? Arrays.asList(asset.getPhotoPaths().split(","))
                : Collections.emptyList());
        
        return ResponseEntity.ok(map);
    }

    // ─────────────────────────────────────────────
    //  CREATE (with multiple photos)
    // ─────────────────────────────────────────────
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createAsset(
            @RequestParam("assetName") String assetName,
            @RequestParam("assetTag") String assetTag,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("exchangeType") String exchangeType,
            @RequestParam("vendorId") Long vendorId,
            @RequestParam(value = "remarks", required = false) String remarks,
            @RequestParam(value = "files", required = false) MultipartFile[] files
    ) throws IOException {

        AssetExchange created = assetService.createAsset(
                assetName, assetTag, receiverName, exchangeType, remarks, vendorId, files);

        Map<String, Object> resp = new HashMap<>();
        resp.put("id", created.getId());
        resp.put("assetTag", created.getAssetTag());
        resp.put("message", "Asset created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // ─────────────────────────────────────────────
    //  PATCH / UPDATE (partial + add more photos)
    // ─────────────────────────────────────────────
    
           
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateAsset(
            @PathVariable Long id,
            @RequestBody AssetExchange patch
    ) {
        AssetExchange asset = assetExchangeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        // Update only provided fields
        if (patch.getAssetName() != null)    asset.setAssetName(patch.getAssetName());
        if (patch.getAssetTag() != null)     asset.setAssetTag(patch.getAssetTag());
        if (patch.getReceiverName() != null) asset.setReceiverName(patch.getReceiverName());
        if (patch.getExchangeType() != null) asset.setExchangeType(patch.getExchangeType());
        if (patch.getRemarks() != null)      asset.setRemarks(patch.getRemarks());
        if (patch.getVendor() != null && patch.getVendor().getVendorId() != null) {
            Vendor v = vendorRepository.findById(patch.getVendor().getVendorId())
                    .orElseThrow();
            asset.setVendor(v);
        }

        // If you want to support photo update via PATCH → keep multipart version separately
        // or create dedicated endpoint: POST /api/assets/{id}/photos

        assetExchangeRepository.save(asset);

        return ResponseEntity.ok(Map.of("message", "Asset updated successfully"));
    }
    // ─────────────────────────────────────────────
    //  DELETE
    // ─────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.ok(Map.of("message", "Asset deleted successfully"));
    }
}