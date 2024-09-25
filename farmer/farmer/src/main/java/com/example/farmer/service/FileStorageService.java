package com.example.farmer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        // Normalize the upload directory path
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // Create directories if they don't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique file name to prevent conflicts
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // Resolve the file path and save the file
        Path filePath = uploadPath.resolve(uniqueFilename);
        file.transferTo(filePath.toFile());

        return uniqueFilename;
    }

    public String storeFile(MultipartFile file, boolean overwrite) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("File name is null");
        }

        Path filePath = uploadPath.resolve(originalFilename);
        
        if (!overwrite && Files.exists(filePath)) {
            throw new IOException("File with the same name already exists");
        }

        file.transferTo(filePath.toFile());

        return originalFilename;
    }

    // Optional: Method to validate file type
    public boolean isValidFileType(MultipartFile file, String[] allowedTypes) {
        String fileType = file.getContentType();
        if (fileType != null) {
            for (String type : allowedTypes) {
                if (fileType.equalsIgnoreCase(type)) {
                    return true;
                }
            }
        }
        return false;
    }
}
