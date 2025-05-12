package com.jzargo.buysmartgui.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzargo.buysmartgui.util.JWTStorage;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private static final CategoryService INSTANCE = new CategoryService();

    private static final String BASIC_URL = "http://localhost:8080/api/categories";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public static CategoryService getInstance() {
        return INSTANCE;
    }

    private CategoryService() {
        this.mapper = new ObjectMapper();
        this.client = HttpClient.newHttpClient();

    }

    @SneakyThrows
    public List<String> getCategories(int x){
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(
                        BASIC_URL + "/view/random?count=" + x
                ))
                .GET()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + JWTStorage.loadToken())
                .build();
        HttpResponse<String> send = client.send(req, HttpResponse.BodyHandlers.ofString());
        return send.body() == null || send.body().isBlank()?
                new ArrayList<>() :
                mapper.readValue(
                        send.body(), new TypeReference<>() {});
    }
}
