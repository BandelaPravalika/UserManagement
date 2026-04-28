package com.company.dashboard.util;

public class FolderMappingUtil {

    /**
     * Maps a file key (e.g., ssc_certificate, pan_file) to a high-level category folder.
     * This ensures all documents are grouped into consistent folders like 'education', 'identity', etc.
     */
    public static String getFolderNameForKey(String key) {
        if (key == null) return "other";
        
        String k = key.toLowerCase();
        
        if (k.contains("bank")) return "bank";
        if (k.contains("photo")) return "identity";
        if (k.contains("pan")) return "identity";
        if (k.contains("aadhaar") || k.contains("aadhar")) return "identity";
        if (k.contains("voter")) return "identity";
        if (k.contains("passport")) return "identity";
        
        if (k.contains("internship")) return "internship";
        if (k.contains("experience")) return "experience";
        
        if (k.contains("ssc") || k.contains("inter") || k.contains("postgrad") || k.contains("grad") || k.contains("education")) {
            return "education";
        }

        if (k.contains("certific")) return "certification";
        
        return "other";
    }

    /**
     * Determines a clean, human-readable filename based on the file key.
     * e.g., pan_file -> pan, grad_certificate_0 -> graduation_certificate_1
     */
    public static String getFileNameForKey(String key) {
        if (key == null) return "document";
        
        String k = key.toLowerCase();
        
        // 1. Identity
        if (k.contains("photo")) return "photo";
        if (k.contains("pan")) return "pan";
        if (k.contains("aadhaar") || k.contains("aadhar")) return "aadhaar";
        if (k.contains("voter")) return "voter";
        if (k.contains("passport")) return "passport";
        if (k.contains("bank")) return "bank_passbook";

        // 2. Internship / Experience (Check these before Education to avoid "inter" conflict)
        if (k.contains("internship") || k.contains("experience")) {
            String category = "document";
            if (k.contains("offer")) category = "offer_letter";
            else if (k.contains("reliev")) category = "relieving_letter";
            else if (k.contains("payslip")) category = "payslip";
            else if (k.contains("certificate")) category = "experience_certificate";

            String prefix = k.contains("internship") ? "internship" : "experience";
            
            String indexStr = key.replaceAll("[^0-9]", "");
            if (indexStr.isEmpty()) {
                return prefix + "_" + category;
            } else {
                int index = Integer.parseInt(indexStr) + 1;
                return prefix + "_" + category + "_" + index;
            }
        }

        // 3. Education (ORDER MATTERS: check postgrad before grad)
        String type = "";
        if (k.contains("ssc")) type = "ssc";
        else if (k.contains("inter")) type = "intermediate";
        else if (k.contains("postgrad")) type = "post_graduation";
        else if (k.contains("grad")) type = "graduation";

        if (!type.isEmpty()) {
            String category = k.contains("mark") ? "marks" : "certificate";
            String indexStr = key.replaceAll("[^0-9]", ""); // use original key for case-insensitive but number preserving
            if (indexStr.isEmpty()) {
                return type + "_" + category;
            } else {
                int index = Integer.parseInt(indexStr) + 1;
                return type + "_" + category + "_" + index;
            }
        }

        // 4. Other Certifications
        if (k.contains("certific")) {
            String indexStr = key.replaceAll("[^0-9]", "");
            int index = indexStr.isEmpty() ? 0 : Integer.parseInt(indexStr) + 1;
            return "certification_hide_" + (index > 0 ? index : "1");
        }
        
        return "other_document";
    }
}
