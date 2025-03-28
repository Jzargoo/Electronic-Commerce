package com.jzargo.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageStorageService {

    String storeUserFile(MultipartFile file);
    List<String> storeProductFiles(Iterable<MultipartFile> files);
    byte[] getUserFile(String fileName);
    List<byte[]> getProductFiles(Iterable<String> fileName);
    void deleteUserFile(String filename);
    void deleteProductFiles(Iterable<String> c);
}
