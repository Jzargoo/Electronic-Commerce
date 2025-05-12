package com.jzargo.filtration;


import com.jzargo.shared.common.Categories;
import com.jzargo.shared.common.Tags;

import java.util.List;

public record ProductFilter (List<Long> userIds,
                             List<Tags> tags,
                             Categories category,
                             Double minPrice,
                             Double maxPrice){
}
