package com.jzargo.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ImageStorageServiceImpl implements ImageStorageService{

    @Value("${Files.upload.product}")
    private String uploadProductDir;

    @Value("${Files.upload.user}")
    private String uploadUserDir;
    @Override
    public String storeUserFile(byte[] file) throws IOException {
        try {

            return store(uploadUserDir,file);
        } catch (IOException e) {
            log.error("Error with saving profile image: {}", e.getMessage());
            throw new IOException();
        } catch (Exception e){
            return "dummy.jpg";
        }
    }

   @SneakyThrows
   @Override
    public List<String> storeProductFiles(Iterable<byte[]> files) {
        if(files==null) throw new FileNotFoundException();

        List<String> fileNames = new ArrayList<>();

        for (byte[] file:files){
            fileNames.add(
                store(uploadProductDir,file)
            );
        }
        return fileNames;
    }

    private String store(String dir,byte[] file) throws IOException {
        Path uploadPath = Files.createDirectories(Paths.get(dir));
        String uniqueName = "saved_image_" + UUID.randomUUID();

        Path filePath = uploadPath.resolve(uniqueName);
        Files.copy(new ByteArrayInputStream(file), filePath, StandardCopyOption.REPLACE_EXISTING);

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

    @Override
    public byte[] getProductFile(String name) throws IOException {
        return Files.readAllBytes(Path.of(uploadProductDir + name));
    }
}
