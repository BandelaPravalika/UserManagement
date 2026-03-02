package com.company.dashboard.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	 String getUploadBaseDir();
	String saveFile(MultipartFile file, Long employeeId, String folderName);
}

