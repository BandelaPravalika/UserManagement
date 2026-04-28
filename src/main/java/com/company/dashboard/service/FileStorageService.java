package com.company.dashboard.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String saveFile(MultipartFile file, Long employeeId, String employeeName, String folderName, String baseFileName);
}

