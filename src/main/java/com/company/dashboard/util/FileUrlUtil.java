package com.company.dashboard.util;

public class FileUrlUtil {

    /**
     * Ensures that a file path starts with the /uploads/ prefix.
     * If the path is null or empty, returns original.
     * If it already starts with /uploads/, returns original.
     * Otherwise, prepends /uploads/.
     */
    public static String ensurePrefix(String path) {
        if (path == null || path.trim().isEmpty()) {
            return path;
        }
        
        String trimmed = path.trim();
        if (trimmed.startsWith("/uploads/") || trimmed.startsWith("http")) {
            return trimmed;
        }
        
        // Ensure no double slashes
        if (trimmed.startsWith("/")) {
            return "/uploads" + trimmed;
        }
        
        return "/uploads/" + trimmed;
    }
}
