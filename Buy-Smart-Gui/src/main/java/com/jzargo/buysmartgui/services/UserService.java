package com.jzargo.buysmartgui.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzargo.buysmartgui.ui.DialogController;
import com.jzargo.buysmartgui.util.JWTStorage;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import com.jzargo.shared.model.UserReadDto;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserService {
    public static final UserService INSTANCE = new UserService();
    private final ObjectMapper mapper;
    private final HttpClient client;
    private static final String BASIC_URL= "http://localhost:8080/api/users";
    private UserService(){
        this.mapper = new ObjectMapper();
        this.client = HttpClient.newHttpClient();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public byte[] getImageIcon() throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .uri(
                        URI.create(BASIC_URL + "/profileImage"))
                .GET()
                .setHeader("Authorization", "Bearer " + JWTStorage.loadToken())
                .build();
        HttpResponse<byte[]> a = client.send(build, HttpResponse.BodyHandlers.ofByteArray());
        return a.body();
    }

    public UserReadDto setUserIcon(File selectedFile) throws URISyntaxException, IOException, InterruptedException {
        byte[] bytes;
        try ( FileInputStream f = new FileInputStream(selectedFile)){
            bytes =f.readAllBytes();
        }
        UserCreateAndUpdateDto user = UserCreateAndUpdateDto.builder()
                .ProfileImage(bytes)
                .build();
        HttpRequest build = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(
                        mapper.convertValue(user, String.class)
                ))
                .setHeader("Authorization", "Bearer " + JWTStorage.loadToken())
                .uri(new URI(
                        BASIC_URL
                ))
                .build();
        HttpResponse<String> a = client.send(build, HttpResponse.BodyHandlers.ofString());
        if (a.statusCode() != 200) {
            throw new IOException();
        }
        return mapper.readValue(a.body(), UserReadDto.class);
    }

    public UserReadDto getUser() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .uri(new URI(
                        BASIC_URL))
                .GET()
                .header("Authorization", "Bearer " + JWTStorage.loadToken())
                .build();
        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(send.body(), UserReadDto.class);
    }
}
