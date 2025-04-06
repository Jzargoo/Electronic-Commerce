package com.jzargo.mapper;


public interface Mapper <F,T>{
    T map(F object) ;

    default T map(F FromObject, T RawObject){
        return RawObject;
    }
}