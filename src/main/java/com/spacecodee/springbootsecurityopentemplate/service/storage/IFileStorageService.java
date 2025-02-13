package com.spacecodee.springbootsecurityopentemplate.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

    String storeFile(MultipartFile file, String documentId);

    void deleteFile(String filePath);

}
