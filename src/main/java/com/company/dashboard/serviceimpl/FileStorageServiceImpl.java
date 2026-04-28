package com.company.dashboard.serviceimpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.company.dashboard.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Uploads a file to Cloudinary and returns the secure URL.
     *
     * @param file         MultipartFile uploaded
     * @param employeeId   Employee ID for folder naming
     * @param employeeName Employee full name for folder naming
     * @param folderName   Logical folder name (e.g. "photo", "pan")
     * @param baseFileName Preferred public_id filename
     * @return Full Cloudinary secure_url (https://res.cloudinary.com/...)
     */
    @Override
    public String saveFile(MultipartFile file, Long employeeId, String employeeName, String folderName, String baseFileName) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // Build folder path: onboarding/<EmployeeName_ID>/<folderName>
            String sanitizedName = (employeeName != null ? employeeName : "Unknown")
                    .replaceAll("[^a-zA-Z0-9]", "");
            String folder = "onboarding/" + sanitizedName + "_" + employeeId + "/" + folderName;

            // Build public_id (filename without extension — Cloudinary handles extension)
            String publicId = (baseFileName != null && !baseFileName.trim().isEmpty())
                    ? baseFileName.trim().replaceAll("[^a-zA-Z0-9\\-_]", "_")
                    : "file_" + System.currentTimeMillis();

            @SuppressWarnings("rawtypes")
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "public_id", publicId,
                            "resource_type", "auto"  // supports images AND PDFs
                    )
            );

            String secureUrl = uploadResult.get("secure_url").toString();
            System.out.println("✅ Cloudinary upload success: " + secureUrl);
            return secureUrl;

        } catch (Exception e) {
            System.err.println("❌ Cloudinary upload failed: " + e.getMessage());
            throw new RuntimeException("File upload to Cloudinary failed: " + e.getMessage(), e);
        }
    }
}