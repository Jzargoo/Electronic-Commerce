package com.jzargo.buysmartgui.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzargo.buysmartgui.util.JWTStorage;
import com.jzargo.shared.common.BaseRole;
import com.jzargo.shared.model.LoginCreateDto;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import com.sun.jdi.InvocationException;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

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
    
    public BaseRole checkPermission(String s){

         if (s == null || s.isBlank()) {
             return BaseRole.GUEST;
         }

        HttpRequest jwt = HttpRequest.newBuilder()
                .GET()
                .uri(
                        URI.create(
                                basicUrl + "/api/auth/checkPermission"
                        ))
                .header("Authorization", "Bearer " + s)
                .build();
        HttpResponse<String> send = null;
        try {
            send = client.send(jwt, HttpResponse.BodyHandlers.ofString());
            return Arrays.stream(send.body().split(" "))
                    .map(BaseRole::valueOf)
                    .max(Enum::compareTo).orElse(BaseRole.GUEST);
        } catch (Exception e){
            return BaseRole.GUEST;
        }
    }
}
