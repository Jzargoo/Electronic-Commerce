package com.jzargo.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(List<T> content, Metadata metadata) {

    public static <T> PageResponse<T> of(Page<T> page){
        var metadata = new Metadata(
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
        return new PageResponse<>(page.getContent(),metadata);
    }

    record Metadata(Integer size, Integer pages, Long totalElements) {
    }
}
