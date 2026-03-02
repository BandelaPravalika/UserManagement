package com.company.dashboard.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.dashboard.service.FileStorageService;

//import lombok.Value;
import org.springframework.beans.factory.annotation.Value;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	@Value("${onboarding.upload.dir}")
	private String uploadBaseDir;

    public String getUploadBaseDir() {
        return uploadBaseDir;
    }

	    // Other repositories & constructors ...

    @Override
    public String saveFile(MultipartFile file, Long employeeId, String folderName) { 
    	if (file == null || file.isEmpty()) return null;

	        try {
	            // Use OS-independent path
	            Path folderPath = Paths.get(uploadBaseDir, folderName);
	            Files.createDirectories(folderPath);

	            // Unique file name
	            String sanitizedFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
	            String uniqueFileName = employeeId + "_" + UUID.randomUUID() + "_" + sanitizedFileName;

	            Path targetPath = folderPath.resolve(uniqueFileName);

	            // Copy the file, replace if exists (should be rare due to UUID)
	            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

	            // Return URL for frontend
	            String urlPath = "/uploads/" + folderName + "/" + uniqueFileName;
	            return urlPath;

	        } catch (IOException e) {
	            throw new RuntimeException("Failed to save file: " + file.getOriginalFilename(), e);
	        }
	        
	    }
}
