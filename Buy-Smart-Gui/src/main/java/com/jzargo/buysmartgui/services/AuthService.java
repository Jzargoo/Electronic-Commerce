package com.jzargo.buysmartgui.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzargo.buysmartgui.util.JWTStorage;
import com.jzargo.shared.model.LoginCreateDto;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {
    public static final AuthService INSTANCE = new AuthService();

    public static AuthService getInstance() {
        return INSTANCE;
    }
    private final HttpClient client;
    private final String basicUrl = "http://localhost:8080";
    private final ObjectMapper mapper;

     private AuthService() {
        this.mapper = new ObjectMapper();
        client = HttpClient.newHttpClient();

    }

    @SneakyThrows
    public int login(LoginCreateDto loginCreateDto) {
        HttpResponse<String> authenticate = authenticate(mapper.writeValueAsString(loginCreateDto), "/api/auth/login");
        switch (authenticate.statusCode()){
            case 500 -> System.out.println("Bad request. Try again later");
            case 200 -> {
                JWTStorage.saveToken(authenticate.body());
                System.out.println(
                        JWTStorage.loadToken()
                );
                return 200;
            }
            default -> System.out.println("authenticate");

        }
        return 1;
    }

    @SneakyThrows
    public int register(UserCreateAndUpdateDto dto) {
        HttpResponse<String> authenticate = authenticate(mapper.writeValueAsString(dto), "/api/auth/register");
        switch (authenticate.statusCode()){
            case 409 -> System.out.println("user already exist");
            case 500 -> System.out.println("Bad request. Try again later");
            case 201 -> {
                JWTStorage.saveToken(authenticate.body());
                System.out.println(
                    JWTStorage.loadToken()
                );
                return 201;
            }
            default -> System.out.println("authenticate");
        }
            return 1;
    }

    private HttpResponse<String> authenticate(String mapper, String path) throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(mapper))
                .header("Content-Type", "application/json")
                .uri(
                        URI.create(
                                basicUrl + path)
                ).build();
        return client.send(build, HttpResponse.BodyHandlers.ofString());

    }
    
    @SneakyThrows
    public boolean checkJWT(String s){

         if (s == null || s.isBlank()) {
             return false;
         }

        HttpRequest jwt = HttpRequest.newBuilder()
                .GET()
                .uri(
                        URI.create(
                                basicUrl + "/api/auth/check"
                        ))
                .header("Authorization", "Bearer " + s)
                .build();
        HttpResponse<Void> send = client.send(jwt, HttpResponse.BodyHandlers.discarding());

        return send.statusCode() == 200;
    }
}