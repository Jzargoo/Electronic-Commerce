package com.jzargo.buysmartgui.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzargo.buysmartgui.util.JWTStorage;
import com.jzargo.shared.model.ProductReadDto;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final ObjectMapper mapper;
    private final HttpClient client;
    private final String basicUrl = "http://localhost:8080/api/products";

    public static ProductService getInstance() {
        return INSTANCE;
    }
     private ProductService() {
        this.mapper = new ObjectMapper();
        this.client = HttpClient.newHttpClient();
    }

    // Absolutely sure that get 3 or smaller products
    @SneakyThrows
    public List<ProductReadDto> getThreeProducts(){
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(
                        basicUrl + "/view/random"
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
    @SneakyThrows
    public byte[] getImage(String image){
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(
                        basicUrl + "/view/image/" + image
                ))
                .GET()
                .header("Accept", "application/json")
                .build();
        return client.send(req, HttpResponse.BodyHandlers.ofByteArray()).body();
    }
}
