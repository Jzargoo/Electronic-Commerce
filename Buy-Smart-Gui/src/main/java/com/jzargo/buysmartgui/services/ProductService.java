package com.jzargo.buysmartgui.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzargo.buysmartgui.errors.ProductNotFoundException;
import com.jzargo.buysmartgui.util.JWTStorage;
import com.jzargo.buysmartgui.util.QueryStringBuilder;
import com.jzargo.shared.filters.ProductFilter;
import com.jzargo.shared.model.PageResponse;
import com.jzargo.shared.model.ProductReadDto;
import lombok.SneakyThrows;

import java.io.IOException;
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

    public PageResponse<ProductReadDto> findAll(ProductFilter filter) throws IOException,
            InterruptedException, ProductNotFoundException {
        
        HttpRequest build = HttpRequest.newBuilder()
                .GET()
                .uri(
                        URI.create(basicUrl + "/view?" + QueryStringBuilder.toQueryString(filter))
                )
                .build();
        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());

        PageResponse<ProductReadDto> body = mapper.readValue(send.body(), new TypeReference<>() {});


        if(body == null || body.content().isEmpty()) throw new ProductNotFoundException();

        return body;
    }
}
