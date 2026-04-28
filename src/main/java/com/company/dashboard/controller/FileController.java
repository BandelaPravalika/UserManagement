package com.company.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Files are now stored on Cloudinary and served directly via their secure_url.
 * This controller is kept as a placeholder; no local file serving is needed.
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    @GetMapping("/**")
    public ResponseEntity<String> filesMoved() {
        return ResponseEntity.ok(
                "Files are now served directly via Cloudinary CDN URLs. " +
                "No local file serving is required."
        );
    }
}
