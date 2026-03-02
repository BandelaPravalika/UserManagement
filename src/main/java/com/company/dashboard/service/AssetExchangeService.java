package com.company.dashboard.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.model.AssetExchange;

public interface AssetExchangeService {

    AssetExchange createAsset(
            String assetName, String assetTag, String receiverName,
            String exchangeType, String remarks, Long vendorId,
            MultipartFile[] files) throws IOException;

    AssetExchange updateAsset(
            Long id,
            String assetName, String assetTag, String receiverName,
            String exchangeType, String remarks, Long vendorId,
            MultipartFile[] newFiles, boolean replacePhotos) throws IOException;

    AssetExchange getAssetById(Long id);

    List<AssetExchange> getAllAssets();

    void deleteAsset(Long id);
}