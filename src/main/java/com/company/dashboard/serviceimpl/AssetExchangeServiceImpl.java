package com.company.dashboard.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.AssetExchange;
import com.company.dashboard.model.Vendor;
import com.company.dashboard.repository.AssetExchangeRepository;
import com.company.dashboard.repository.VendorRepository;
import com.company.dashboard.service.AssetExchangeService;

@Service
@Transactional
public class AssetExchangeServiceImpl implements AssetExchangeService {

    private final AssetExchangeRepository assetRepo;
    private final VendorRepository vendorRepo;

    public AssetExchangeServiceImpl(AssetExchangeRepository assetRepo, VendorRepository vendorRepo) {
        this.assetRepo = assetRepo;
        this.vendorRepo = vendorRepo;
    }

    @Override
    public AssetExchange createAsset(String assetName, String assetTag, String receiverName,
                                     String exchangeType, String remarks, Long vendorId,
                                     MultipartFile[] files) throws IOException {

        if (assetRepo.existsByAssetTag(assetTag)) {
            throw new IllegalArgumentException("Asset tag already exists");
        }

        Vendor vendor = vendorRepo.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));

        AssetExchange asset = new AssetExchange();
        asset.setAssetName(assetName);
        asset.setAssetTag(assetTag);
        asset.setReceiverName(receiverName);
        asset.setExchangeType(exchangeType);
        asset.setRemarks(remarks);
        asset.setVendor(vendor);

        if (files != null && files.length > 0) {
            asset.setPhotoPaths(savePhotos(files));
        }

        return assetRepo.save(asset);
    }

    @Override
    public AssetExchange updateAsset(Long id,
                                     String assetName, String assetTag, String receiverName,
                                     String exchangeType, String remarks, Long vendorId,
                                     MultipartFile[] newFiles, boolean replacePhotos) throws IOException {

        AssetExchange asset = assetRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));

        if (assetName != null) asset.setAssetName(assetName);
        if (receiverName != null) asset.setReceiverName(receiverName);
        if (exchangeType != null) asset.setExchangeType(exchangeType);
        if (remarks != null) asset.setRemarks(remarks);

        // Asset tag uniqueness check only when changed
        if (assetTag != null && !assetTag.equals(asset.getAssetTag())) {
            if (assetRepo.existsByAssetTag(assetTag)) {
                throw new IllegalArgumentException("Asset tag already exists");
            }
            asset.setAssetTag(assetTag);
        }

        if (vendorId != null) {
            Vendor vendor = vendorRepo.findById(vendorId)
                    .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
            asset.setVendor(vendor);
        }

        // Photo handling
        if (replacePhotos) {
            asset.setPhotoPaths(newFiles != null && newFiles.length > 0 ? savePhotos(newFiles) : null);
        } else if (newFiles != null && newFiles.length > 0) {
            String existing = asset.getPhotoPaths() != null ? asset.getPhotoPaths() : "";
            String added = savePhotos(newFiles);
            asset.setPhotoPaths(existing.isBlank() ? added : existing + "," + added);
        }

        return assetRepo.save(asset);
    }

    @Override
    public AssetExchange getAssetById(Long id) {
        return assetRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));
    }

    @Override
    public List<AssetExchange> getAllAssets() {
        return assetRepo.findAllWithVendor();
    }

    @Override
    public void deleteAsset(Long id) {
        if (!assetRepo.existsById(id)) {
            throw new IllegalArgumentException("Asset not found");
        }
        assetRepo.deleteById(id);
    }

    private String savePhotos(MultipartFile[] files) throws IOException {
        List<String> paths = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get("uploads/" + filename);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                paths.add(path.toString());
            }
        }
        return String.join(",", paths);
    }
}