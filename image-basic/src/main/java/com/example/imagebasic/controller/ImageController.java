// 在ImageController中的部分代码

package com.example.imagebasic.controller;

import com.example.imagebasic.service.ImageService;
import com.example.imagebasic.exception.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String primaryPath,  // 添加新的请求参数
            @RequestParam(required = false) String secondaryPath) {  // 添加新的请求参数
        try {
            String imageName = imageService.store(file, primaryPath, secondaryPath);
            return ResponseEntity.ok("Image uploaded successfully and the URL is /api/image/" + imageName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getImage(
            @RequestParam(required = false) String primaryPath,
            @RequestParam(required = false) String secondaryPath) {
        try {
            Resource image = imageService.loadImage(primaryPath, secondaryPath);

            // Determine the content type of the image
            String contentType = determineContentType(image);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(image);
        } catch (ImageNotFoundException inf) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private String determineContentType(Resource image) throws IOException {
        // Default content type to application/octet-stream
        String contentType = "application/octet-stream";

        String mimeType = Files.probeContentType(Paths.get(image.getURI()));
        if (mimeType != null) {
            contentType = mimeType;
        }

        return contentType;
    }

    // ... 其他方法的代码
}
