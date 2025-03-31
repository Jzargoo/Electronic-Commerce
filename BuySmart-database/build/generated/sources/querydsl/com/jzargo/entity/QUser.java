package com.jzargo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1484888158L;

    public static final QUser user = new QUser("user");

    public final ListPath<Cart, QCart> carts = this.<Cart, QCart>createList("carts", Cart.class, QCart.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> createdTime = createDate("createdTime", java.time.LocalDate.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Order, QOrder> orders = this.<Order, QOrder>createList("orders", Order.class, QOrder.class, PathInits.DIRECT2);

    public final ListPath<Product, QProduct> OwnProducts = this.<Product, QProduct>createList("OwnProducts", Product.class, QProduct.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final ListPath<PaymentMethod, QPaymentMethod> paymentMethods = this.<PaymentMethod, QPaymentMethod>createList("paymentMethods", PaymentMethod.class, QPaymentMethod.class, PathInits.DIRECT2);

    public final ListPath<Payment, QPayment> payments = this.<Payment, QPayment>createList("payments", Payment.class, QPayment.class, PathInits.DIRECT2);

    public final StringPath phone = createString("phone");

    public final StringPath ProfileImage = createString("ProfileImage");

    public final ListPath<com.jzargo.common.Role, EnumPath<com.jzargo.common.Role>> roles = this.<com.jzargo.common.Role, EnumPath<com.jzargo.common.Role>>createList("roles", com.jzargo.common.Role.class, EnumPath.class, PathInits.DIRECT2);

    public final StringPath stripeCustomerId = createString("stripeCustomerId");

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

