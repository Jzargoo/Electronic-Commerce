package com.jzargo.common;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
//import java.util.stream.Collectors;
public class QPredicate {
    List<Predicate> predicates = new ArrayList<>();
    public static QPredicate builder(){
        return new QPredicate();
    }

    public <T>QPredicate add(T object, Function<T,Predicate> function){
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public <T> QPredicate adds(List<T> objects, Function<T, Predicate> function) {
        if (objects != null) {
            for (T object : objects) {
                predicates.add(function.apply(object));
            }
        }
        return this;
    }

    public Predicate buildAnd(){
        return Optional.ofNullable(ExpressionUtils.allOf(predicates))
                .orElseGet(() -> Expressions.asBoolean(true).isTrue());
    }

    public Predicate buildOr(){
        return Optional.ofNullable(ExpressionUtils.anyOf(predicates))
                .orElseGet(() -> Expressions.asBoolean(true).isTrue());
    }


}
