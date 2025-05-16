package com.jzargo.shared.filters;


import com.jzargo.shared.common.Categories;
import com.jzargo.shared.common.Tags;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductFilter (List<Long> userIds,
                             List<Tags> tags,
                             Categories category,
                             String name,
                             Double minPrice,
                             Double maxPrice){
}
