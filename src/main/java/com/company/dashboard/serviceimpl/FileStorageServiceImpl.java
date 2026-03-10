package com.company.dashboard.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${onboarding.upload.dir:C:/onboard/uploads}")
    private String uploadBaseDir;

    /**
     * Getter for upload base directory
     */
    public String getUploadBaseDir() {
        return uploadBaseDir;
    }

    /**
     * Save the file to the folder and return the relative URL
     *
     * @param file       MultipartFile uploaded
     * @param employeeId Employee ID for naming
     * @param folderName Folder name like "photo", "pan"
     * @return Relative path like /uploads/photo/employeeId_uuid_filename.ext
     */
    @Override
    public String saveFile(MultipartFile file, Long employeeId, String folderName) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // Ensure OS-independent folder path
            Path folderPath = Paths.get(uploadBaseDir, folderName).toAbsolutePath().normalize();
            Files.createDirectories(folderPath); // Create folder if it doesn't exist

            // Sanitize original filename
            String originalFileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "file";
            String sanitizedFileName = originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");

            // Generate unique filename using employeeId and UUID
            String uniqueFileName = employeeId + "_" + UUID.randomUUID() + "_" + sanitizedFileName;

            Path targetPath = folderPath.resolve(uniqueFileName);

            // Copy file to target path (overwrite if exists)
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Return relative URL
            return "/uploads/" + folderName + "/" + uniqueFileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + file.getOriginalFilename(), e);
        }
    }
}