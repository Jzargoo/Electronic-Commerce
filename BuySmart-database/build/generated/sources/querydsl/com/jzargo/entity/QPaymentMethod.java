package com.jzargo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentMethod is a Querydsl query type for PaymentMethod
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentMethod extends EntityPathBase<PaymentMethod> {

    private static final long serialVersionUID = 1130470228L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentMethod paymentMethod = new QPaymentMethod("paymentMethod");

    public final StringPath brand = createString("brand");

    public final StringPath createdAt = createString("createdAt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath last4 = createString("last4");

    public final ListPath<Payment, QPayment> payments = this.<Payment, QPayment>createList("payments", Payment.class, QPayment.class, PathInits.DIRECT2);

    public final StringPath stripeId = createString("stripeId");

    public final EnumPath<com.jzargo.common.PaymentType> type = createEnum("type", com.jzargo.common.PaymentType.class);

    public final QUser user;

    public QPaymentMethod(String variable) {
        this(PaymentMethod.class, forVariable(variable), INITS);
    }

    public QPaymentMethod(Path<? extends PaymentMethod> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentMethod(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentMethod(PathMetadata metadata, PathInits inits) {
        this(PaymentMethod.class, metadata, inits);
    }

    public QPaymentMethod(Class<? extends PaymentMethod> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

