package com.jzargo.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageStorageService {

    String storeUserFile(MultipartFile file);
    List<String> storeProductFiles(Iterable<MultipartFile> files);
    Optional<byte[]> getUserFile(String fileName);
    List<byte[]> getProductFiles(Iterable<String> fileName);
    void deleteUserFile(String filename);
    void deleteProductFiles(Iterable<String> c);
}
