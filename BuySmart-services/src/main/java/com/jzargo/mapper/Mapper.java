package com.jzargo.mapper;


import com.jzargo.exceptions.DataNotFoundException;
import com.stripe.exception.StripeException;

public interface Mapper <F,T>{
    T map(F object) ;

    default T map(F FromObject, T RawObject){
        return RawObject;
    }
}