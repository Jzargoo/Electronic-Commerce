package com.jzargo.buysmartgui.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzargo.buysmartgui.util.JWTStorage;
import com.jzargo.shared.model.AddressDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AddressService {
    private static final AddressService INSTANCE = new AddressService();

    private static final String BASIC_URL = "http://localhost:8080/api/address";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public static AddressService getInstance() {
        return INSTANCE;
    }

    private AddressService() {
        this.mapper = new ObjectMapper();
        this.client = HttpClient.newHttpClient();

    }

    public List<AddressDto> getAddresses() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + JWTStorage.loadToken())
                .GET()
                .uri(
                        new URI(BASIC_URL)
                )
                .build();

        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(send.body(), new TypeReference<>(){});
    }

    public AddressDto addAddress(AddressDto addressDto) throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .POST(
                        HttpRequest.BodyPublishers.ofString(
                                mapper.writeValueAsString(addressDto)
                        )
                )
                .uri(URI.create(BASIC_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + JWTStorage.loadToken())
                .build();

        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(send.body(), AddressDto.class);
    }
}
