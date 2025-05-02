package com.jzargo.buysmartgui.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzargo.shared.model.LoginCreateDto;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {
    private final HttpClient client;
    private final String basicUrl = "http://localhost:8080";
    private final ObjectMapper mapper;

    public AuthService() {
        this.mapper = new ObjectMapper();
        client = HttpClient.newHttpClient();

    }

    @SneakyThrows
    public void login(LoginCreateDto loginCreateDto) {
        authenticate(mapper.writeValueAsString(loginCreateDto), "/api/auth/login");

    }

    @SneakyThrows
    public void register(UserCreateAndUpdateDto dto) {
        HttpResponse<String> authenticate = authenticate(mapper.writeValueAsString(dto), "/api/auth/register");
        switch (authenticate.statusCode()){
            case 409 -> System.out.println("user already exist");
            case 500 -> System.out.println("Bad request. Try again later");
            case 201 -> System.out.println("User created");
        }
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
}
