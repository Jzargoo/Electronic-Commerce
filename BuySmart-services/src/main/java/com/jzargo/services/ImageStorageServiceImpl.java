package com.jzargo.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ImageStorageServiceImpl implements ImageStorageService{

    @Value("${UPLOAD_DIR_PRODUCTS}")
    private String uploadProductDir;

    @Value("${UPLOAD_DIR_USERS}")
    private String uploadUserDir;

    @Override
    public String storeUserFile(MultipartFile file) {
        try {

            return store(uploadUserDir,file);
        } catch (IOException e) {
            log.error("Error with saving profile image: {}", e.getMessage());
            throw new RuntimeException();
        } catch (Exception e){
            return System.getenv("DUMMY");
        }
    }

    private String getFileExtension(String fileName) {
        return (fileName != null && fileName.contains(".")) ?
                fileName.substring(fileName.lastIndexOf(".")) : "";
    }


   @SneakyThrows
   @Override
    public List<String> storeProductFiles(Iterable<MultipartFile> files) {
        if(files==null) throw new FileNotFoundException();

        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file:files){
            fileNames.add(
                store(uploadProductDir,file)
            );
        }
        return fileNames;
    }

    private String store(String dir,MultipartFile file) throws IOException {
        Path uploadPath = Files.createDirectories(Paths.get(dir));

        String extension = getFileExtension(file.getOriginalFilename());
        String uniqueName = "saved_image_" + UUID.randomUUID() + extension;

        Path filePath = uploadPath.resolve(uniqueName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueName;
    }

    @SneakyThrows
    @Override
    public byte[] getUserFile(String fileName) {
        return Files.readAllBytes(Path.of(uploadUserDir + fileName));
    }

    @SneakyThrows
    @Override
    public List<byte[]> getProductFiles(Iterable<String> fileNames) {
        List<byte[]> images=new ArrayList<>();
        if (fileNames == null) {
            throw new FileNotFoundException("Product files do not found");
        }
        for(String s:fileNames) {
            images.add(
                Files.readAllBytes(Paths.get(uploadProductDir+s))
            );
        }
        return images;
    }

    @Override
    @SneakyThrows
    public void deleteUserFile(String Image) {
        Files.delete(Path.of(uploadUserDir + Image));
    }

    @Override
    @SneakyThrows
    public void deleteProductFiles(Iterable<String> c){
        if(c==null) throw new FileNotFoundException("Files do not found");
        for(String s:c){
            Files.delete(Path.of(uploadProductDir+s));
        }
    }
}
