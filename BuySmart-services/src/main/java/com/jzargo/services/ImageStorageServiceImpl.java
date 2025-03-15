package com.jzargo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageStorageServiceImpl {

    @Value("${file.upload-dir}")
    private String uploadDir;


    public void deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir, filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении файла", e);
        }
    }


    public String storeFile(MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(uploadDir));
            if (file == null){
                return System.getenv("DUMMY");
            }

            String uniqueFilename = "saved_image:" + UUID.randomUUID();

            Path filePath = Paths.get(uploadDir, uniqueFilename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла", e);
        }
    }

    public File getFile(String filename) {
        Path filePath = Paths.get(uploadDir, filename);
        File file = filePath.toFile();
        if (!file.exists()) {
            throw new RuntimeException("Файл не найден");
        }
        return file;
    }
}
