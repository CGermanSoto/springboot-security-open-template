package com.spacecodee.springbootsecurityopentemplate.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class FileTypeUtils {

    private FileTypeUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    public static boolean isValidContentType(String contentType) {
        return ALLOWED_CONTENT_TYPES.contains(contentType);
    }

    public static boolean isValidFile(MultipartFile file) {
        return file != null &&
                !file.isEmpty() &&
                isValidContentType(file.getContentType());
    }
}