package com.example.imagebasic.service;

import com.example.imagebasic.config.StorageProperties;
import com.example.imagebasic.exception.StorageException;
import com.example.imagebasic.exception.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.NoSuchFileException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;
@Service
public class ImageService {

    private final Path rootLocation;

    @Autowired
    public ImageService(StorageProperties storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }


    public String store(MultipartFile file, String primaryPath, String secondaryPath) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            // 确保路径是相对于rootLocation的偏移
            Path storageLocation = rootLocation;

            if (primaryPath != null && !primaryPath.trim().isEmpty()) {
                storageLocation = storageLocation.resolve(primaryPath.trim());
            }

            if (secondaryPath != null && !secondaryPath.trim().isEmpty()) {
                storageLocation = storageLocation.resolve(secondaryPath.trim());
            }

            storageLocation = storageLocation.normalize();
            Path destinationFile = storageLocation.toAbsolutePath();

            if (!Files.exists(storageLocation)) {
                Files.createDirectories(storageLocation);
            }

            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            return file.getOriginalFilename();
        } catch (Exception e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
    public Resource loadImage(String primaryPath, String secondaryPath, String imageName) throws Exception {
        try {
            // 确保路径是相对于rootLocation的偏移
            Path storageLocation = rootLocation;

            if (primaryPath != null && !primaryPath.trim().isEmpty()) {
                storageLocation = storageLocation.resolve(primaryPath.trim());
            }

            if (secondaryPath != null && !secondaryPath.trim().isEmpty()) {
                storageLocation = storageLocation.resolve(secondaryPath.trim());
            }

            storageLocation = storageLocation.normalize();
            Path filePath = storageLocation.resolve(imageName).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ImageNotFoundException(imageName);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }
    public Resource loadImage(String primaryPath, String secondaryPath) throws Exception {
        try {
            // 确保路径是相对于rootLocation的偏移
            Path storageLocation = rootLocation;

            if (primaryPath != null && !primaryPath.trim().isEmpty()) {
                storageLocation = storageLocation.resolve(primaryPath.trim());
            }

            if (secondaryPath != null && !secondaryPath.trim().isEmpty()) {
                storageLocation = storageLocation.resolve(secondaryPath.trim());
            }

            storageLocation = storageLocation.normalize();
            Path filePath = storageLocation;

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ImageNotFoundException(secondaryPath);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

}
