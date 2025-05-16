package com.jzargo.buysmartgui.util;

import com.jzargo.shared.common.Tags;
import com.jzargo.shared.filters.ProductFilter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QueryStringBuilder {
    private static final StringBuilder sb = new StringBuilder();
    public static String toQueryString(ProductFilter filter){
        if (filter.userIds() != null) {
            for (Long id : filter.userIds()) {
                sb.append("userIds=").append(URLEncoder.encode(id.toString(), StandardCharsets.UTF_8)).append("&");
            }
        }

        if (filter.tags() != null) {
            for (Tags tag : filter.tags()) {
                sb.append("tags=").append(URLEncoder.encode(tag.name(), StandardCharsets.UTF_8)).append("&");
            }
        }

        if (filter.category() != null) {
            sb.append("category=").append(URLEncoder.encode(filter.category().name(), StandardCharsets.UTF_8)).append("&");
        }

        if (filter.minPrice() != null) {
            sb.append("minPrice=").append(filter.minPrice()).append("&");
        }

        if (filter.maxPrice() != null) {
            sb.append("maxPrice=").append(filter.maxPrice()).append("&");
        }

        // удалить последний &
        if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }
}

