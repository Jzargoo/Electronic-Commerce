package com.jzargo.services;

import java.util.List;

public interface ImageStorageService {

    String storeUserFile(byte[] file);
    List<String> storeProductFiles(Iterable<byte[]> files);
    byte[] getUserFile(String fileName);
    List<byte[]> getProductFiles(Iterable<String> fileName);
    void deleteUserFile(String filename);
    void deleteProductFiles(Iterable<String> c);
}
