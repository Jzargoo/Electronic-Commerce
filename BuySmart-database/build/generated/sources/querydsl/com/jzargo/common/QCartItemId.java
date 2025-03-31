package com.jzargo.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCartItemId is a Querydsl query type for CartItemId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCartItemId extends BeanPath<CartItemId> {

    private static final long serialVersionUID = 1113813529L;

    public static final QCartItemId cartItemId = new QCartItemId("cartItemId");

    public final NumberPath<Long> cartId = createNumber("cartId", Long.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public QCartItemId(String variable) {
        super(CartItemId.class, forVariable(variable));
    }

    public QCartItemId(Path<? extends CartItemId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCartItemId(PathMetadata metadata) {
        super(CartItemId.class, metadata);
    }

}

