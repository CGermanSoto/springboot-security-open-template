package com.spacecodee.springbootsecurityopentemplate.service.storage.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.storage.IFileStorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageServiceImpl implements IFileStorageService {

    private static final String STORAGE_DIR = "files";

    @Value("${app.file-storage.location}")
    private String storageLocation;
    private Path fileStorageLocation;
    private final ExceptionShortComponent exceptionShortComponent;

    @PostConstruct
    public void init() {
        try {
            this.fileStorageLocation = Paths.get(this.storageLocation).toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            log.error("Could not create storage directory: {}", this.fileStorageLocation, ex);
            throw this.exceptionShortComponent.noCreatedException(
                    "storage.directory.create.failed",
                    this.fileStorageLocation.toString());
        }
    }

    @Override
    public String storeFile(MultipartFile file, String fileName) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName; // Store only filename, not full path
        } catch (Exception ex) {
            log.error("Could not store file: {}", file.getOriginalFilename(), ex);
            throw this.exceptionShortComponent.noCreatedException(
                    "storage.file.store.failed",
                    file.getOriginalFilename());
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            // Remove "files/" prefix if present
            String cleanPath = filePath.startsWith(STORAGE_DIR + File.separator)
                    ? filePath.substring(STORAGE_DIR.length() + 1)
                    : filePath;

            Path fullPath = this.fileStorageLocation.resolve(cleanPath);

            if (!Files.exists(fullPath)) {
                throw this.exceptionShortComponent.objectNotFoundException("storage.file.not.found", filePath);
            }

            Files.delete(fullPath);
        } catch (ObjectNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error deleting file: {}", filePath, ex);
            throw this.exceptionShortComponent.noDeletedException("storage.file.delete.failed", filePath);
        }
    }
}
