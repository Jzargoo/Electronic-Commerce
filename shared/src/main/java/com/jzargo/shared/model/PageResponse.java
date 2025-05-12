package com.jzargo.shared.model;


import java.util.List;

public record PageResponse<T>(List<T> content, Metadata metadata) {

    public static <T> PageResponse<T> of(int size, long totalElements, int totalPages, List<T> content){
        var metadata = new Metadata(
                size,
                totalPages,
                totalElements
        );
        return new PageResponse<>(content,metadata);
    }

    record Metadata(Integer size, Integer pages, Long totalElements) {
    }
}
