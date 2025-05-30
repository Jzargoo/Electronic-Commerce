package com.jzargo.services;

import java.io.IOException;
import java.util.List;

public interface ImageStorageService {

    String storeUserFile(byte[] file) throws IOException;
    List<String> storeProductFiles(Iterable<byte[]> files);
    byte[] getUserFile(String fileName);
    List<byte[]> getProductFiles(Iterable<String> fileName);
    void deleteUserFile(String filename);
    void deleteProductFiles(Iterable<String> c);

    byte[] getProductFile(String name) throws IOException;
}
